package State;

import java.io.*;
import java.net.Socket;

public class Idle extends CallState
{

    public Idle() {;}



    public CallState userInputReceivedSendInvite(CallHandler ch)
    {
        String ipAddress;
        String portString;
        int port;
        PrintWriter out = null;

        try
        {
            System.out.println("===========================");
            System.out.print("Enter IP-address: ");

            ch.getInputScanner().setClassString("userInputReceivedSendInvite");
            while(true)
            {
                if(ch.getInputScanner().hasInput("userInputReceivedSendInvite"))
                {
                    ipAddress = ch.getInputScanner().getUserInput("userInputReceivedSendInvite");
                    break;
                }
            }
            System.out.print("Enter port: ");
            ch.getInputScanner().setClassString("userInputReceivedSendInvite");
            while(true)
            {
                if(ch.getInputScanner().hasInput("userInputReceivedSendInvite"))
                {
                    portString = ch.getInputScanner().getUserInput("userInputReceivedSendInvite");
                    break;
                }
            }

            port = Integer.parseInt(portString);
            try
            {
                Socket socket = new Socket(ipAddress, port);
                ch.setClientSocket(socket);
                ch.startClientInputThread();
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("INVITE");
                System.out.println("===========================");
                System.out.println("Now waiting for the other person to answer");

                ch.startRingingThread();
            }
            catch (Exception e)
            {
                System.out.println("Could not make connection to: " + ipAddress + " on port: " + port);
            }
        }
        catch (Exception e)
        {
            System.out.println("Incorrect ip or port");
            return new Idle();
        }

        ch.getInputScanner().setClassString("MAIN");
        return new MakingCallRequest();
    }

    public CallState receiveINVITEsendTRO(CallHandler ch)
    {
        PrintWriter out;
        String ans;
        System.out.println("===========================");
        System.out.print("You have received an invite to a call, do you want to accept? (Y/N): ");

        //här kontrorllerar vi inte om det är ett protokoll input från programmet utan detta är en user input (om man vill svara på samtalet)
        ch.getInputScanner().setClassString("receiveINVITEsendTRO");
        while(true)
        {
            if(ch.getInputScanner().hasInput("receiveINVITEsendTRO"))
            {
                ans = ch.getInputScanner().getUserInput("receiveINVITEsendTRO");
                ans = ans.toUpperCase();
                break;
            }
        }

        if(ans.equals("Y"))
        {
            try
            {
                out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
                out.println("TRO");
                //System.out.println("Have now sent TRO");
            }
            catch (IOException e)
            {
                System.out.println("Couldn't get an outputstream for the client: " + e.toString());
                //sendError(e.toString());
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
            ch.getInputScanner().setClassString("MAIN");
            return new ReceiveCallRequest();
        }
        ch.getInputScanner().setClassString("MAIN");
        System.out.println("You chose to not answer");

        try
        {
            ch.getClientSocket().close();
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }

        //returns to main manu, print it to show the user
        System.out.println("===========================");
        System.out.println("Menu");
        System.out.println("Call: sends invite");
        System.out.println("Exit: exits.");
        System.out.print("Input: ");

        return new Idle();
    }


    public void printState()
    {
        System.out.println("State: IDLE");
    }
}
