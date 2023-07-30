package controller.moveValidator;

import model.Card;

import java.util.List;

public class ThrowValidator {
    public boolean isCorrect(List<Card> tableCards, List<Card> throwerCards) {
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
}