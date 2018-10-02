package State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        switch(msg)
        {
            case "EXIT": break;
            case "SEND_INVITE": processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV); break;
            case "INVITE": processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO); break;
            case "BYE": processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK); break;
            case "TRO": processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);break;
            case "ACK": processNextEvent(CallHandler.CallEvent.SEND_TRO_RECV_ACK);break;
            case "OK": processNextEvent(CallHandler.CallEvent.RECV_OK);break;
            case "SEND_BYE": processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE); break;
            default: processNextEvent(CallEvent.ERROR); break;
        }
    }

    public void processNextEvent (CallEvent event)
    {
        switch(event)
        {
            case USER_INPUT_RECV_SEND_INV: currentState = currentState.userInputReceivedSendInvite(this); break;
            case USER_INPUT_RECV_SEND_BYE: currentState = currentState.userInputReceivedSendBYE(this); break;
            case RECV_NOTHING_SEND_BYE: currentState = currentState.receiveNothingSendBYE();
            case RECV_INV_SEND_TRO: currentState = currentState.receiveINVITEsendTRO(this); break;
            case RECV_BYE_SEND_OK: currentState = currentState.receiveByeSendOK(this); break;
            case RECV_TRO_SEND_ACK: currentState = currentState.receiveTROsendACK(this); break;
            case SEND_TRO_RECV_ACK: currentState = currentState.sendTROreceiveACK(); break;
            case RECV_OK: currentState = currentState.receiveOK(); break;
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

    public void startClientInputThread()
    {
        Thread clientInputThread = new Thread()
        {
            @Override
            public void run()
            {
                BufferedReader clientIn;
                String clientInputLine="";
                try {
                    clientIn = new BufferedReader(new InputStreamReader(getClientSocket().getInputStream()));
                    while((clientInputLine = clientIn.readLine()) != null)
                    {
                        System.out.println("Received from client: " + clientInputLine);
                        changeState(clientInputLine);
                    }
                } catch (IOException e) {
                    System.out.println("could not read from client: " + e.toString());
                    currentState = currentState.sendError();
                }
                System.out.println("Now closing startClientInputThread");
            }
        };
        clientInputThread.start();
    }
}
