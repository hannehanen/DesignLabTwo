package storage.Entity;

import Game.Clients.Client;


import java.util.ArrayList;

public class GameLoggingClass {

    private ArrayList<Client> clients;

    public GameLoggingClass(ArrayList<Client> clients) {
       this.clients = clients;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
}
