package Game;

import Enteties.Card;
import Enteties.Deck;
import Enteties.OrdinaryDeck;
import Enteties.Player;
import Factories.DeckFactory;
import Listeners.UserListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Game implements UserListener{
    private ArrayList<GameListener> gameListeners;
    private ArrayList<Player> players;
    private Deck deck;
    private DeckFactory deckFactory;
    public Game (){
        players = new ArrayList<>();
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
            Player player1 = new Player((String) data);
            addPlayer(player1);
            notifyUsers("gameMode",null);
        }
        if(command.equals("decktype")){
            String svar = (String) data;
            if(svar.equalsIgnoreCase("vanlig")){
               deck = deckFactory.getOrdinaryDeck();
               notifyUsers("amountOfPlayers",null);
            }
        }
        if(command.equalsIgnoreCase("playerAmount")){
            for(String player: (ArrayList<String>)data){
                addPlayer(new Player(player));
            }
            deck.shuffleDeck();
            startDealingCards();
        }
        if(command.equalsIgnoreCase("hitOrStay")){
            HashMap<String,Object> playerAndAnswer = (HashMap<String,Object>) data;
            String svar =(String) playerAndAnswer.get("answer");
            Player player = (Player) playerAndAnswer.get("player");
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
        for(Player player: players){
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
        dealerDrawCards();
    }
    public void dealerDrawCards(){

    }

    public void startDealingCards(){
        for(Player player: players){
            player.addCard(deck.getTopCard());
            player.addCard(deck.getTopCard());
        }
        notifyUsers("firstRondCardsDone",players);
        askPlayersToHitOrStay();
    }



    public void notifyUsers(String command,Object data){
        for(GameListener listener: gameListeners){
            listener.updateUser(command,data);
        }
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public Deck getDeck(){
        return this.deck;
    }
    public void addGameListener(GameListener listener){
        this.gameListeners.add(listener);
    }
}
