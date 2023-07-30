package controller.move;

import controller.PlayerInputValidator;
import controller.TableController;
import controller.moveValidator.AttackValidator;
import model.Card;
import model.Player;
import model.Table;

import java.util.List;

import static view.Printer.print;

public class AttackMove extends PlayerInputValidator implements MoveInterface {
    @Override
    public void move(Player attacker, TableController tableController) {
        List<Card> cards = askForCards(attacker);
        AttackValidator attackValidator = new AttackValidator();
        boolean isMoveCorrect = attackValidator.isCorrect(cards);
        while (cards.isEmpty() || (cards.size() > 1 && !isMoveCorrect)) {
            print("Так пойти не получится.");
            cards = askForCards(attacker);
            isMoveCorrect = attackValidator.isCorrect(cards);
        }
        tableController.addCardsToTable(cards, attacker);
        attacker.setRole("thrower");
    }
}
