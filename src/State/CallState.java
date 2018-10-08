package State;

public abstract class CallState
{

    public CallState userInputReceivedSendInvite(CallHandler ch)
    {
        //skicka error
        return new Idle();
    }

    public CallState userInputReceivedSendBYE(CallHandler ch)
    {
        //skicka error
        return new Idle();
    }

    public CallState receiveINVITEsendTRO(CallHandler ch)
    {
        return new Idle();
    }

    public CallState receiveByeSendOK(CallHandler ch)
    {
        //skicka error
        return new Idle();
    }
    public CallState receiveOK()
    {
        //skicka error.
        return new Idle();
    }
    public CallState receiveTROsendACK(CallHandler ch)
    {
        System.out.println("i callstate receiveTROsendACK..");
        //skicka error
        return new Idle();
    }

    public CallState sendTROreceiveACK(CallHandler ch)
    {
        //skicka error
        return new Idle();
    }

    public CallState receiveNothingSendBYE(CallHandler ch)
    {
        //skicka error
        return new Idle();
    }

    public void printState(){}

    public CallState sendError()
    {
        //behöver ha socket och referenser, om den här skickas ska programmet stängas av och meddela användaren
        System.out.println("an error occurred: ");
        return new Idle();
    }

    public boolean busy()
    {
        return false;
    }

}
