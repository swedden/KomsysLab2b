public class Conversation extends Busy
{
    public CallState userInputReceivedSendInvite() {
        System.out.println("INVITE");
        return new HangingUp();
    } //hur skickar den receiveByeSendOK?


    public CallState receiveByeSendOK() {
        System.out.println("OK");
        return new Idle();
    } //hur skickar den receiveOK?

}
