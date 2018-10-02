package State;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
            //Scanner scanner = new Scanner(System.in);
            System.out.println("Enter IP-address: ");

            ch.getInputScanner().setClassString("userInputReceivedSendInvite");
            while(true)
            {
                if(ch.getInputScanner().hasInput("userInputReceivedSendInvite"))
                {
                    ipAddress = ch.getInputScanner().getUserInput("userInputReceivedSendInvite");
                    break;
                }
            }

            //ipAddress = scanner.nextLine();
            System.out.println("Enter port: ");

            ch.getInputScanner().setClassString("userInputReceivedSendInvite");
            while(true)
            {
                if(ch.getInputScanner().hasInput("userInputReceivedSendInvite"))
                {
                    portString = ch.getInputScanner().getUserInput("userInputReceivedSendInvite");
                    break;
                }
            }

            ch.getInputScanner().setClassString("MAIN");

            port = Integer.parseInt(portString);

            //port = scanner.nextInt();
            try
            {
                Socket socket = new Socket(ipAddress, port);
                ch.setClientSocket(socket);
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("INVITE");
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

        return new MakingCallRequest();
    }

    public CallState receiveINVITEsendTRO(CallHandler ch)
    {
        PrintWriter out;
        String ans;
        System.out.println("You have received an invite, do you want to accept? (Y/N)");
        ch.getInputScanner().setClassString("receiveINVITEsendTRO");
        while(true)
        {
            if(ch.getInputScanner().hasInput("receiveINVITEsendTRO"))
            {
                ans = ch.getInputScanner().getUserInput("receiveINVITEsendTRO");
                break;
            }
        }

        if(ans.equals("Y"))
        {
            try
            {
                out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
                out.println("TRO");
                System.out.println("Have now sent TRO");
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
        System.out.println("hoppar till idle");
        return new Idle();
    }


    public void printState()
    {
        System.out.println("State: IDLE");
    }
}
