package controller.moveValidator;

import model.Card;

import java.util.List;

public class DefenceValidator {
    public boolean isCorrect(List<Card> tableCards, List<Card> defenderCards) {
        int isDefendCorrect = -1;
        int cardsNumberToBeat = tableCards.size();
        int beatenCards = 0;
        for (Card tableCard : tableCards) {
            for (Card defenderCard : defenderCards) {
                isDefendCorrect = defenderCard.compareTo(tableCard);
                if (isDefendCorrect > 0) {
                    beatenCards++;
                    break;
                }
            }
            if (beatenCards == cardsNumberToBeat) break;
        }
        return beatenCards == cardsNumberToBeat;

    }
}
