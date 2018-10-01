package State;
import java.net.Socket;
import java.util.Scanner;

public class CallHandler {
    public enum CallEvent {
        USER_INPUT_RECV_SEND_INV, USER_INPUT_RECV_SEND_BYE, RECV_INV_SEND_TRO, RECV_BYE_SEND_OK,
        RECV_TRO_SEND_ACK, SEND_TRO_RECV_ACK, RECV_OK, RECV_NOTHING_SEND_BYE, ERROR};

    private CallState currentState;
    private Socket clientSocket;
    private Scanner scanner;

    public Socket getClientSocket()
    {
        return clientSocket;
    }
    public void setClientSocket(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public CallHandler()
    {
        currentState = new Idle();
    }

    public void processNextEvent (CallEvent event) {
        switch(event)
        {
            case USER_INPUT_RECV_SEND_INV: currentState = currentState.userInputReceivedSendInvite(this); break;
            case USER_INPUT_RECV_SEND_BYE: currentState = currentState.userInputReceivedSendBYE(); break;
            case RECV_NOTHING_SEND_BYE: currentState = currentState.receiveNothingSendBYE();
            case RECV_INV_SEND_TRO: currentState = currentState.receiveINVITEsendTRO(this); break;
            case RECV_BYE_SEND_OK: currentState = currentState.receiveByeSendOK(); break;
            case RECV_TRO_SEND_ACK: currentState = currentState.receiveTROsendACK(); break;
            case SEND_TRO_RECV_ACK: currentState = currentState.sendTROreceiveACK(); break;
            case RECV_OK: currentState = currentState.receiveOK(); break;
            case ERROR: currentState = currentState.sendError(); break;
        }
    }

    public void printState() {
        currentState.printState();
    }

    public boolean busy() { return currentState.busy();}
}
