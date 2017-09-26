package Game.Clients;

import Game.Clients.Moves.Move;
import Game.Decks.Card.Card;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private ArrayList<Card> cards;
    private boolean satisFiedWithCards;
    private boolean isFat;
    private ArrayList<Move> moves;
    public Player(){
    }
    public Player(String name){
        isFat = false;
        satisFiedWithCards = false;
        this.name = name;
        cards =  new ArrayList<>();
        moves = new ArrayList<>();
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public boolean isFat() {
        return isFat;
    }

    public void setFat(boolean fat) {
        isFat = fat;
    }

    public boolean isSatisFiedWithCards() {
        return satisFiedWithCards;
    }

    public void setSatisFiedWithCards(boolean satisFiedWithCards) {
        this.satisFiedWithCards = satisFiedWithCards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    public void addCard(Card card){
        cards.add(card);
    }

    public String getCardsToString(){
        String cardsString="";
        for(Card card: cards){
            cardsString += card.toString();
        }
        return cardsString;
    }
    public int getAllCardsValue(){
        int value = 0;
        for(Card card: cards){
            value+=card.getValue();
        }
        return value;
    }

    public String getName() {
        return name;
    }
}
