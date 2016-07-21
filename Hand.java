

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//holds card
public class Hand {

	//array list of cards
    private List<Cards> cards = new ArrayList<Cards>();

    //picks a card out
    public List<Cards> getCards() {
        return cards;
    }

    //give player a new card
    public void addCard(Cards card) {
        cards.add(card);
    }

    //total of player hand
    public int getValueOfHand() {
        int valueOfHand = 0;
        int ace = 0;

        //calculation
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValueOfCard().equalsIgnoreCase("Ace")) {
            	//counter
                ace++;
                valueOfHand++;
                //faces are worth 10
            } else if (cards.get(i).getValueOfCard().equalsIgnoreCase("Jack")
                    || cards.get(i).getValueOfCard().equalsIgnoreCase("Queen")
                    || cards.get(i).getValueOfCard().equalsIgnoreCase("King")) {
                valueOfHand += 10;
            } else {
                valueOfHand += Integer.parseInt(cards.get(i).getValueOfCard());
            }
        }

        //Ace turns into 11 as long as it doesn't go over 21
        for (int i = 0; i < ace; i++) {
            if (valueOfHand - 1 + 11 <= 21) {
                valueOfHand = valueOfHand - 1 + 11;
            }
        }

        return valueOfHand;
    }
    
    //return string of hand
    public String toString() {
        String string = "";
        
        Iterator<Cards> it = cards.iterator();
        
        while(it.hasNext()) {
            string += it.next().toString() + "\n";
        }
        
        //getting rid of white space
        return string.trim();
    }
}