package State;

import java.util.Scanner;

/**
 * Pga att man i olika delar av programmet vill skriva in från System.in och programmet använder trådar kan man inte ha
 * scanners på alla ställen då dessa krockar med varnnadra (med System.in). Denna klassen sköter det så endas och endast
 * en i taget kan läsa in från System.in (scanner). Klassen fungerar så att den läser in allt från användaren, och för
 * att skilja åt vem som ska ha meddelandet används classString.
 */

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
                    //System.out.println("Start reading input");
                    inputMessage = scanner.nextLine().toUpperCase();
                    //System.out.println("inputMessage: " + inputMessage + ", length: " + inputMessage.length());
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
        //System.out.println("inputMessage.length() == " + inputMessage.length() + ", inputmessage: " + inputMessage + ", classString: " + classString);
        if(!(inputMessage.length() == 0))
        {
            //System.out.println("inputMessage.length() == " + inputMessage.length() + ", inputmessage: " + inputMessage + ", classString: " + classString);
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
