package State;

import java.io.IOException;
import java.io.PrintWriter;

public class Conversation extends Busy
{
    private boolean receviedBye; //för att brya looparna för input från användaren
    public Conversation()
    {
        System.out.println("ring ringassdx");
    }

    public CallState userInputReceivedSendBYE(CallHandler ch)
    {
        receviedBye = false;
        String input = "";
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
            out.println("BYE");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return new HangingUp();
    }


    public CallState receiveByeSendOK(CallHandler ch)
    {
        System.out.println("Client hang up on you, sorry m8 :'(");
        String input = "";
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
            out.println("OK");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return new Idle();
    }


    public void printState()
    {
        System.out.println("State: CONVERSATION");
    }

}
