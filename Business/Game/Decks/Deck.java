package Game.Decks;

import Game.Decks.Card.Card;
import Game.Decks.Card.CardType;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;
    public Deck(){
        cards = new ArrayList<>();
    }
    private Deck(ArrayList<Card> cards){
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
    public Deck getOrdinaryDeck(){
        cards.clear();
       cards = createOneDeck();

        return new Deck(cards);
    }

    public Deck getDoubleDeck(){
        cards.clear();
        createOneDeck();
        createOneDeck();
        return  new Deck(cards);
    }
    public Deck getRandomDeck(int amountOfCards){
        cards.clear();
        int amountOfDecks= (int)Math.ceil((double) amountOfCards/(double)52) ;
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
        randomListToCreateDeckfrom.remove(randomListToCreateDeckfrom.size()-1);
        cards = randomListToCreateDeckfrom;

        System.out.println("Skapade just ett random deck med storlek: "+cards.size());
        return new Deck(cards);
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
