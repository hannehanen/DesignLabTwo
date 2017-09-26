package Game.Clients;


import Game.Clients.Moves.Move;
import Game.Decks.Card.Card;
import Game.Decks.Deck;
import Listeners.DealerListener;
import java.util.ArrayList;

public class Dealer extends Client {
    private DealerListener listener;
    private Deck deck;
    public Dealer(String name) {
        super(name);
    }
    public void setDeck(Deck deck){
        this.deck = deck;
    }
    public Deck getDeck() {
        return deck;
    }
    public void setDealerListener(DealerListener listener){
        this.listener = listener;
    }
    public Card givePlayerOneNewCard(Player player){
        Card card = deck.getTopCard();
        player.addCard(card);
        return card;
    }
    public void tellHighScore(){
        listener.listenToDealer("highscore",null);
    }
    public void dealerDrawCards(){
        Card card = deck.getTopCard();
        addCard(card);
        Card card2 = deck.getTopCard();
        addCard(card2);
        dealerCalculatingCards();
        listener.listenToDealer("dealerCards",this);
        while (!isSatisFiedWithCards()){
            addCard(deck.getTopCard());
            if(this.getAllCardsValue()>21){
                this.setFat(true);
            }
            dealerCalculatingCards();
            listener.listenToDealer("dealerDrawCard",this);
        }
        listener.listenToDealer("dealerHappy",this);
    }
    public void startDealingCards(ArrayList<Client> clients){
        for(Client player: clients){
            givePlayerOneNewCard(player);
            givePlayerOneNewCard(player);
            player.getMoves().add(new Move(getMoves().size()+1,player.getAllCardsValue(),"First Move. No action"));
        }
        listener.listenToDealer("firstRondCardsDone",clients);
    }
    public void dealCardsToAllClientsTillDone(ArrayList<Client> clients){
        for(Player player: clients){
            while(player.isSatisFiedWithCards()==false&&player.isFat()==false){
                if(player.getAllCardsValue()>21){
                    listener.listenToDealer("Fat",player);
                    player.setFat(true);
                }
                if(!player.isFat()){
                    listener.listenToDealer("hitOrStay",player);
                }
            }
        }
    }

    public void calculateWinner(ArrayList<Client> clients) {
        for(Client client: clients){
            if(client.getAllCardsValue()==21){
                client.setWinner(true);
            }
            else if(!client.isFat()&&this.isFat()){
                client.setWinner(true);
            }
            else if(!client.isFat()&&client.getAllCardsValue()>this.getAllCardsValue()){
                client.setWinner(true);
            }
            else  {
                client.setWinner(false);
            }
        }
        listener.listenToDealer("winnersAndLoosers",clients);
    }


    public void dealerCalculatingCards(){
        int value = 0;
        int numerOfAces=0;
        ArrayList<Card> aces = new ArrayList<>();
        for(Card card: this.getCards()){
            if(card.getValue()==1||card.getValue()==11){
                numerOfAces+=1;
                aces.add(card);
                value+=1;
            }
            else{
                value += card.getValue();
            }
        }
        if(numerOfAces ==0&&value>=17){
            this.setSatisFiedWithCards(true);
        }
        if(numerOfAces>0&&value<17){
            for(Card card: aces){
                if(value+10>=17){
                    card.setAceLowValue(false);
                    setSatisFiedWithCards(true);
                }
            }
        }

    }
}
