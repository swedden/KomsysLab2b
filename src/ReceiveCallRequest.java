public class ReceiveCallRequest extends Busy {

    public CallState receiveACK() {
        //skicka ingenting men returnera ny conversation
        return new Conversation();
    }

    //if busy or timeout, go back to idle

}
