package Game.Highscore;

public class Highscore {
    private String name;
    private  int wins;

    public Highscore(String name, Integer wins){
        this.name = name;
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public String getName() {
        return name;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
