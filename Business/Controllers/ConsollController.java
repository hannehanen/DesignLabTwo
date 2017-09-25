package Controllers;

import Enteties.*;
import Factories.DeckFactory;
import Game.*;
import Listeners.DealerListener;
import Listeners.UserListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsollController implements UserListener, DealerListener{
    private Game game;
    private ArrayList<GameListener> gameListeners;
    private DeckFactory deckFactory;

    public ConsollController(){
        deckFactory = new DeckFactory();
        gameListeners= new ArrayList<GameListener>();
    }

    @Override
    public void listenToUser(String command, Object data) {
        if(command.equalsIgnoreCase("gameOptionsDone")){
            HashMap<String,Object> gameOptions = (HashMap<String,Object>) data;
            this.game = new Game();
            game.getDealer().setDealerListener(this);
            ArrayList<String> clients = (ArrayList<String>)gameOptions.get("clients");
            for(String client: clients){
                game.addPlayer(new Client(client));
            }
            String deckType = (String) gameOptions.get("decktype");
            if(deckType.equalsIgnoreCase("vanlig")){
               game.getDealer().setDeck(deckFactory.getOrdinaryDeck());
            }
            if(deckType.equalsIgnoreCase("dubbel")){
                game.getDealer().setDeck(deckFactory.getDoubleDeck());
            }
            if(deckType.equalsIgnoreCase("random")){
                int amount = (int)gameOptions.get("amountWithRandomDeck");
                game.getDealer().setDeck(deckFactory.getRandomDeck(amount));
            }
            game.playGame();
        }
        if(command.equalsIgnoreCase("hitOrStay")){
            HashMap<String,Object> playerAndAnswer = (HashMap<String,Object>) data;
            String svar =(String) playerAndAnswer.get("answer");
            Client player = (Client) playerAndAnswer.get("player");
            if(svar.equalsIgnoreCase("hit")){
               Card card =  game.getDealer().givePlayerOneNewCard(player);
                if(card.getValue()==1){
                    notifyUsers("aceValue",player);
                }
                else{
                    notifyUsers("newCard",player);
                }
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

    public void addGameListener(GameListener listener){
        this.gameListeners.add(listener);
    }
    public void notifyUsers(String command,Object data){
        for(GameListener listener: gameListeners){
            listener.updateUser(command,data);
        }
    }

    @Override
    public void listenToDealer(String command, Object data) {
        if(command.equalsIgnoreCase("dealerCards")){
            notifyUsers("dealerCards",data);
        }
        if(command.equalsIgnoreCase("dealerDrawCard")){
            notifyUsers("dealerDrawCard",data);
        }
        if(command.equalsIgnoreCase("dealerHappy")){
            notifyUsers("dealerHappy",data);
        }
        if(command.equalsIgnoreCase("winnersAndLoosers")){
            notifyUsers("winnersAndLoosers",data);
        }
        if(command.equalsIgnoreCase("firstRondCardsDone")){
            notifyUsers("firstRondCardsDone", data);
        }
        if(command.equalsIgnoreCase("Fat")){
            notifyUsers("Fat",data);
        }
        if(command.equalsIgnoreCase("hitOrStay")){
            notifyUsers("hitOrStay",data);
        }
    }
}
