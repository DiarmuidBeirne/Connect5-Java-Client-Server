
/*
connect5 
Author: 15331436 | Diarmuid Beirne

20 Feb 2019
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException
    {
        int port = Integer.parseInt(args[0]);
        Socket clientSocket = null;
        try{
        clientSocket = new Socket("localhost", port);
        //create a thread for sending out data
        ThreadOut threadOut = new ThreadOut(clientSocket);
        Thread threadO = new Thread(threadOut);
        threadO.start();
        //create a thread for taking in data
        ThreadIn threadIn = new ThreadIn(clientSocket);
        Thread threadI =new Thread(threadIn);
        threadI.start();

        } catch (Exception e) {System.out.println(e.getMessage());}

    }
}

    class ThreadIn implements Runnable
{
	Socket socket=null;
	BufferedReader recieve=null;
	
	public ThreadIn(Socket s) {
		this.socket = s;
	}

	public void run() {
		try{
		recieve = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));//get inputstream
		String msgRecieved = null;
		while((msgRecieved = recieve.readLine())!= null)
		{
			System.out.println(msgRecieved);
			
		}
		}catch(Exception e){System.out.println(e.getMessage());}
	}
}



class ThreadOut implements Runnable
{
	Socket socket=null;
	PrintWriter print=null;
	BufferedReader brinput=null;
	
	public ThreadOut(Socket s)
	{
		this.socket = s;
	}
	public void run(){
		try{
		if(socket.isConnected())
		{
			
			this.print = new PrintWriter(socket.getOutputStream(), true);	
		while(true){
			
			brinput = new BufferedReader(new InputStreamReader(System.in));
			String msgtoServerString=null;
			msgtoServerString = brinput.readLine();
			this.print.println(msgtoServerString);
			this.print.flush();
			if(msgtoServerString.equals("QUIT"))
			break;
			}//end while
		socket.close();}}catch(Exception e){System.out.println(e.getMessage());}
	}
}

