



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    public static void main(String[] args)  throws IOException {
        System.out.println("Attempting to Start Connect5 Server..");

        int port = Integer.parseInt(args[0]);


        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Starting the socket server at port:" + port);

        ArrayList<Socket> players = new ArrayList<Socket>();


        int playerCount = 0;

        while (playerCount < 2) {
            playerCount++;
            System.out.println("Waiting for Player " + playerCount);
            players.add(serverSocket.accept());
            System.out.println("Player " + playerCount + " Joined");


        }
        Thread thread = new Thread(new ConnectFiveGame(players));
        thread.start();

    }

	
}
