package State;

import java.io.IOException;
import java.io.PrintWriter;

public class Conversation extends Busy
{
    private boolean receviedBye; //för att brya looparna för input från användaren
    public Conversation(CallHandler ch)
    {
        System.out.println("ring ringassdx");
    }

    public CallState userInputReceivedSendBYE(CallHandler ch)
    {
        receviedBye = false;
        String input = "";
        ch.getInputScanner().setClassString("Conversation");
        while(true)
        {
            while(true)
            {
                if(receviedBye)
                {
                    break;
                }
                else if(ch.getInputScanner().hasInput("Conversation"))
                {
                    input = ch.getInputScanner().getUserInput("Conversation");
                    break;
                }
            }
            if(receviedBye)
            {
                break;
            }
            else if(input.equals("BYE"))//if user types BYE
            {
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
                break;
            }
            else
            {
                System.out.println(input + " is not BYE");
            }
        }
        ch.getInputScanner().setClassString("MAIN");
        return new HangingUp();
    }


    public CallState receiveByeSendOK()
    {
        receviedBye = true;
        System.out.println("OK");
        return new Idle();
    } //hur skickar den receiveOK?


    public void printState() {
        System.out.println("State: CONVERSATION");
    }

}
