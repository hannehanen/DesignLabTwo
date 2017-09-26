package Game;
import Game.Clients.Client;
import Game.Clients.Dealer;
import Game.Decks.DeckFactory;
import storage.DAO.DAOFactory;
import storage.Facade.StorageFacade;

import java.util.ArrayList;
public class Game {
    private ArrayList<Client> clients;
    private Dealer dealer;
    private DeckFactory deckFactory;
    public Game (){
        dealer = new Dealer("Dealer");
        deckFactory = new DeckFactory();
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
            getDealer().setDeck(deckFactory.getRandomDeck(amount));
        }
    }
    public void createDeck(String type){
        if(type.equalsIgnoreCase("vanlig")){
            getDealer().setDeck(deckFactory.getOrdinaryDeck());
        }
        if(type.equalsIgnoreCase("dubbel")){
            getDealer().setDeck(deckFactory.getDoubleDeck());
        }
    }

    private void logThisGame() {
        StorageFacade storageFacade = StorageFacade.getInstance();
        DAOFactory factory = new DAOFactory();
        storageFacade.setDAO(factory.getJsonDAO());
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
