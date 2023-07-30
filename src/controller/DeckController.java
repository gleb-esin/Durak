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

    public List<Player> dealCards(String[] namesArr) {
        Comparator<Card> cardComparator = Card::compareTo;
        List<Player> players = new ArrayList<>();
        for (String s : namesArr) {
            List<Card> playerHand = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                playerHand.add(deck.getNextCard());
            }
            playerHand.sort(cardComparator);
            players.add(new Player(s, playerHand));
        }
        return players;
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
