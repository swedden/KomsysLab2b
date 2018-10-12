package State;

import java.io.IOException;

public class HangingUp extends Busy
{
    public HangingUp() {;}

    public CallState receiveOK(CallHandler ch)
    {
        try {
            ch.getClientSocket().close();
        } catch (IOException e) {
            System.out.println("now closing socket in recvok: " + e.toString());
        }
        ch.stopCall();
        return new Idle();
    }

    public void printState()
    {
        System.out.println("State: State.HangingUp");
    }

}
