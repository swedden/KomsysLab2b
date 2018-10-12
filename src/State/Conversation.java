package State;

import java.io.IOException;
import java.io.PrintWriter;

public class Conversation extends Busy
{
    public Conversation()
    {
        System.out.println("You are now in an IP phone call");
    }

    public CallState userInputReceivedSendBYE(CallHandler ch)
    {
        try
        {
            PrintWriter out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
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
        try
        {
            PrintWriter out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
            out.println("OK");
            ch.stopCall();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //returns to main manu, print it to show the user.
        System.out.println("===========================");
        System.out.println("Menu");
        System.out.println("Call: sends invite");
        System.out.println("Exit: exits.");
        System.out.print("Input: ");

        return new Idle();
    }


    public void printState()
    {
        System.out.println("State: CONVERSATION");
    }

}
