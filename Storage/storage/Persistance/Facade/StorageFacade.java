package storage.Persistance.Facade;

import Game.Clients.Client;
import Game.Game;
import storage.Datalayer.DAO.DAOInterface;
import storage.Datalayer.DAO.Entity.GameLoggingClass;
import Game.Highscore.Highscore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StorageFacade {
    private DAOInterface dao;
    private static StorageFacade storageFacade = new StorageFacade();
    private StorageFacade(){
    }

    public void setDAO(DAOInterface dao){
        this.dao = dao;
    }

    public static StorageFacade getInstance(){
        return storageFacade;
    }

    public void logGame(Game game){
        GameLoggingClass storeObject = new GameLoggingClass(game.getClients());
        dao.storeGame(storeObject);
    }
    public Highscore getHighScore(){
        ArrayList<GameLoggingClass> data = dao.getGames();
        HashMap<String,Integer> score = new HashMap<String, Integer>();
        for(GameLoggingClass item: data){
            for(Client client: item.getClients()){
                if(score.get(client.getName())==null){
                    score.put(client.getName(),0);
                    if(client.isWinner()){
                        score.put(client.getName(),score.get(client.getName())+1);
                    }
                }
                else{
                    if(client.isWinner()){
                        score.put(client.getName(),score.get(client.getName())+1);
                    }
                }
            }

        }
        String highScoreName ="";
        Integer highScore =0;
        for(Map.Entry<String,Integer> scoreValue: score.entrySet()){
            Integer playerScore = scoreValue.getValue();
            if(playerScore>highScore){
                highScore = playerScore;
                highScoreName = scoreValue.getKey();
            }
        }
        Highscore highscore = new Highscore(highScoreName,highScore);
        return highscore;
    }

}
