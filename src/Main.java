import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("peer to peer voice chat");
        CallHandler ch = new CallHandler();
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        do
        {
            System.out.println(ch.busy());
            ch.printState();
            System.out.println();
            System.out.println("Call state progression");
            System.out.println("1. user input, sends invite");
            System.out.println("2. receive invite, send TRO");
            System.out.println("3. receive BYE, send OK");
            System.out.println("4. receive TRO send ACK");
            System.out.println("5. receive ACK, send nada");
            System.out.println("6. receive OK, send nada");
            System.out.println("7. user input, send BYE");
//		System.out.println("7. What is the door doing?");
            System.out.println("0. quit.");
            choice = scan.nextInt();
            switch(choice)
            {
                case 0: break;
                case 1: ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_INV); break;
                case 2: ch.processNextEvent(CallHandler.CallEvent.RECV_INV_SEND_TRO); break;
                case 3: ch.processNextEvent(CallHandler.CallEvent.RECV_BYE_SEND_OK); break;
                case 4: ch.processNextEvent(CallHandler.CallEvent.RECV_TRO_SEND_ACK);break;
                case 5: ch.processNextEvent(CallHandler.CallEvent.RECV_ACK);break;
                case 6: ch.processNextEvent(CallHandler.CallEvent.RECV_OK);break;
                case 7: ch.processNextEvent(CallHandler.CallEvent.USER_INPUT_RECV_SEND_BYE); break;
                default: ch.processNextEvent(CallHandler.CallEvent.ERROR); break;
                //case 7: dh.printState(); break;
            }
            System.out.println("");
        }
        while(choice != 0);
    }
}
