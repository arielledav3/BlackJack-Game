

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//communication with player
public class PlayerControls implements Runnable {

    private BufferedReader fromPlayer;
    private PrintWriter toPlayer;

    //communication socket
    public PlayerControls(Socket socket) throws Exception {
        fromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toPlayer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    //message from the player
    public String readTheMessage() throws Exception {
        return fromPlayer.readLine();
    }

    //message gets sent to the player
    public void sendTheMessage(String message) throws Exception {
        toPlayer.println(message.trim());
        toPlayer.println("EOL");
    }

    //new game
    public void run() {
        try {
        	//one card for dealer one for player
            Hand dealerHand = new Hand();
            Hand playerHand = new Hand();

            //both the dealer's cards
            dealerHand.addCard(DealerControls.hitCard());
            dealerHand.addCard(DealerControls.hitCard());

            //player's cards
            playerHand.addCard(DealerControls.hitCard());
            playerHand.addCard(DealerControls.hitCard());

            //dealer's second card is hidden
            String message = "Dealer's hand (one card hidden): ";
            message += dealerHand.getCards().get(0).toString() + "\n";

            //displaying players card
            message += "Your hand is: ";
            message += playerHand.toString() + "\n";
            message += "Your hand's value equals:  " + playerHand.getValueOfHand() + "\n";

            //sending the print message
            sendTheMessage(message);

            //players go until they stay or bust
            boolean playerBusted = false;

            while (true) {
                message = readTheMessage();

                if (message.equalsIgnoreCase("HIT")) {
                    //second card sent if not bust
                    playerHand.addCard(DealerControls.hitCard());

                    //displaying new player hand
                    message = "Your hand is: ";
                    message += playerHand.toString() + "\n";
                    message += "Your hand's value equals:  " + playerHand.getValueOfHand() + "\n";

                    if (playerHand.getValueOfHand() > 21) {
                        playerBusted = true;
                        break;
                    //Arielle's Code
                    //if player hand is 21
                    }else if(playerHand.getValueOfHand() == 21){
                    	message += "YOU WIN!\n";
                    	//added by Jannet 
                    	break;
                	}else {
                        sendTheMessage(message);
                    }
                } else if (message.equalsIgnoreCase("STAY")) {
                    break;
                }
            }

            if (playerBusted) {
                ///player lost
                sendTheMessage(message + "You went BUST! GAME OVER!");
                message = "";
                //needs to exit only a single thread
                //PROBLEM; ONLY ENDS 2 OR THE 3 THREADS
                //added by Jannet 
                DealerControls.addplayerFinish();
              //  Server.players.remove(this);
            } else {
            	message = "";
                //dealer hand
                while (dealerHand.getValueOfHand() < 17) {
                    dealerHand.addCard(DealerControls.hitCard());
                }

                message = "Dealer's final hand is: ";
                //extra space for style
                message += dealerHand.toString() + "\n";
                message += "Dealer hand's value equals:  " + dealerHand.getValueOfHand() + "\n";

                //winning/losing/draw text
                if (dealerHand.getValueOfHand() > 21) {
                    message += "Dealer went BUST! YOU WIN!\n";
                } else if (dealerHand.getValueOfHand() > playerHand.getValueOfHand()) {
                    message += "Dealer WON. You LOSE.\n";
                } else if (dealerHand.getValueOfHand() < playerHand.getValueOfHand()) {
                    message += "You WIN!\n";
                } else {
                    message += "Its a DRAW!\n";
                }

                //sending the print message
                sendTheMessage(message);
            }

            DealerControls.addplayerFinish();
        } catch (Exception e) {
        	try {
				sendTheMessage("something when wrong!");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	DealerControls.addplayerFinish();
        }
    }
}

