package controller.move;

import controller.PlayerInputValidator;
import controller.TableController;
import model.Card;
import model.Player;
import java.util.List;
import static controller.moveValidator.AttackValidator.isCorrect;
import static view.Printer.print;

public class AttackMove extends PlayerInputValidator implements MoveInterface {
    @Override
    public void move(Player attacker, TableController tableController) {
        List<Card> cards = askForCards(attacker);
        boolean isMoveCorrect = isCorrect(cards);
        while (cards.isEmpty() || (cards.size() > 1 && !isMoveCorrect)) {
            print("Так пойти не получится.");
            cards = askForCards(attacker);
            isMoveCorrect = isCorrect(cards);
        }
        tableController.addCardsToTable(cards, attacker);
        attacker.setRole("thrower");
    }
}
