package State;

import java.net.Socket;
import java.util.Scanner;

public class Idle extends CallState {

    public Idle() {;}

    public CallState userInputReceivedSendInvite(CallHandler ch)
    {
        String ipAdress;
        int port;

        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter IP-adress: ");
            ipAdress = scanner.nextLine();
            System.out.println("Enter port: ");
            port = scanner.nextInt();

            try
            {
                Socket socket = new Socket(ipAdress, port);
                System.out.println("Has now made a connection!");
            }
            catch (Exception e)
            {
                System.out.println("Could not make connection to: " + ipAdress + " on port: " + port);
            }
        }
        catch (Exception e)
        {
            System.out.println("Incorrect ip or port");
            return new Idle();
        }
        System.out.println("INVITE");

        return new MakingCallRequest();
    } //hur skickar den receivedInviteSendTRO

    public CallState receivedInviteSendNothing() {
        return new ReceiveCallRequest();
    } //hur skickar den receiveTROsendACK


    public void printState() {
        System.out.println("State: IDLE");
    }
}
