import java.util.*;

public class Game {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    boolean isGameOver = false;


    public Game() {
        this.deck = new Deck();
        while (deck.hasNext()) {
            Card.Suit suit = deck.next().getSuit();
            if (suit.equals(deck.getTrump())) {
                suit.setTrump(true);
            }
        }
    }

    public void addPlayer(String name) {
        List<Card> playerHand = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            playerHand.add(deck.getNextCard());
        }
        Comparator<Card> cardComparator = Card::compareTo;
        playerHand.sort(cardComparator.reversed());
        this.players.add(new Player(name, playerHand));
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
                    if (player.getMinTrumpWeight() == null){
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeigth());
                    } else {
                        if (player.getMinTrumpWeight() > player.getPlayerHand().get(i).getValue().getWeigth()){
                            player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeigth());
                        }
                    }
                } else {
                    if (player.getMinTrumpWeight() == null) {
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeigth() + 100);
                    }
                }
            }
        }
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i + 1);
        }
    }
}
