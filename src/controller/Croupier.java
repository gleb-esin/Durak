package controller;

import model.Card;
import model.Deck;
import model.Player;

import java.util.*;

public class Croupier {
    Deck deck;

    public Croupier() {
        this.deck = new Deck();
    }

    public List<Player> dealCards(String[] namesArr){
        while (deck.hasNext()) {
            Card.Suit suit = deck.next().getSuit();
            if (suit.equals(deck.getTrump())) {
                suit.setTrump(true);
            }
        }
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
    public List<Player> setPlayersTurn(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < player.getPlayerHand().size(); i++) {
                if (player.getPlayerHand().get(i).getSuit().getTrump() == true) {
                    if (player.getMinTrumpWeight() == null) {
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight());
                    } else {
                        if (player.getMinTrumpWeight() > player.getPlayerHand().get(i).getValue().getWeight()) {
                            player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight());
                        }
                    }
                } else {
                    if (player.getMinTrumpWeight() == null) {
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight() + 100);
                    }
                }
            }
        }
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i + 1);
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
