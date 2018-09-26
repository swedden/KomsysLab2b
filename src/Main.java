import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main
{

    private static boolean run = true;

    public static void main(String[] args)
    {
        System.out.println("Peer-to-Peer chat/voice program");

        //listening for incoming calls
        Thread listeningThread = new Thread()
        {
            @Override
            public void run()
            {
                while(run)
                {
                    try
                    {
                        ServerSocket serverSocket = new ServerSocket();
                        System.out.println("Is now waiting for a connection..");
                        //stuff
                    }
                    catch (IOException e)
                    {
                        System.out.println("Exception in listening thread: " + e.toString());
                    }
                    catch (Exception e)
                    {
                        System.out.println("Exception " + e.toString());
                        System.out.println("Now closing listening thread");
                        run = false;
                    }
                }
            }
        };
        listeningThread.start();

        //hoppa till IDEL-state och ringa därifrån
    }
}
