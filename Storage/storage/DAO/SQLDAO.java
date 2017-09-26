package storage.DAO;

import storage.Entity.GameLoggingClass;

import java.util.ArrayList;

public class SQLDAO implements DAOInterface {

    private static SQLDAO sqldao = new SQLDAO();

    public static SQLDAO getSqldao(){
        return sqldao;
    }
    @Override
    public void storeGame(GameLoggingClass game) {

    }

    @Override
    public ArrayList<GameLoggingClass> getGames() {
        return null;
    }
}
