package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    Card.Suit trump;
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
    public void setBeatenCards(Card beatenCard) {
        this.beatenCards.add(beatenCard);
    }

    public List<Card> getUnbeatenCards() {
        return unbeatenCards;
    }

    public void setUnbeatenCards(Card unbeatenCard) {
        this.unbeatenCards.add(unbeatenCard);
    }

    public void clear(){
        unbeatenCards.clear();
        beatenCards.clear();
    }

    public List<Card> getAll(){
        List<Card> allCards = new ArrayList<>(beatenCards);
        allCards.addAll(unbeatenCards);
        return allCards;
    }

    public boolean isEmpty() {
        return unbeatenCards.isEmpty() == beatenCards.isEmpty();
    }

    public Card.Suit getTrump() {
        return trump;
    }

    public void setTrump(Card.Suit trump) {
        this.trump = trump;
    }
    public void addCardsToTable(List<Card> playerCards, Player player, Table table) {
        if (player.getRole().equals("defender")) {
            Card unbeatenCard;
            for (int i = 0; i < playerCards.size(); i++) {
                unbeatenCard = table.getUnbeatenCards().get(i);
                table.setBeatenCards(unbeatenCard);
                table.setBeatenCards(playerCards.get(i));
                player.getPlayerHand().remove(playerCards.get(i));
            }
            table.getUnbeatenCards().clear();

        } else {
            for (Card c : playerCards) {
                table.setUnbeatenCards(c);
                player.getPlayerHand().remove(c);
            }
        }
    }
}
