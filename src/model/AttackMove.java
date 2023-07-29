package model;

import java.util.List;

import static view.Printer.print;

public class AttackMove extends AbstractMove implements MoveInterface {


    public boolean isCorrect(List<Card> cards) {
        boolean isMoveCorrect = false;
        for (int i = 0; i < cards.size() - 1; i++) {
            isMoveCorrect = cards.get(i).getValue().equals(cards.get(i + 1).getValue());
        }
        return isMoveCorrect;
    }

    @Override
    public void move(Player attacker, Table table) {
        List<Card> cards = askForCards(attacker);
        boolean isMoveCorrect = isCorrect(cards);
        while (cards.isEmpty() || (cards.size() > 1 && !isMoveCorrect)) {
            print("Так пойти не получится.");
            cards = askForCards(attacker);
            isMoveCorrect = isCorrect(cards);
        }
        addCardsToTable(cards, attacker, table);
        attacker.setRole("thrower");
    }
}
