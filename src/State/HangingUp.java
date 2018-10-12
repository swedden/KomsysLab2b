package State;

public class HangingUp extends Busy
{
    public HangingUp() {;}

    public CallState receiveOK(CallHandler ch)
    {
        //skickar ingenting men gör ny idle.
        ch.stopCall();
        return new Idle();
    }

    public void printState()
    {
        System.out.println("State: State.HangingUp");
    }

}
