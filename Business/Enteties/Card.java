package Enteties;

import Game.CardType;

public class Card {
    private int value;
    private CardType type;
    String name;
    public Card (int value,CardType type){
        this.type = type;
        switch (value){
            case 1: name= "Ace of";
                this.value = value;
                break;
            case 2: name="Two of";
                this.value = value;
                break;
            case 3: name="Three of";
                this.value = value;
                break;
            case 4: name="Four of";
                this.value = value;
                break;
            case 5: name="Five of";
                this.value=value;
                break;
            case 6: name="Six of";
                this.value = value;
                break;
            case 7: name="Seven of";
                this.value = value;
                break;
            case 8: name="Eight of";
                this.value=value;
                break;
            case 9: name="Nine of";
                this.value=value;
                break;
            case 10: name="Ten of";
                this.value = value;
                break;
            case 11: name="Knight of";
                this.value=10;
                break;
            case 12: name="Queen of";
                this.value=10;
                break;
            case 13: name="King of";
                this.value = 10;
             break;
        }
    }

    public int getValue() {
        return value;
    }
    public void setAceLowValue(Boolean aceLowValue){
        if(this.getValue()!=1){
            return;
        }
        if(!aceLowValue){
            this.value =11;
        }
        if(aceLowValue){
            this.value=1;
        }
    }
    public CardType getType() {
        return type;
    }

    @Override
    public String toString(){
        return this.name +" "+this.type+" ";
    }
}
