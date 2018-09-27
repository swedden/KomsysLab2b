public class MakingCallRequest extends Busy{

    public MakingCallRequest() {;}
    //kanske kalla getTROsendACK
    public CallState receiveTROsendACK(){ //skulle t.ex. kunna ha socket till peer som inparameter för att kunna skapa strömmar senare
        System.out.println("ACK"); //är det här man skickar utsignaler???
        return new Conversation(); //ha med socketen till peer här
    } //hur skickar den receiveACK?

}
