package controller;

import model.Card;
import model.Deck;
import model.Player;

import java.util.*;

/**
 * This class provides control over deck's behavior during round
 */
public class DeckController {
    Deck deck;

    public DeckController() {
        this.deck = new Deck();
    }

    private void fillUpThePlayersHand(Player player) {
        int playerCardGap = 6 - player.getPlayerHand().size();
        if (!deck.isEmpty()) {
            if (playerCardGap > 0)
                for (int i = 0; i < playerCardGap; i++) {
                    player.getPlayerHand().add(deck.getNextCard());
                }
        }
    }


    public void fillUpTheHands(Deque<Player> queue, Player defender) {
        for (Player thrower : queue) {
            fillUpThePlayersHand(thrower);
        }
        fillUpThePlayersHand(defender);
    }

    public Deck getDeck() {
        return this.deck;
    }
}
