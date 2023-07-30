package controller.move;

import controller.PlayerInputValidator;
import controller.TableController;
import controller.moveValidator.ThrowValidator;
import model.Card;
import model.Player;
import model.Table;

import java.util.List;

import static view.Printer.print;

public class ThrowMove extends PlayerInputValidator implements MoveInterface {

    @Override
    public void move(Player thrower, TableController tableController) {
        ThrowValidator throwValidator = new ThrowValidator();
            List<Card> cards = askForCards(thrower);
            if (cards.isEmpty()) {
                print(thrower.getName() + ", не будет подкидывать.");
            } else {
                boolean isThrowCorrect = throwValidator.isCorrect(tableController.getAll(), cards);
                while (!isThrowCorrect) {
                    print(thrower.getName() + " , так не получится подкинуть.");
                    cards = askForCards(thrower);
                    isThrowCorrect = throwValidator.isCorrect(tableController.getAll(), cards);
                }
                tableController.addCardsToTable(cards, thrower);
            }
    }
}
