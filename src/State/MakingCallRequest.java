package State;

import java.io.IOException;
import java.io.PrintWriter;

public class MakingCallRequest extends Busy
{
    public MakingCallRequest()
    {

    }

    public CallState receiveTROsendACK(CallHandler ch)
    {
        //System.out.println("clientInputline: " + clientInputLine);
        //ch.changeState(clientInputLine);
        //System.out.println("Nu i receiveTROsendACK");
        ch.stopTimerThread();
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
            out.println("ACK");
            ch.startAudioStream();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //System.out.println("ACK");
        return new Conversation();
    }

    public CallState receiveNothingSendBYE(CallHandler ch) { //eg. times out, send bye and go back to idle
        System.out.println("BYE");
        return new Idle();
    }

    public void printState()
    {
        System.out.println("State: State.MakingCallRequest");
    }

}
