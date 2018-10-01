package State;

public abstract class Busy extends CallState {

    public boolean busy()
    {
        return true;
    }

    public void printState() {
        System.out.println("State: BUSY");
    }


}
