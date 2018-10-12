package State;

import java.util.Scanner;

public class InputScanner
{
    private Scanner scanner;
    private String classString;
    private String inputMessage;
    public InputScanner()
    {
        scanner = new Scanner(System.in);
        inputMessage = "";
        Thread inputThread = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    inputMessage = scanner.nextLine();
                }
            }
        };
        inputThread.start();
    }

    //overrids old classString so that a new class can get the inputMessage
    public void setClassString(String classString)
    {
        this.classString = classString;
    }

    //returns inputmessage if this.classString classString
    public String getUserInput(String classString)
    {
        if(this.classString.equals(classString))
        {
            String tmp = inputMessage;
            inputMessage = "";
            return tmp;
        }
        else
        {
            return "";
        }
    }

    //returns true if there is a new message from user (System.in) and is for the right class
    public boolean hasInput(String classString)
    {
        try
        {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
        if(!(inputMessage.length() == 0))
        {
            if(this.classString.equals(classString))
            {
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }
}
