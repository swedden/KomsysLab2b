package State;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Idle extends CallState {

    public Idle() {;}

    public CallState userInputReceivedSendInvite(CallHandler ch)
    {
        String ipAddress;
        int port;
        PrintWriter out = null;
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter IP-address: ");
            ipAddress = scanner.nextLine();
            System.out.println("Enter port: ");
            port = scanner.nextInt();
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

    public CallState receiveINVITEsendTRO(CallHandler ch){

        PrintWriter out;
        System.out.println("You have received an invite, do you want to accept? (Y/N)");
        String ans = ch.getInputScanner().nextLine().toUpperCase();
        System.out.println(ans);
        if(ans.equals("Y"))
        {
            System.out.println("hello we dont go here");
            try
            {
                out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
                out.println("TRO");
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
            return new ReceiveCallRequest();
        }
        System.out.println("hoppar till idle");
        return new Idle();
    }


    public void printState() {
        System.out.println("State: IDLE");
    }
}
