package Game;
import Game.Clients.Client;
import Game.Clients.Dealer;
import Game.Decks.Deck;
import storage.Datalayer.DAO.AbstractDAOFactory;
import storage.Datalayer.DAO.DAOInterface;
import storage.Datalayer.DAO.DAOJson;
import storage.Persistance.Facade.StorageFacade;

import java.util.ArrayList;
public class Game {
    private ArrayList<Client> clients;
    private Dealer dealer;
    private Deck deck;
    public Game (){
        dealer = new Dealer("Dealer");
        deck = new Deck();
        clients = new ArrayList<>();
        }
    public void playGame(){
        dealer.getDeck().shuffleDeck();
        dealer.startDealingCards(clients);
        dealer.dealCardsToAllClientsTillDone(clients);
        dealer.dealerDrawCards();
        dealer.calculateWinner(clients);
        logThisGame();
        dealer.tellHighScore();
    }


    public void createDeck(String type,int amount){
        if(type.equalsIgnoreCase("random")){
            getDealer().setDeck(deck.getRandomDeck(amount));
        }
    }
    public void createDeck(String type){
        if(type.equalsIgnoreCase("vanlig")){
            getDealer().setDeck(deck.getOrdinaryDeck());
        }
        if(type.equalsIgnoreCase("dubbel")){
            getDealer().setDeck(deck.getDoubleDeck());
        }
    }

    private void logThisGame() {
        StorageFacade storageFacade = StorageFacade.getInstance();
        AbstractDAOFactory factory = new DAOJson();
        DAOInterface storeJson = factory.getDAO();
        storageFacade.setDAO(storeJson);
        storageFacade.logGame(this);
    }

    public Dealer getDealer() {
        return dealer;
    }
    public void addPlayer(Client player){
        clients.add(player);
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
