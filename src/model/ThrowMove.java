package model;

import java.util.List;

import static view.Printer.print;

public class ThrowMove extends AbstractMove implements MoveInterface {

    @Override
    public void move(Player thrower, Table table) {
            List<Card> cards = askForCards(thrower);
            if (cards.isEmpty()) {
                print(thrower.getName() + ", не будет подкидывать.");
            } else {
                boolean isThrowCorrect = isThrowCorrect(table.getAll(), cards);
                while (!isThrowCorrect) {
                    print(thrower.getName() + " , так не получится подкинуть.");
                    cards = askForCards(thrower);
                    isThrowCorrect = isThrowCorrect(table.getAll(), cards);
                }
                addCardsToTable(cards, thrower, table);
            }
    }


    public boolean isThrowPossible(List<Card> tableCards, List<Card> throwerHand) {
        boolean isThrowPossible = false;
        for (Card tableCard : tableCards) {
            for (Card throwerCard : throwerHand) {
                if (tableCard.getValue().equals(throwerCard.getValue())) {
                    isThrowPossible = true;
                    break;
                }
            }
            if (isThrowPossible) break;
        }
        return isThrowPossible;
    }

    private boolean isThrowCorrect(List<Card> tableCards, List<Card> throwerCards) {
        boolean isThrowCorrect;
        int thrownCards = throwerCards.size();
        int allowedCards = 0;
        for (Card throwerCard : throwerCards) {
            for (Card tableCard : tableCards) {
                isThrowCorrect = tableCard.getValue().equals(throwerCard.getValue());
                if (isThrowCorrect) {
                    allowedCards++;
                    break;
                }
            }
            if (thrownCards == allowedCards) break;
        }
        return thrownCards == allowedCards;
    }
}
