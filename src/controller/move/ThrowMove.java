package controller.move;

import controller.PlayerInputValidator;
import controller.TableController;
import model.Card;
import model.Player;

import java.util.List;

import static controller.moveValidator.ThrowValidator.isThrowMoveCorrect;
import static view.Printer.print;

public class ThrowMove extends PlayerInputValidator implements MoveInterface {

    @Override
    public void move(Player thrower, TableController tableController) {
            List<Card> cards = askForCards(thrower);
            if (cards.isEmpty()) {
                print(thrower.getName() + ", не будет подкидывать.");
            } else {
                boolean isThrowCorrect = isThrowMoveCorrect(tableController.getAll(), cards);
                while (!isThrowCorrect) {
                    print(thrower.getName() + " , так не получится подкинуть.");
                    cards = askForCards(thrower);
                    isThrowCorrect = isThrowMoveCorrect(tableController.getAll(), cards);
                }
                tableController.addCardsToTable(cards, thrower);
            }
    }
}
