public class Idle extends CallState {

    public Idle() {;}

    public CallState userInputReceivedSendInvite() {
        System.out.println("INVITE");
        return new MakingCallRequest();
    } //hur skickar den receivedInviteSendTRO

    public CallState receivedInviteSendTRO() {
        System.out.println("TRO");
        return new ReceiveCallRequest();
    } //hur skickar den receiveTROsendACK


    public void printState() {
        System.out.println("State: IDLE");
    }
}
