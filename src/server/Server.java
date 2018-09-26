package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Fredrik on 2018-09-26.
 */
public class Server
{
    private ServerSocket serverSocket;

    public Server(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e1)
        {
            System.out.println("Could not start Server: " + e1.toString());
        }
        catch (Exception e)
        {

        }
    }
}
