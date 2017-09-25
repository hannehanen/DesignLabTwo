package storage.Facade;

import Game.Game;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import storage.DAO.JsonDAO;
import storage.Entity.GameLoggingClass;

import java.io.*;

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

        ObjectMapper mapper = new ObjectMapper();
        GameLoggingClass storeObject = new GameLoggingClass(game.getClients());
        try {
            String objectInJasonString = mapper.writeValueAsString(storeObject);
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("game.json"),true));
            printWriter.println(objectInJasonString);
          //  printWriter.append(""+objectInJasonString);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
