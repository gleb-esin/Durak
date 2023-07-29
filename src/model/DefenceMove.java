package model;

import java.util.List;

import static view.Printer.print;

public class DefenceMove extends AbstractMove implements MoveInterface {
    public void move(Player defender, Table table) {
        boolean canDefend = isPossible(table.getUnbeatenCards(), defender.getPlayerHand());
        //If defender can't beat attacker cards...
        if (!canDefend) {
            print(table);
            print(defender);
            print(defender.getName() + " не может отбиться.");
            defender.setRole("binder");
        } else {
            print("Отбивается " + defender.getName() + ", козырь " + table.getTrump());
            print(defender);
            //If defender can beat attacker cards...
            //... defender lays out his cards...
            List<Card> cards = askForCards(defender);
            if (cards.isEmpty()) {
                print(defender.getName() + " не будет отбиваться");
                defender.setRole("binder");
            } else {
                boolean isDefendPossible = isPossible(table.getUnbeatenCards(), cards);
                while (!isDefendPossible || cards.size() > table.getUnbeatenCards().size()) {
                    if (cards.isEmpty()) {
                        defender.setRole("binder");
                        break;
                    }
                    //...we ask defender for correct cards.
                    print("Так не получится отбиться");
                    cards = askForCards(defender);
                    isDefendPossible = isPossible(table.getUnbeatenCards(), cards);
                }
                if(!defender.getRole().equals("binder")) {
                    print(defender.getName() + " отбился");
                    //...we add these cards on the table...
                    addCardsToTable(cards, defender, table);
                }
            }
        }
    }


    public boolean isPossible(List<Card> tableCards, List<Card> defenderCards) {
        int isDefendCorrect = -1;
        int cardsNumberToBeat = tableCards.size();
        int beatenCards = 0;
        for (Card tableCard : tableCards) {
            for (Card defenderCard : defenderCards) {
                isDefendCorrect = defenderCard.compareTo(tableCard);
                System.out.println(tableCard + " vs " + defenderCard + " = " + isDefendCorrect);
                if (isDefendCorrect > 0) {
                    beatenCards++;
                    break;
                }
            }
            if (beatenCards == cardsNumberToBeat) break;
        }
        System.out.println("beatenCards " + beatenCards);
        System.out.println("cardsNumberToBeat "+ cardsNumberToBeat);
        return beatenCards == cardsNumberToBeat;

    }
}
