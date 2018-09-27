public class HangingUp extends Busy {

    public HangingUp() {;}

    public CallState receiveOK() {
        //skickar ingenting men gör ny idle
        return new Idle();
    }


}
