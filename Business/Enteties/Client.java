package Enteties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "isWinner","cards","satisFiedWithCards","cardsToString","fat" })
public class Client extends Player {
    public Client(String name) {
        super(name);
        isWinner = false;
    }
    private boolean isWinner;

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
