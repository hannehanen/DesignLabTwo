package Controllers;

import Game.Clients.Client;
import Game.Clients.Moves.Move;
import Game.Decks.Card.Card;
import Game.Decks.DeckFactory;
import Game.*;
import Game.Highscore.Highscore;
import Listeners.DealerListener;
import Listeners.ControllerListener;
import Listeners.ViewListener;
import storage.Datalayer.DAO.DAOFactory;
import storage.Persistance.Facade.StorageFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsollController implements ViewListener, DealerListener{
    private Game game;
    private ArrayList<ControllerListener> gameListeners;
    private DeckFactory deckFactory;

    public ConsollController(){
        deckFactory = new DeckFactory();
        gameListeners= new ArrayList<ControllerListener>();
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
            if(deckType.equalsIgnoreCase("random")){
                int amount = (int)gameOptions.get("amountWithRandomDeck");
                game.createDeck(deckType,amount);
            }
            else{
                game.createDeck(deckType);
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
                player.getMoves().add(new Move(player.getMoves().size()+1,player.getAllCardsValue(),"HIT"));
            }
            if(svar.equalsIgnoreCase("stay")){
                player.setSatisFiedWithCards(true);
                player.getMoves().add(new Move(player.getMoves().size()+1,player.getAllCardsValue(),"STAY"));
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
        if(command.equalsIgnoreCase("highscore")){
            StorageFacade facade = StorageFacade.getInstance();
            DAOFactory factory = new DAOFactory();
            facade.setDAO(factory.getJsonDAO());
            Highscore leader = facade.getHighScore();
            notifyUsers("highscoreLeader",leader);
        }
    }

    public void addGameListener(ControllerListener listener){
        this.gameListeners.add(listener);
    }
    public void notifyUsers(String command,Object data){
        for(ControllerListener listener: gameListeners){
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
        if(command.equalsIgnoreCase("highscore")){
            notifyUsers("highScore",null);
        }
    }
}
