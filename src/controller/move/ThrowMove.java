package controller.move;

import controller.PlayerInputValidator;
import controller.TableController;
import controller.moveValidator.ThrowValidator;
import model.Card;
import model.Player;
import model.Table;

import java.util.List;

import static controller.moveValidator.ThrowValidator.isCorrect;
import static view.Printer.print;

public class ThrowMove extends PlayerInputValidator implements MoveInterface {

    @Override
    public void move(Player thrower, TableController tableController) {
            List<Card> cards = askForCards(thrower);
            if (cards.isEmpty()) {
                print(thrower.getName() + ", не будет подкидывать.");
            } else {
                boolean isThrowCorrect = isCorrect(tableController.getAll(), cards);
                while (!isThrowCorrect) {
                    print(thrower.getName() + " , так не получится подкинуть.");
                    cards = askForCards(thrower);
                    isThrowCorrect = isCorrect(tableController.getAll(), cards);
                }
                tableController.addCardsToTable(cards, thrower);
            }
    }
}
