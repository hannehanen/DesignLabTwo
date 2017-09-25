package Game;
import Enteties.*;
import storage.Facade.StorageFacade;

import java.util.ArrayList;
public class Game {
    private ArrayList<Client> clients;
    private Dealer dealer;

    public Game (){
        dealer = new Dealer("Dealer");
        clients = new ArrayList<>();
        }
    public void playGame(){
        dealer.getDeck().shuffleDeck();
        dealer.startDealingCards(clients);
        dealer.dealCardsToAllClientsTillDone(clients);
        dealer.dealerDrawCards();
        dealer.calculateWinner(clients);
        logThisGame();
    }

    private void logThisGame() {
        StorageFacade storageFacade = StorageFacade.getInstance();
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
