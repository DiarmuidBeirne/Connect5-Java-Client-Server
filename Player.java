

/*
connect5 
Author: 15331436 | Diarmuid Beirne

21 Feb 2019
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Socket socket;
    private String playerName;
    private char counter;

    public Player(Socket socket, PrintWriter printWriter, BufferedReader bufferedReader, char counter) {
        this.socket = socket;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.counter = counter;
   
        try {
            this.printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            

        }catch(Exception ex){System.out.println(ex);}
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public char getCounter() {
        return counter;
    }

    public void setCounter(char counter) {
        this.counter = counter;
    }
}
