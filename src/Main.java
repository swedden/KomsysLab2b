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
    private static Thread listeningThread;

    public static void main(String[] args)
    {
        System.out.println("===== Peer to peer voice chat =====");

        //===============================================
        System.out.print("What port do you want to use: ");
        Scanner scanner = new Scanner(System.in);

        int port = scanner.nextInt();
        ch = new CallHandler();
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            System.out.println("Could not start listening for clients on port " + port + " : " + e.toString());
            System.exit(-1);
        }

        listeningThread();

        ch.getInputScanner().setClassString("MAIN");

        showMainMenu();

        String choice;
        do
        {
            while(true)
            {
                if(ch.getInputScanner().hasInput("MAIN"))
                {
                     choice = ch.getInputScanner().getUserInput("MAIN");
                     choice = choice.toUpperCase();
                     break;
                }
            }
            if(choice.equals("CALL")) //when user typs call
            {
                ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV);
            }
            else if(choice.equals("BYE") && ch.busy()) //when user is in a conversation and typs bye (ends the conversation)
            {
                ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE);
                try
                {
                    ch.getClientSocket().close();
                }
                catch(IOException e)
                {
                    System.out.println(e.toString());
                }
                showMainMenu();
            }
            else if(choice.equals("EXIT")) //exit the program
            {
                break;
            }
            else
            {
                System.out.println("Bad input");
                showMainMenu();
            }
        }
        while(!choice.equals("EXIT"));

        try
        {
            listeningThread.interrupt();
            listeningThread.join(1000);
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

    private static void showMainMenu()
    {
        ch.printState();
        System.out.println("busy: " + ch.busy());
        System.out.println("===========================");
        System.out.println("Menu");
        System.out.println("Call: sends invite");
        System.out.println("Exit: exits.");
        System.out.print("Input: ");
    }

    private static Socket acceptSocket = null;

    public static void listeningThread()
    {

        listeningThread = new Thread()
        {
            @Override
            public void run()
            {
            while(true)
            {
                try
                {
                    acceptSocket = serverSocket.accept(); //skapar en ny för om clientSocket redan används
                    System.out.println("ServerSocket, has now made a connection");
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
                        System.out.println("Somebody else is calling, now closing that connection");
                        acceptSocket.close();
                        acceptSocket = null;
                    }
                    catch (IOException e)
                    {
                        System.out.println("Could not close socket with new client: " + e.toString());
                    }
                }
                else
                {
                    //ny tråd här kanske? =====================================================================
                    //ringing
                    Thread acceptThread = new Thread()
                    {
                        @Override
                        public void run()
                        {
                            BufferedReader clientIn = null;
                            try
                            {
                                clientSocket = acceptSocket; //gör acceptSocket permanent/till clientSocket
                                ch.setClientSocket(clientSocket);
                                clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                String clientInputLine = null;

                                while ((clientInputLine = clientIn.readLine().toUpperCase()) != null)
                                {
                                    //System.out.println("i main while: " + clientInputLine);
                                    ch.changeState(clientInputLine);
                                }
                                //ch.changeState("OK");
                            }
                            catch (IOException e)
                            {
                                System.out.println("ServerSocket, Could not read stream from client: " + e.toString());
                                //ch.changeState("OK");
                            }
                            catch (NullPointerException e)
                            {
                                System.out.println("Peer closed call: " + e.toString());
                            }
                            System.out.println("Now closing acceptThread (lägger på)");
                        }
                    };
                    acceptThread.start();
                }
            }
            }
        };
        listeningThread.start();
    }
}
