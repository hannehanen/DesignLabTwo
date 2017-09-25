package Game;

import Enteties.*;
import Factories.DeckFactory;
import Listeners.UserListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Game implements UserListener{
    private ArrayList<Player> loosers;
    private ArrayList<Player> winners;
    private ArrayList<GameListener> gameListeners;
    private ArrayList<Client> clients;
    private Deck deck;
    private DeckFactory deckFactory;
    private Dealer dealer;
    public Game (){
        dealer = new Dealer("Dealer");
        clients = new ArrayList<>();
        deckFactory = new DeckFactory();
        gameListeners = new ArrayList<GameListener>();
    }
    public void setDeck(Deck deck){
        this.deck = deck;
    }
    public void playGame(){


    }
    @Override
    public void listenToUser(String command, Object data) {
        if(command.equals("gameOn")){
            Client player1 = new Client((String) data);
            addPlayer(player1);
            notifyUsers("gameMode",null);
        }
        if(command.equals("decktype")){
            HashMap<String,Object> clientData = (HashMap<String, Object>) data;
            String svar = (String) clientData.get("decktype");
            if(svar.equalsIgnoreCase("vanlig")){
               deck = deckFactory.getOrdinaryDeck();
            }
            if(svar.equalsIgnoreCase("dubbel")){
                deck = deckFactory.getDoubleDeck();
            }
            if(svar.equalsIgnoreCase("random")){
                int amount = (int)clientData.get("amount");
                deck = deckFactory.getRandomDeck(amount);
            }
            notifyUsers("amountOfPlayers",null);
        }
        if(command.equalsIgnoreCase("playerAmount")){
            for(String player: (ArrayList<String>)data){
                addPlayer(new Client(player));
            }
            deck.shuffleDeck();
            startDealingCards();
        }
        if(command.equalsIgnoreCase("hitOrStay")){
            HashMap<String,Object> playerAndAnswer = (HashMap<String,Object>) data;
            String svar =(String) playerAndAnswer.get("answer");
            Client player = (Client) playerAndAnswer.get("player");
            if(svar.equalsIgnoreCase("hit")){
                givePlayerOneNewCard(player);
            }
            if(svar.equalsIgnoreCase("stay")){
                player.setSatisFiedWithCards(true);
                notifyUsers("stay",player);
            }
        }
        if(command.equalsIgnoreCase("aceValue")){
            HashMap<String, Object> cardAndAnswer = (HashMap<String, Object>) data;
            Card card = (Card) cardAndAnswer.get("card");
            String answer = (String) cardAndAnswer.get("answer");
            if(answer.equalsIgnoreCase("1")){
                card.setAceLowValue(true);
            }
            if(answer.equalsIgnoreCase("11")){
                card.setAceLowValue(false);
            }
        }
    }

    public void givePlayerOneNewCard(Player player){
        Card card = deck.getTopCard();
        player.addCard(card);
        if(card.getValue()==1){
            notifyUsers("aceValue",player);
        }
        else{
            notifyUsers("newCard",player);
        }
    }

    public void askPlayersToHitOrStay(){
        for(Player player: clients){
            while(player.isSatisFiedWithCards()==false&&player.isFat()==false){
                if(player.getAllCardsValue()>21){
                    notifyUsers("Fat",player);
                    player.setFat(true);
                }
                if(!player.isFat()){
                    notifyUsers("hitOrStay",player);
                }
            }
        }
        //dessa borde inte vara här!
        dealerDrawCards();
        calculateWinner();
    }

    private void calculateWinner() {
        winners = new ArrayList<>();
        loosers = new ArrayList<>();
        for(Client client: clients){
            if(client.getAllCardsValue()==21){
                winners.add(client);
            }
            else if(!client.isFat()&&dealer.isFat()){
                winners.add(client);
            }
           else if(!client.isFat()&&client.getAllCardsValue()>dealer.getAllCardsValue()){
                winners.add(client);
            }
            else  {
                loosers.add(client);
            }
        }
        HashMap<String,Object> winnersLoosers = new HashMap<>();
        winnersLoosers.put("winners",winners);
        winnersLoosers.put("loosers",loosers);
        notifyUsers("winnersAndLoosers",winnersLoosers);
    }

    public void dealerDrawCards(){
        Card card = deck.getTopCard();
        dealer.addCard(card);
        Card card2 = deck.getTopCard();
        dealer.addCard(card2);
        dealer.dealerCalculatingCards();
        notifyUsers("dealerCards",dealer);
        while (!dealer.isSatisFiedWithCards()){
            dealer.addCard(deck.getTopCard());
            dealer.dealerCalculatingCards();
            notifyUsers("dealerDrawCard",dealer);
        }
        notifyUsers("dealerHappy",dealer);
    }

    public void startDealingCards(){
        for(Client player: clients){
            player.addCard(deck.getTopCard());
            player.addCard(deck.getTopCard());
        }
        notifyUsers("firstRondCardsDone", clients);
        askPlayersToHitOrStay();
    }



    public void notifyUsers(String command,Object data){
        for(GameListener listener: gameListeners){
            listener.updateUser(command,data);
        }
    }
    public void addPlayer(Client player){
        clients.add(player);
    }
    public Deck getDeck(){
        return this.deck;
    }
    public void addGameListener(GameListener listener){
        this.gameListeners.add(listener);
    }
}
