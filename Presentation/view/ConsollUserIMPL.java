package view;

import Game.Decks.Card.Card;
import Game.Clients.Client;
import Game.Clients.Dealer;
import Game.Clients.Player;
import Game.Highscore.Highscore;
import Listeners.ControllerListener;
import Listeners.ViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConsollUserIMPL implements  ControllerListener {
    private ArrayList<ViewListener> listeners;
    private Scanner scanner;
    public ConsollUserIMPL(){
        listeners = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    public void gameOptions(){
        HashMap<String,Object> gameOptions = new HashMap<>();
        ArrayList<String> clients = new ArrayList<>();
        gameOptions.put("clients",clients);
        System.out.println("Klockan är ganska mycket och det är ju inte lätt att komma ihåg sitt namn såhär sent... vad heter jag nu igen?(var god skriv ditt namn)");
        clients.add(scanner.nextLine());
        System.out.println("Vad vill du använda för deck? Vanlig, Dubbel eller Random kort med viss mängd?");
        String deckType = scanner.nextLine();
        if(deckType.equalsIgnoreCase("random")){
            System.out.println("Hur många randomkort skall leken innehålla?");
            int amountOfCards = Integer.parseInt(scanner.nextLine());
            gameOptions.put("amountWithRandomDeck",amountOfCards);
        }
        gameOptions.put("decktype",deckType);
        System.out.println("Är det någon utöver dig som vill spela? Skriv isåfall antal.");
        int amountOfExtraPlayers = Integer.parseInt(scanner.nextLine());
        if(amountOfExtraPlayers>0){
            for(int i =2;i<amountOfExtraPlayers+2;i++){
                System.out.println("Namn på spelare "+i+" tack.");
                clients.add(scanner.nextLine());
            }
        }
        notifyListeners("gameOptionsDone",gameOptions);
    }
    @Override
    public void updateUser(String command, Object data) {
        if(command.equalsIgnoreCase("firstRondCardsDone")){
            ArrayList<Client> clients = (ArrayList<Client>) data;
            printCards(clients);
            printValue(clients);
        }
        if(command.equalsIgnoreCase("hitOrStay")){
            Player player = (Player) data;
            System.out.println(player.getName()+" du har korten "+player.getCardsToString()+" med värdet "+printValueForAPlayer(player)+" vill du hit eller stay?");
            String svar = scanner.nextLine();
            HashMap<String, Object> playerAndAnswer = new HashMap<>();
            playerAndAnswer.put("answer",svar);
            playerAndAnswer.put("player",player);
            notifyListeners("hitOrStay",playerAndAnswer);
        }
        if(command.equalsIgnoreCase("fat")){
            Player player = (Player) data;
            System.out.println(player.getName() +" är fet och har förlorat!");
        }
        if(command.equalsIgnoreCase("stay")){
            Player player = (Player) data;
            System.out.println(player.getName()+" har valt att stanna på värdet "+player.getAllCardsValue()+" med korten "+player.getCardsToString());
        }
        if(command.equalsIgnoreCase("aceValue")){
            Player player = (Player) data;
            Card card = player.getCards().get(player.getCards().size()-1);
            System.out.println(player.getName()+" du drog precis ett spännande kort: "+card.toString()+" vill du att det ska ha värdet 1 eller 11?");
            String svar = scanner.nextLine();
            HashMap<String,Object> cardAndAnswer = new HashMap<>();
            cardAndAnswer.put("card",card);
            cardAndAnswer.put("answer",svar);
            notifyListeners("aceValue",cardAndAnswer);
        }
        if(command.equalsIgnoreCase("newCard")){
            Player player = (Player) data;
            System.out.println(player.getName()+" du fick precis ett nytt kort! "+player.getCards().get(player.getCards().size()-1).toString());
        }
        if(command.equalsIgnoreCase("dealerCards")){

            Dealer dealer = (Dealer) data;
            System.out.println("Dealern har nu dessa kort: "+dealer.getCardsToString()+" med värdet: "+dealer.getAllCardsValue());
        }
        if(command.equalsIgnoreCase("dealerDrawCard")){
            Dealer dealer = (Dealer) data;
            System.out.println("Dealern drog just detta kort: "+dealer.getCards().get(dealer.getCards().size()-1).toString());
        }
        if(command.equalsIgnoreCase("dealerHappy")){
            Dealer dealer = (Dealer) data;
            if(dealer.getAllCardsValue()>21){
                System.out.println("Dealern är tjock med dessa kort: "+dealer.getCardsToString()+" och har värdet: "+dealer.getAllCardsValue());
            }
            else{
                System.out.println("Dealern är nu nöjd med dessa kort: "+dealer.getCardsToString()+" och har värdet: "+dealer.getAllCardsValue());
            }
        }
        if(command.equalsIgnoreCase("winnersAndLoosers")){
            ArrayList<Client> clients = (ArrayList<Client>) data;
            printOutWinnersAndLoosers(clients);
        }
        if(command.equalsIgnoreCase("highScore")){
            System.out.println("vill du se highscoren? (ja/nej)");
            String svar = scanner.nextLine();
            if(svar.equalsIgnoreCase("ja")){
                notifyListeners("highscore",null);
            }
            scanner.close();
        }
        if(command.equalsIgnoreCase("highscoreLeader")){
            Highscore leader = (Highscore)data;
            System.out.println("Den som har vunnit mest är: "+leader.getName()+" med såhär många wins: "+leader.getWins());
        }
    }

    private void printOutWinnersAndLoosers(ArrayList<Client> clients) {
        for(Client client: clients){
            if(client.isWinner()){
                System.out.println(client.getName()+" är vinnare! du fick värdet: "+client.getAllCardsValue());
            }
            else{
                System.out.println(client.getName()+" är förlorare! du fick värdet: "+client.getAllCardsValue());
            }
        }
    }

    public int printValueForAPlayer(Player player){
        int value =0;
        for (Card card: player.getCards()){
            value += card.getValue();
        }
        return value;
    }

    public void printCards(ArrayList<Client> clients){
        for (Player client: clients){
            System.out.println(client.getName()+" du har korten: "+client.getCardsToString());
        }
    }
    public void printValue(ArrayList<Client> clients){
        System.out.println();
        for(Player client: clients){
            int value = 0;
            for(Card card: client.getCards()){
                if(card.getValue()==1){
                    System.out.println(client.getName()+" du har ett Ace. vill du ha värdet 1 eller 11? (skriv 1 eller 11)");
                    String svar = scanner.nextLine();
                    if(svar.equalsIgnoreCase("11")){
                        card.setAceLowValue(false);
                    }
                    else {
                        card.setAceLowValue(true);
                    }
                }
                value += card.getValue();
            }
            System.out.println(client.getName()+" dina kort har värdet "+value);
        }
    }

    public void notifyListeners(String command,Object data) {
        for(ViewListener listener: listeners){
            listener.listenToUser(command,data);
        }
    }
    public void addListener(ViewListener listener){
        listeners.add(listener);
    }
}
