import Enteties.Card;
import Enteties.Client;
import Enteties.Dealer;
import Enteties.Player;
import Game.GameListener;
import Listeners.UserListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConsollUserIMPL implements UserInterface, GameListener {
    private ArrayList<UserListener> listeners;
    Scanner scanner;
    ArrayList<Player> players;
    public ConsollUserIMPL(){
        listeners = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    public void playCardGame(){
        String svar;
        System.out.println("Klockan är ganska mycket och det är ju inte lätt att komma ihåg sitt namn såhär sent... vad hetter jag nu igen?(var god skriv ditt namn)");
        String name = scanner.nextLine();
        System.out.println("Kortspel! ja det vill jag ju spela!... hrm eller... har ju inte så mycket mer pengar att spela för.. ska jag?(ja/nej)");
        svar = scanner.nextLine();
        if(svar.equalsIgnoreCase("ja")){
            notifyListeners("gameOn",name);
        }
        else{
            System.out.println("Smart val! Kyparen ser ju lite suspekt ut..");
            return;
        }
    }
    @Override
    public void updateUser(String command, Object data) {
        if(command.equals("gameMode")){
            //kanske på fel ställe, men får ha det för tillfället.
            HashMap<String,Object> clientData = new HashMap<>();
            System.out.println("Vad vill du använda för deck? Vanlig, Dubbel eller Random kort med viss mängd?");
            String svar = scanner.nextLine();
            clientData.put("decktype",svar);
            if(svar.equalsIgnoreCase("random")){
                System.out.println("Hur många randomkort skall leken innehålla?");
                int amountOfCards = Integer.parseInt(scanner.nextLine());
                clientData.put("amount",amountOfCards);
                notifyListeners("decktype",clientData);
            }
            else{

                notifyListeners("decktype",clientData);
            }

        }
        if(command.equalsIgnoreCase("amountOfPlayers")){
            System.out.println("Hur många spelare utöver dig vill spela spelet? Vi kan säga max... 10 st");
            int players = Integer.parseInt(scanner.nextLine());
            ArrayList<String> playerNames = new ArrayList<>();
            if(players>0){
                for(int i =2;i<players+2;i++){
                    System.out.println("Namn på spelare "+i+" tack.");
                    playerNames.add(scanner.nextLine());
                }
            }
            notifyListeners("playerAmount",playerNames);
        }
        if(command.equalsIgnoreCase("firstRondCardsDone")){
            players = (ArrayList<Player>) data;
            printCards();
            printValue();
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
            HashMap<String,Object> winnersLoosers = (HashMap<String,Object>) data;
            printOutWinnersAndLoosers(winnersLoosers);
        }
    }

    private void printOutWinnersAndLoosers(HashMap<String,Object> winnersLoosers) {
        ArrayList<Client> winners = (ArrayList<Client>)winnersLoosers.get("winners");
        ArrayList<Client> loosers = (ArrayList<Client>) winnersLoosers.get("loosers");
        for(Client client: winners){
            System.out.println(client.getName()+" är vinnare!");
        }
        for(Client client: loosers){
            System.out.println(client.getName()+" är förlorare! du fick värdet: "+client.getAllCardsValue());
        }
    }


    public int printValueForAPlayer(Player player){
        int value =0;
        for (Card card: player.getCards()){
            value += card.getValue();
        }
        return value;
    }

    public void printCards(){
        for (Player player: players){
            System.out.println(player.getName()+" du har korten: "+player.getCardsToString());
        }
    }
    public void printValue(){
        System.out.println();
        for(Player player: players){
            int value = 0;
            for(Card card: player.getCards()){
                if(card.getValue()==1){
                    System.out.println(player.getName()+" du har ett Ace. vill du ha värdet 1 eller 11? (skriv 1 eller 11)");
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
            System.out.println(player.getName()+" dina kort har värdet "+value);
        }
    }
    @Override
    public void notifyListeners(String command,Object data) {
        for(UserListener listener: listeners){
            listener.listenToUser(command,data);
        }
    }
    public void addListener(UserListener listener){
        listeners.add(listener);
    }
}
