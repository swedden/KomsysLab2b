public class Conversation extends Busy
{
    public CallState userInputReceivedSendBYE() {
        System.out.println("BYE");
        return new HangingUp();
    }


    public CallState receiveByeSendOK() {
        System.out.println("OK");
        return new Idle();
    } //hur skickar den receiveOK?


    public void printState() {
        System.out.println("State: CONVERSATION");
    }

}
