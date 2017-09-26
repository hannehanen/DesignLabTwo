package storage.DAO;

import Game.Game;
import storage.Entity.GameLoggingClass;

import java.util.ArrayList;

public interface DAOInterface {
    public void storeGame(GameLoggingClass game);
    public ArrayList<GameLoggingClass> getGames();
}
