import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//3 players
//only handles dealer; player class has game logic
//implements runnable; run()
public class DealerControls implements Runnable {

	//card names and numbers
    private static final String[] SUITSDECK = {"Diamond", "Heart", "Spade", "Club"};
    private static final String[] CARDSDECK = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
   
    //randomize deck
    private static Random randomize = new Random();
    private static List<Cards> cardsUsed = new ArrayList<Cards>();

    public static int playersFinish = 0;
    public static int isDone = 0;
    //player finished
    public static synchronized void addplayerFinish() {
    	playersFinish++;
    	isDone++;
        
        // If the number of finished players reaches the expected, game ends
        if (getplayerFinish() >= Server.getNumberOfPlayers()) {
            Server.endGame();
            if
        }
    }

    //sees how many players are playing
    public static synchronized int getplayerFinish() {
        return playersFinish;
    }

    //card gets sent to the client
    public static synchronized Cards hitCard() {
    	//if above is true
        while (true) {
            String suitOfCard = SUITSDECK[randomize.nextInt(SUITSDECK.length)];
            String valueOfCard = CARDSDECK[randomize.nextInt(CARDSDECK.length)];

            Cards cards = new Cards(suitOfCard, valueOfCard);

            if (!cardsUsed.contains(cards)) {
            	cardsUsed.add(cards);
                return cards;
            }
        }
    }

    //start
    public void run() {
    	cardsUsed.clear();
        playersFinish = 0;
        
        //players start; finishes when players are all done
        for (int i = 0; i < Server.getNumberOfPlayers(); i++) {
            new Thread(Server.getPlayer(i)).start();
        }
        
        
    }
}
