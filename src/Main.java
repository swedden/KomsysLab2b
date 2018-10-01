import State.CallHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main
{

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static CallHandler ch;

    public static void main(String[] args)
    {
        System.out.println("peer to peer voice chat");

        //===============================================
        System.out.println("What port do you want to use: ");
        Scanner scanner = new Scanner(System.in);

        int port = scanner.nextInt();
        ch = new CallHandler();
        //start server/listening form peer-connections
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.out.println("Could not start listening for clients on port " + port + " : " + e.toString());
        }
        Thread liseningThread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    Socket acceptSocket = null;
                    try
                    {
                        acceptSocket = serverSocket.accept(); //skapar en ny för om clientSocket redan används
                        System.out.println("Serversocket has now made a connection");
                    }
                    catch (IOException e)
                    {
                        System.out.println("Could not accept new client connection: " + e.toString());
                    }

                    if (ch.busy())
                    {
                        //skicka busy
                        try
                        {
                            acceptSocket.close();
                        }
                        catch (IOException e)
                        {
                            System.out.println("Could not close socket with new client: " + e.toString());
                        }
                    }
                    else
                    {
                        //ringing
                        BufferedReader clientIn = null;
                        try
                        {
                            clientSocket = acceptSocket; //gör acceptSocket permanent/till clientSocket
                            ch.setClientSocket(clientSocket);
                            clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            String clientInputLine = null;

                            while ((clientInputLine = clientIn.readLine().toUpperCase()) != null)
                            {
                                changeState(clientInputLine);
                            }
                        }
                        catch (IOException e)
                        {
                            System.out.println("Could not read stream from client: " + e.toString());
                        }
                    }
                }

            }
        };
        liseningThread.start();

        //===============================================


        String choice = "yeet";
        do
        {
            if(!ch.busy())
            {
                System.out.println(ch.busy());
                ch.printState();
                System.out.println();
                System.out.println("Current call state:");
                System.out.println("SEND_INVITE: user input, sends invite");
                System.out.println("INV_TRO: receive invite, send TRO");
                System.out.println("BYE_OK: receive BYE, send OK");
                System.out.println("TRO_ACK: receive TRO send ACK");
                System.out.println("SEND_TRO_RECV_ACK: receive ACK, send nada");
                System.out.println("RECV_OK: receive OK, send nada");
                System.out.println("SEND_BYE: user input, send BYE");
                System.out.println("Exit: exits.");
            }
            //input happens here
            choice = ch.getInputScanner().nextLine().toUpperCase();
            if(!ch.busy())
            {
                changeState(choice);
            }
        }
        while(!choice.equals("EXIT"));

        try
        {
            liseningThread.interrupt();
            liseningThread.join(1000);
            System.exit(0);
        }
        catch (Exception e)
        {
            System.out.println("Could not join thread: " + e.toString());

        }
        finally
        {
            System.exit(0);
        }
    }

    public static void changeState(String msg)
    {
        switch(msg)
        {
            case "EXIT": break;
            case "SEND_INVITE": ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV); break;
            case "INVITE": ch.processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO); break;
            case "BYE": ch.processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK); break;
            case "TRO": ch.processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);break;
            case "ACK": ch.processNextEvent(CallHandler.CallEvent.SEND_TRO_RECV_ACK);break;
            case "OK": ch.processNextEvent(CallHandler.CallEvent.RECV_OK);break;
            case "SEND_BYE": ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE); break;
            default: break;
        }
    }
}
