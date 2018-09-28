public class ReceiveCallRequest extends Busy {

    public CallState receiveACK() {
        //skicka ingenting men returnera ny conversation
        return new Conversation();
    }

    public CallState receiveNothingSendBYE() { //timeout gör så att den här går igång
        System.out.println("BYE");
        return new Idle();
    }

    public void printState() {
        System.out.println("State: ReceiveCallRequest");
    }

    //if busy or timeout, go back to idle

}
