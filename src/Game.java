import java.util.*;

public class Game {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    private boolean isGameOver = false;
    private Player winner;


    public Game(String[] namesArr) {
        this.deck = new Deck();
        while (deck.hasNext()) {
            Card.Suit suit = deck.next().getSuit();
            if (suit.equals(deck.getTrump())) {
                suit.setTrump(true);
            }
        }
        Comparator<Card> cardComparator = Card::compareTo;
        for (String s : namesArr) {
            List<Card> playerHand = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                playerHand.add(deck.getNextCard());
            }
            playerHand.sort(cardComparator);
            this.players.add(new Player(s, playerHand));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Card.Suit getTrump() {
        return this.deck.getTrump();
    }

    public void setPlayersTurn(List<Player> players) {
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
    }

    public Deck getDeck() {
        return deck;

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
