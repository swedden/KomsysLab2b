import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("peer to peer voice chat");
        CallHandler ch = new CallHandler();
        Scanner scan = new Scanner(System.in);
        String choice;
        do
        {
            System.out.println(ch.busy());
            ch.printState();
            System.out.println();
            System.out.println("Current call state:");
            System.out.println("SEND_INVITE: user input, sends invite");
            System.out.println("INV_TRO: receive invite, send TRO");
            System.out.println("BYE_OK: receive BYE, send OK");
            System.out.println("TRO_ACK: receive TRO send ACK");
            System.out.println("RECV_ACK: receive ACK, send nada");
            System.out.println("RECV_OK: receive OK, send nada");
            System.out.println("SEND_BYE: user input, send BYE");
            System.out.println("Exit: exits.");

            //input happens here
            choice = scan.nextLine().toUpperCase();
            switch(choice)
            {
                case "EXIT": break;
                case "SEND_INVITE": ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV); break;
                case "INV_TRO": ch.processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO); break;
                case "BYE_OK": ch.processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK); break;
                case "TRO_ACK": ch.processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);break;
                case "RECV_ACK": ch.processNextEvent(CallHandler.CallEvent.RECV_ACK);break;
                case "RECV_OK": ch.processNextEvent(CallHandler.CallEvent.RECV_OK);break;
                case "SEND_BYE": ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE); break;
                default: ch.processNextEvent(CallHandler.CallEvent.ERROR); break;
            }
            System.out.println("");
        }
        while(!choice.equals("EXIT"));
    }
}
