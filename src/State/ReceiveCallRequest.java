package State;

public class ReceiveCallRequest extends Busy
{
    public CallState sendTROreceiveACK(CallHandler ch)
    {
        ch.connectAudioStream(ch.getAudioStreamPort(), ch.getAd());
        return new Conversation();
    }

    public CallState receiveNothingSendBYE(CallHandler ch) { //timeout gör så att den här går igång

        System.out.println("BYE");
        return new Idle();
    }

    public void printState() {
        System.out.println("State: State.ReceiveCallRequest");
    }

}
