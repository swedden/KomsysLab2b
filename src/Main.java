import client.Client;
import server.Server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        int port = 0;
        String ip = "";

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter server(s) or client(c): ");
        String choice = sc.nextLine();

        if(choice.equals("server") || choice.equals("s"))
        {
            System.out.print("Enter port: ");
            try
            {
                port = Integer.parseInt(sc.nextLine());
                new Server(port);
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                System.out.println("Incorrect port input or server unable to start a new server: " + e.getMessage());
                System.exit(1); //
            }
        }
        else if(choice.equals("client") || choice.equals("c"))
        {
            System.out.print("Enter ip address: ");
            ip = sc.nextLine();
            System.out.print("Enter port: ");
            try
            {
                port = Integer.parseInt(sc.nextLine());
                new Client(ip, port);
            }
            catch (Exception e)
            {
                System.out.println("Incorrect port input or client couldn't connect: " + e.getMessage());
                System.exit(1); //
            }
        }
        else
        {
            System.out.println("\"" + choice + "\"" + " is not server or client.. program now exiting");
        }
    }
}
