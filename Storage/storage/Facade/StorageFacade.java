package storage.Facade;

import Game.Game;
import storage.DAO.JsonDAO;

public class StorageFacade {
    private JsonDAO jsonDAO;
    private static StorageFacade storageFacade = new StorageFacade();
    private StorageFacade(){
        this.jsonDAO = new JsonDAO();
    }
    public static StorageFacade getInstance(){
        return storageFacade;
    }
    public void logGame(Game game){

    }
}
