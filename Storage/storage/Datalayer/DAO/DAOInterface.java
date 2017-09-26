package storage.Datalayer.DAO;

import storage.Datalayer.DAO.Entity.GameLoggingClass;

import java.util.ArrayList;

public interface DAOInterface {
    public void storeGame(GameLoggingClass game);
    public ArrayList<GameLoggingClass> getGames();
}
