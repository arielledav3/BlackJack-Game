

public class Cards implements Comparable<Cards> {
    private String suitOfCard;
    private String valueOfCard;

    //creates card
    public Cards(String suitOfCard, String valueOfCard) {
        this.suitOfCard = suitOfCard;
        this.valueOfCard = valueOfCard;
    }

    //gets value
    public String getValueOfCard() {
        return valueOfCard;
    }

    //gets suit
    public String getSuitOfCard() {
        return suitOfCard;
    }

    //return string of card
    public String toString() {
        return valueOfCard + " " + suitOfCard;
    }

    //cards equal to each other if they are the same 
    public int compareTo(Cards c) {
        return (suitOfCard + valueOfCard).compareTo(c.suitOfCard + c.valueOfCard);
    }
}
