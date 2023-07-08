import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    boolean isGameOver = false;

    public Game() {
        this.deck = new Deck();
    }

    public void addPlayer (String name) {
        List<Card> playerHand = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            playerHand.add(deck.getNextCard());

        }
        this.players.add(new Player(name,  playerHand));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }
}
