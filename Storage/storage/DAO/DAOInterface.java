package storage.DAO;

import Game.Game;

public interface DAOInterface {
    public void storeGame(Game game);
    public Game getGame(int id);
}
