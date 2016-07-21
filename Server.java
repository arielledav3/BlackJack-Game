

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	static ServerSocket serverSocket;
	static Socket clientSocket;

	//changing the number of players allowed to play against
	//dealer at the same time
    public static final int MAX_NUM_OF_PLAYERS = 3;
    private static final int PORT = 5000;

    public static List<PlayerControls> players = new ArrayList<PlayerControls>();
    private static boolean gameOngoing = false;
    private static boolean joinMode = false;

    //how many players are playing
    public static synchronized int getNumberOfPlayers() {
        return players.size();
    }

    //object of player
    public static synchronized PlayerControls getPlayer(int i) {
        return players.get(i);
    }

    //adding players
    public static synchronized void addNewPlayer(PlayerControls player) {
        if (getNumberOfPlayers() >= MAX_NUM_OF_PLAYERS) {
            return;
        }

        System.out.println("A new player joined.");
        players.add(player);
        
        if (Server.getNumberOfPlayers() == Server.MAX_NUM_OF_PLAYERS) {
            Server.startGame();
        }
    }

    //time to start game; 3 players
    public static synchronized void startGame() {
        System.out.println("A new round is starting!");
        
        gameOngoing = true;

        //threads for the game
        new Thread(new DealerControls()).start();
    }

    //checking the game is still going
    public static synchronized boolean isGameOngoing() {
        return gameOngoing;
    }

    //ending game
    public static synchronized void endGame() {
        System.out.println("The round ended!");
        joinMode = false;
        gameOngoing = false;
        //clearing the old players
        players.clear();
    }
    
    public static void close() {
    	try{
    		//clientSocket.close();
    		System.out.println("Game Finished, shutting down");
    		serverSocket.close();
    		
    	}catch(Exception e){
    		//ignore
    	}
    }

    //start
    public static void main(String[] args) {
        //server

        try {
        	serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running. To stop, press ctrl + c.");

            while (true) {
                //waiting for player one
            	PlayerControls player = new PlayerControls(serverSocket.accept());

                //error message for late joiners
                if (isGameOngoing()) {
                    player.sendTheMessage("A game is on going. Try the next one!");
                    continue;
                }

                addNewPlayer(player);
             }

        } catch (Exception e) {
        	
        }
        
    	
    }
}
