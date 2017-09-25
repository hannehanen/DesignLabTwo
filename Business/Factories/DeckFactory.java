package Factories;

import Enteties.Card;
import Enteties.DoubleDeck;
import Enteties.OrdinaryDeck;
import Enteties.RandomDeck;
import Game.CardType;

import java.util.ArrayList;
import java.util.Collections;

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

    public DoubleDeck getDoubleDeck(){
        cards.clear();
        createOneDeck();
        createOneDeck();
        DoubleDeck doubleDeck = new DoubleDeck(cards);
        System.out.println("skapade just en doubledeck med "+doubleDeck.getCards().size()+" kort");
        return  doubleDeck;
    }
    public RandomDeck getRandomDeck(int amountOfCards){
        cards.clear();
        int amountOfDecks= (int)Math.ceil((double) amountOfCards/(double)52) ;
        System.out.println("int nr of decks"+amountOfDecks);
        if(amountOfDecks>1){
            for(int i =0;i<amountOfDecks;i++){
                createOneDeck();
            }
        }
        else{
            createOneDeck();
        }

        Collections.shuffle(cards);
        ArrayList<Card> randomListToCreateDeckfrom = new ArrayList<>();
        for(int i =0;i<=amountOfCards;i++){
            randomListToCreateDeckfrom.add(cards.get(i));
        }
        RandomDeck randomDeck = new RandomDeck(randomListToCreateDeckfrom);
        return randomDeck;
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
