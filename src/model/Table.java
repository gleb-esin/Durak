package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Card> beatenCards = new ArrayList<>();
    private List<Card> unbeatenCards = new ArrayList<>();

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Карты на столе\n");
        for (Card c : beatenCards) {
            stringBuffer.append(c.toString() + " ");
        }
        stringBuffer.append("\n");
        for (Card c : unbeatenCards) {
            stringBuffer.append(c.toString() + " ");
        }
        return stringBuffer.toString();
    }

    public List<Card> getBeatenCards() {
        return beatenCards;
    }

    public void setBeatenCard(Card beatenCard) {
        this.beatenCards.add(beatenCard);
    }

    public List<Card> getUnbeatenCards() {
        return unbeatenCards;
    }

    public void setUnbeatenCard(Card beatenCard) {
        unbeatenCards.add(beatenCard);
    }
}
