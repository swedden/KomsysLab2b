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
        ch.stopTimerThread();
        PrintWriter out = null;
        try
        {
            ch.startAudioStream();
            out = new PrintWriter(ch.getClientSocket().getOutputStream(), true);
            out.println("ACK " + ch.getAd().getLocalPort());
            ch.connectAudioStream(ch.getAudioStreamPort(), ch.getAd());
        }
        catch(IOException e)
        {
            System.out.println("Error while starting audiostream or while writing to client: " +e.toString());
        }

        return new Conversation();
    }

    public CallState receiveNothingSendBYE(CallHandler ch) {
        System.out.println("BYE");
        return new Idle();
    }

    public void printState()
    {
        System.out.println("State: State.MakingCallRequest");
    }

}
