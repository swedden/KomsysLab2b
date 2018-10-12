package State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CallHandler
{
    public enum CallEvent
    {
        USER_INPUT_RECV_SEND_INV, USER_INPUT_RECV_SEND_BYE, RECV_INV_SEND_TRO, RECV_BYE_SEND_OK,
        RECV_TRO_SEND_ACK, SEND_TRO_RECV_ACK, RECV_OK, RECV_NOTHING_SEND_BYE, ERROR};

    private CallState currentState;
    private Socket clientSocket;
    private InputScanner inputScanner;
    private AudioStreamUDP ad = null;
    private int audioStreamPort;

    //for ringingThread
    private boolean run = true;

    public Socket getClientSocket()
    {
        return clientSocket;
    }
    public void setClientSocket(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public InputScanner getInputScanner()
    {
        return inputScanner;
    }

    public CallHandler()
    {
        currentState = new Idle();

        //if((inputScanner == null))
        //{
            inputScanner = new InputScanner();
        //}
    }

    public void changeState(String msg)
    {
        String[] splitmsg = msg.split(" ");
        if(msg.equals("Exit"))
        {

        }
        else if(msg.equals("SEND_INVITE"))
        {
            processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV);
        }
        else if(msg.equals("INVITE"))
        {
            processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO);
        }
        else if(msg.equals("BYE"))
        {
            processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK);
            stopCall();
        }
        else if(splitmsg[0].equals("TRO") && !splitmsg[1].equals(null))
        {
            setAudioStreamPort(Integer.parseInt(splitmsg[1]));
            processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);
        }
        else if(splitmsg[0].equals("ACK") && !splitmsg[1].equals(null))
        {
            setAudioStreamPort(Integer.parseInt(splitmsg[1]));
            processNextEvent(CallHandler.CallEvent.SEND_TRO_RECV_ACK);
        }
        else if(msg.equals("OK"))
        {
            processNextEvent(CallHandler.CallEvent.RECV_OK);
            stopCall();
        }
        else if(msg.equals("SEND_BYE"))
        {
            processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE);
        }
        else
        {
            processNextEvent(CallEvent.ERROR);
        }

        /*switch(msg)
        {
            case "EXIT": break;
            case "SEND_INVITE": processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV); break;
            case "INVITE": processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO); break;
            case "BYE": processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK); break;
            case "TRO": processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);break;
            case "ACK": processNextEvent(CallHandler.CallEvent.SEND_TRO_RECV_ACK); break;
            case "OK": processNextEvent(CallHandler.CallEvent.RECV_OK);break;
            case "SEND_BYE": processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE); break;
            default: processNextEvent(CallEvent.ERROR); break;
        } */
    }

    public void processNextEvent (CallEvent event)
    {
        switch(event)
        {
            case USER_INPUT_RECV_SEND_INV: currentState = currentState.userInputReceivedSendInvite(this); break;
            case USER_INPUT_RECV_SEND_BYE: currentState = currentState.userInputReceivedSendBYE(this); break;
            case RECV_NOTHING_SEND_BYE: currentState = currentState.receiveNothingSendBYE(this);
            case RECV_INV_SEND_TRO: currentState = currentState.receiveINVITEsendTRO(this); break;
            case RECV_BYE_SEND_OK: currentState = currentState.receiveByeSendOK(this); break;
            case RECV_TRO_SEND_ACK: currentState = currentState.receiveTROsendACK(this); break;
            case SEND_TRO_RECV_ACK: currentState = currentState.sendTROreceiveACK(this); break;
            case RECV_OK: currentState = currentState.receiveOK(this); break;
            case ERROR: currentState = currentState.sendError(); break;
        }
    }

    public void printState()
    {
        currentState.printState();
    }

    public boolean busy()
    {
        return currentState.busy();
    }

    //process the clients incoming messages from socket
    public void startClientInputThread()
    {
        Thread clientInputThread = new Thread()
        {
            @Override
            public void run()
            {
                BufferedReader clientIn;
                String clientInputLine="";
                try
                {
                    clientIn = new BufferedReader(new InputStreamReader(getClientSocket().getInputStream()));
                    while((clientInputLine = clientIn.readLine()) != null)
                    {
                        changeState(clientInputLine);
                    }
                }
                catch (IOException e)
                {
                    System.out.println("\n===========================");
                    System.out.println("could not read from client: " + e.toString());
                    currentState = currentState.sendError();
                }
               // System.out.println("Now closing startClientInputThread");
            }
        };
        clientInputThread.start();
    }

    //when user is making a call, this is the timeout of ringing
    public void startTimerThread()
    {
        Thread ringingThread = new Thread()
        {
            @Override
            public void run()
            {
                run = true;
                int ringingInt = 0;
                int maxRinging = 10;
                while(run)
                {
                    ringingInt++;
                    System.out.println("Ringing " + ringingInt + " (" + maxRinging + ")");
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println(e.toString());
                    }
                    if(ringingInt == maxRinging)
                    {
                        System.out.println("Nobody answered");
                        try
                        {
                            getClientSocket().close();
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                        run = false;
                        break;
                    }
                }
                if(ringingInt == maxRinging)
                {
                    changeState("SEND_BYE");
                }
                System.out.println("stänger ringing tråd");
            }
        };
        ringingThread.start();
    }

    public void stopTimerThread()
    {
        run = false;
    }

    public void startAudioStream()
    {
        try
        {
            ad = new AudioStreamUDP();
        }
        catch(IOException e)
        {
            System.out.println("Error in startAudioStream" + e.toString());
        }
    }

    public AudioStreamUDP getAd() {
        return ad;
    }

    public void connectAudioStream(int port, AudioStreamUDP ad)
    {
        try {
            InetAddress ip = InetAddress.getByName(clientSocket.getInetAddress().getHostAddress());
            ad.connectTo(ip, port);
        } catch (IOException e) {
            System.out.println("Error while connecting to ip and port specified by peer: " +e.toString());
        }
        ad.startStreaming();
    }

    public int getAudioStreamPort() {
        return audioStreamPort;
    }

    public void setAudioStreamPort(int audioStreamPort) {
        this.audioStreamPort = audioStreamPort;
    }

    public void stopCall()
    {
        ad.stopStreaming();
        ad.close();
        ad = null;
    }

}
