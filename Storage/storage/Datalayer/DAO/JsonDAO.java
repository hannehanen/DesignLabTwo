package storage.Datalayer.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import storage.Datalayer.DAO.Entity.GameLoggingClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonDAO implements  DAOInterface {

    private ObjectMapper objectMapper;
    public JsonDAO(){
        objectMapper = new ObjectMapper();
    }


    @Override
    public void storeGame(GameLoggingClass storeObject) {
        try {
            String objectInJasonString = objectMapper.writeValueAsString(storeObject);
            File f = new File("game.json");
            if(!f.exists()) {
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("game.json")));
                printWriter.println(objectInJasonString);
                printWriter.close();
            }
            else{
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("game.json"),true));
                printWriter.println(objectInJasonString);
                printWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<GameLoggingClass> getGames() {
        ArrayList<GameLoggingClass> loggedGameInfo = new ArrayList<>();
        try {
            List<String> values = new ArrayList<>();

            Scanner scanner = new Scanner(new File("game.json"));
            while(scanner.hasNext()){
                values.add(scanner.nextLine());
            }
            for(String value: values){
                loggedGameInfo.add(objectMapper.readValue(value,GameLoggingClass.class));
            }
          } catch (IOException e) {
            e.printStackTrace();
        }
        return loggedGameInfo;
    }


}
