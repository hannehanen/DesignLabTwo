package Enteties;

import java.util.ArrayList;

public class Player {
    private String name;
    private   ArrayList<Card> cards;
    private boolean satisFiedWithCards;
    private boolean isFat;
    public Player(String name){
        isFat = false;
        satisFiedWithCards = false;
        this.name = name;
        cards =  new ArrayList<>();
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
