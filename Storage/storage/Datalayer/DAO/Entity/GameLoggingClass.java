package storage.Datalayer.DAO.Entity;

import Game.Clients.Client;
import com.fasterxml.jackson.annotation.JsonCreator;


import java.util.ArrayList;

public class GameLoggingClass {
    @JsonCreator
    public GameLoggingClass(){
    }
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
