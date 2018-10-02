package State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakingCallRequest extends Busy{



    public MakingCallRequest(CallHandler ch)
    {
    //start timer for timeout
        BufferedReader clientIn;
        String clientInputLine;
        try {
            clientIn = new BufferedReader(new InputStreamReader(ch.getClientSocket().getInputStream()));
            while((clientInputLine = clientIn.readLine()) != null)
            {
                System.out.println("Received TRO in while: " + clientInputLine);
                ch.changeState(clientInputLine);
                break;
            }

        } catch (IOException e) {
            System.out.println("could not start input stream from client: " + e.toString());
            sendError();
        }
    }

    public CallState receiveTROsendACK(CallHandler ch){ //skulle t.ex. kunna ha socket till peer som inparameter för att kunna skapa strömmar senare
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
