package storage.DAO;

import Game.Game;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonDAO implements  DAOInterface {

    ObjectMapper objectMapper;
    public JsonDAO(){
        objectMapper = new ObjectMapper();
    }
    @Override
    public void storeGame(Game game) {
        try {
            objectMapper.writeValue(new File("games.json"),game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Game getGame(int id) {
        return null;
    }
}
