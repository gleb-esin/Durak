package controller;

import model.Card;
import model.Player;
import model.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides control over tables' behavior during round
 */
public class TableController {
    private Table table;

    public TableController() {
        table = new Table();
    }

    public void clear(){
        table.getUnbeatenCards().clear();
        table.getBeatenCards().clear();
    }

    public List<Card> getAll(){
        List<Card> allCards = new ArrayList<>(table.getBeatenCards());
        allCards.addAll(table.getUnbeatenCards());
        return allCards;
    }

    public boolean isEmpty() {
        return table.getUnbeatenCards().isEmpty() == table.getBeatenCards().isEmpty();
    }

    public void addCardsToTable(List<Card> playerCards, Player player) {
        if (player.getRole().equals("defender")) {
            Card unbeatenCard;
            for (int i = 0; i < playerCards.size(); i++) {
                unbeatenCard = table.getUnbeatenCards().get(i);
                table.setBeatenCard(unbeatenCard);
                table.setBeatenCard(playerCards.get(i));
                player.getPlayerHand().remove(playerCards.get(i));
            }
            table.getUnbeatenCards().clear();

        } else {
            for (Card c : playerCards) {
                table.setUnbeatenCard(c);
                player.getPlayerHand().remove(c);
            }
        }
    }

    public Table getTable() {
        return table;
    }
}
