package Enteties;

public class Move {
    private int moveNumber;
    private int value;
    private String action;

    public Move(int moveNumber, int value, String action){
        this.moveNumber = moveNumber;
        this.value= value;
        this.action = action;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
