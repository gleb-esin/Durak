package controller.move;

import controller.PlayerInputValidator;
import controller.moveValidator.DefenceValidator;
import model.Card;
import model.Player;
import model.Table;

import java.util.List;

import static view.Printer.print;

public class DefenceMove extends PlayerInputValidator implements MoveInterface {
    public void move(Player defender, Table table) {
        DefenceValidator defenceValidator = new DefenceValidator();
        boolean canDefend = defenceValidator.isCorrect(table.getUnbeatenCards(), defender.getPlayerHand());
        //If defender can't beat attacker cards...
        if (!canDefend) {
            print(defender.getName() + " не может отбиться.");
            defender.setRole("binder");
        } else {
            List<Card> cards = askForCards(defender);
            if (cards.isEmpty()) {
                print(defender.getName() + " не будет отбиваться");
                defender.setRole("binder");
            } else {
                boolean isDefendPossible = defenceValidator.isCorrect(table.getUnbeatenCards(), cards);
                while (!isDefendPossible || cards.size() > table.getUnbeatenCards().size()) {
                    if (cards.isEmpty()) {
                        defender.setRole("binder");
                        break;
                    }
                    //...we ask defender for correct cards.
                    print("Так не получится отбиться");
                    cards = askForCards(defender);
                    isDefendPossible = defenceValidator.isCorrect(table.getUnbeatenCards(), cards);
                }
                if(!defender.getRole().equals("binder")) {
                    print(defender.getName() + " отбился");
                    //...we add these cards on the table...
                    table.addCardsToTable(cards, defender, table);
                }
            }
        }
    }
}