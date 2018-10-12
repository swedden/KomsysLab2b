package State;

public abstract class CallState
{

    public CallState userInputReceivedSendInvite(CallHandler ch)
    {
        return new Idle();
    }

    public CallState userInputReceivedSendBYE(CallHandler ch)
    {
        return new Idle();
    }

    public CallState receiveINVITEsendTRO(CallHandler ch)
    {
        return new Idle();
    }

    public CallState receiveByeSendOK(CallHandler ch)
    {
        return new Idle();
    }
    public CallState receiveOK(CallHandler ch)
    {
        return new Idle();
    }
    public CallState receiveTROsendACK(CallHandler ch)
    {
        return new Idle();
    }

    public CallState sendTROreceiveACK(CallHandler ch)
    {
        return new Idle();
    }

    public CallState receiveNothingSendBYE(CallHandler ch)
    {
        return new Idle();
    }

    public void printState(){}

    public CallState sendError(String errorMessage)
    {
        System.out.println("an error occurred: " + errorMessage);
        return new Idle();
    }

    public boolean busy()
    {
        return false;
    }

}
