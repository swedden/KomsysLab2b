package State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakingCallRequest extends Busy{



    public MakingCallRequest()
    {

    }

    public CallState receiveTROsendACK(CallHandler ch)
    {
        //skulle t.ex. kunna ha socket till peer som inparameter för att kunna skapa strömmar senare
        //start timer for timeout

        //System.out.println("clientInputline: " + clientInputLine);
        //ch.changeState(clientInputLine);


        System.out.println("HääÄÄÄÄÄÄÄR");
        System.out.println("ACK"); //är det här man skickar utsignaler???
        return new Conversation(); //ha med socketen till peer här
    } //hur skickar den receiveACK?

    public CallState receiveNothingSendBYE() { //eg. times out, send bye and go back to idle
        System.out.println("BYE");
        return new Idle();
    }

    public void printState() {
        System.out.println("State: State.MakingCallRequest");
    }

}
