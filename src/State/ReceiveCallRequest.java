package State;

public class ReceiveCallRequest extends Busy
{
    public CallState sendTROreceiveACK(CallHandler ch)
    {
        //System.out.println("sendtroreceiveack");
        //skicka ingenting men returnera ny conversation

        //skcika TRO för att svara
        //ingenting/bye för att gå tillbaka till IDLE (jag själv)
        //if yes, send TRO, return new conversation, else idle
        ch.startAudioStream();
        return new Conversation();
    }

    public CallState receiveNothingSendBYE(CallHandler ch) { //timeout gör så att den här går igång

        System.out.println("BYE");
        return new Idle();
    }

    public void printState() {
        System.out.println("State: State.ReceiveCallRequest");
    }

    //if busy or timeout, go back to idle

}
