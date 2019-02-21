
/*
connect5 
Author: 15331436 | Diarmuid Beirne
20 Feb 2019
*/

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.lang.*;
public class ConnectFiveGame implements Runnable {

    private ArrayList<Player> players;
    private boolean gameActive = true; //is game still active i.e no winner
    private Player winner;

    final static int WIDTH = 9;
    final static int HEIGHT = 6;
    final static int BOTTOM_ROW = HEIGHT - 1;
    private char[][] grid = new char[WIDTH][HEIGHT];

    private int lastmoveX;
    private int lastmoveY;
    private PrintWriter PrintWriter1;
    private PrintWriter PrintWriter2;
    private BufferedReader BufferedReader1;
    private BufferedReader BufferedReader2;
    

    public ConnectFiveGame(ArrayList<Socket> players) {

        Player player1 = new Player(players.get(0), PrintWriter1, BufferedReader1, 'X');
        Player player2 = new Player(players.get(1), PrintWriter2, BufferedReader2, 'O');
        
        this.players = new ArrayList<Player>();
        this.players.add(player1);
        this.players.add(player2);

        SetUpGame();
        CreateGrid();
    }

    @Override
    public void run() {
            while(gameActive)
            {
                    takeTurn(players.get(0));
                    if(gameActive){takeTurn(players.get(1));}         
            }
            for(Player p: players)
            {
                p.getPrintWriter().println("------Game Over-------");
                p.getPrintWriter().println("The Winner of the game is " + winner.getPlayerName());
                p.getPrintWriter().flush();
            }
            System.out.println("Game Over. " + winner.getPlayerName() + " won");
            return;
    }

    private void takeTurn(Player player) {
        try{
            player.getPrintWriter().println("- - - - - - - - - - - - - - - ");
            player.getPrintWriter().println("Current Grid");
            player.getPrintWriter().println(PrintGrid());
            player.getPrintWriter().println("It's your turn " + player.getPlayerName() + ", Enter a column number (1-9):");
            player.getPrintWriter().flush();

            Boolean moveStatus =false;
            while (!moveStatus)
            {
                String input = player.getBufferedReader().readLine();
                moveStatus = DropCounter(player.getCounter(), Integer.parseInt(input));
             
             if(moveStatus){
                player.getPrintWriter().println(PrintGrid());
                if(gameOver(player.getCounter()))
                {
                    player.getPrintWriter().println("Congrats!!! You have Won");
                    winner = player;
                    gameActive = false;
                }else
                {
                    player.getPrintWriter().println("Please wait for other players move");
                }
                player.getPrintWriter().flush();
             }else{
                player.getPrintWriter().println("Invalid Input. Make sure column is not full ");
                player.getPrintWriter().println("and you input a number between 1-9 inclusive");
                player.getPrintWriter().flush();
             }       
            }
        }catch(Exception ex){System.out.println(ex);}
    }

    public void SetUpGame(){
        try{
        String welcomeMessage = "Welcome Player to Connect 5\nPlease Enter Your Name:";
        players.get(0).getPrintWriter().println(welcomeMessage);
        players.get(0).getPrintWriter().flush();
        players.get(1).getPrintWriter().println(welcomeMessage);
        players.get(1).getPrintWriter().flush();
        players.get(0).setPlayerName(players.get(0).getBufferedReader().readLine());
        players.get(1).setPlayerName(players.get(1).getBufferedReader().readLine());
        }catch(Exception ex){System.out.println(ex);}
    } 

    public void CreateGrid() {
        //create a empty grid
        for (int w = 0; WIDTH > w; w += 1) {
            for (int h = 0; HEIGHT > h; h += 1) {
                grid[w][h] = ' ';
            }
        }
    }

    public String PrintGrid() {
        //prints the board
        String toReturn = "";
        for (int h = 0; HEIGHT > h; h += 1) { 
            for (int w = 0; WIDTH > w; w += 1) {
                toReturn += " [";
                toReturn += grid[w][h];
                toReturn += "] ";
            }
            toReturn += "\n";
        }
        toReturn += "\n";
        return toReturn;
    }

    public Boolean DropCounter(char counter, int columnChoice){
        columnChoice--; //change values [1-9] to [0-8] for array 
       
            if(columnChoice >= WIDTH || columnChoice < 0)return false;//input not between 1-9
                for(int i = BOTTOM_ROW; i >= 0; i--){ //loop over all positions in column 
                if(grid[columnChoice][i] == ' '){ //checks if row above is empty
                    grid[columnChoice][i] = counter;
                    lastmoveX=columnChoice;
                    lastmoveY=i;
                    return true;//Move Completed
                }
            }
                return false;//Full column 
        }

        public Boolean gameOver(char counter){
           
            int count = 0;
           //check horizontally
                for(int i = HEIGHT - 1; i >= 0 ; i--) //makes more sense to start at bottom of grid
                    {
                        for(int j =0; j < WIDTH; j++)
                        {
                            if (grid[j][i] == counter){count++;}
                            else{count=0;}
                            if(count==5){return true;}
                        }
                    }
                    //check vertically
                    count = 0;
                for(int i = 0; i < WIDTH;  i++) 
                {
                    for(int j = HEIGHT - 1; j >= 0 ; j--) //start at bottom of grid
                    {
                        if (grid[i][j] == counter){count++;}
                        else{count=0;}
                        if(count==5){return true;}
                    }
                }

                //check diaginoly
                if (checkDiagonals(lastmoveX, lastmoveY, counter)){return true;}
           return false;
            }

            public Boolean checkDiagonals(int X, int Y, char counter)
            {
                char[] positiveDiagonal = new char[9];
                char[] negativeDiagonal=  new char[9];
                int pos = 0;
                int neg = 0;
                int j = -4;

                //create positive Diagonal char array
                int k = 4;
                for(int i = -4; i <= 4; i++){
                    if((X + i) >= 0 && (Y + k) >= 0 && (X + i) < WIDTH && (Y + k) < HEIGHT){
                    negativeDiagonal[neg] = grid[X+i][Y+k];
                    neg++;
                    }
                k--; 
                }
                //create negative Diagonal char array
                for(int i = -4; i <= 4; i++)
                {
                    
                    if((X + i) >= 0 && (Y + j) >= 0 && (X + i) < WIDTH && (Y + j) < HEIGHT){
                    positiveDiagonal[pos] = grid[X+i][Y+j];
                    pos++;     
                    }
                j++; 
                }
                int count = 0;
                for(int i = 0; i < pos; i++)
                    {   
                        if (positiveDiagonal[i] == counter){count++;}
                            else{count=0;}
                            if(count==5){return true;}
                    }
                count = 0;
                    for(int i = 0; i < neg; i++)
                    {   
                        if (negativeDiagonal[i] == counter){count++;}
                            else{count=0;}
                            if(count==5){return true;}
                    }
                    return false;
            }

            

}

