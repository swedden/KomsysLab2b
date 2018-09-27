public abstract class CallState {

    public CallState userInputReceivedSendInvite() {
        //skicka error
        return new Idle();
    }
    public CallState receivedInviteSendTRO() {
        //skicka error
        return new Idle();
    }
    public CallState receiveByeSendOK() {
        //skicka error
        return new Idle();
    }
    public CallState receiveOK() {
        //skicka error
        return new Idle();
    }
    public CallState receiveTROsendACK() {
        //skicka error
        return new Idle();
    }
    public CallState receiveACK() {
        //skicka error
        return new Idle();
    }
    public void printState(){}
    public void sendError(){
        //behöver ha socket och referenser, om den här skickas ska programmet stängas av och meddela användaren
    }

    public boolean busy(){
        return false;
    }

}
