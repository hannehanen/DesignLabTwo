package Enteties;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public void shuffleDeck(){
        Collections.shuffle(this.cards);
    }
    public Card getTopCard(){
        Card topCard = cards.get(0);
        cards.remove(0);
        return topCard;
    }
}
