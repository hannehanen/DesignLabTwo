package Enteties;

import java.util.ArrayList;

public class Dealer extends Client{

    public Dealer(String name) {
        super(name);
    }
    public void dealerCalculatingCards(){
        int value = 0;
        int numerOfAces=0;
        ArrayList<Card> aces = new ArrayList<>();
        for(Card card: this.getCards()){
            if(card.getValue()==1||card.getValue()==11){
                numerOfAces+=1;
                aces.add(card);
                value+=1;
            }
            else{
                value += card.getValue();
            }
        }
        if(numerOfAces ==0&&value>=17){
            this.setSatisFiedWithCards(true);
        }
        if(numerOfAces>0&&value<17){
            for(Card card: aces){
                if(value+10>=17){
                    card.setAceLowValue(false);
                    setSatisFiedWithCards(true);
                }
            }
        }

    }
}
