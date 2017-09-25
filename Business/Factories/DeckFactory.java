package Factories;

import Enteties.Card;
import Enteties.DoubleDeck;
import Enteties.OrdinaryDeck;
import Game.CardType;

import java.util.ArrayList;

public class DeckFactory {
    ArrayList<Card> cards;
    public DeckFactory(){
        cards = new ArrayList<>();
    }
    public OrdinaryDeck getOrdinaryDeck(){
        cards.clear();
        OrdinaryDeck ordinaryDeck = new OrdinaryDeck(createOneDeck());
        return ordinaryDeck;
    }

    public ArrayList<Card> createOneDeck(){
       for(CardType type:CardType.values()){
           for(int i =1;i<14;i++){
               cards.add(new Card(i,type));
           }
       }
        return cards;
    }
}
