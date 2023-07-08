import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
    private Deck deck;
    private List<Player> players = new ArrayList<>();
    boolean isGameOver = false;
    private Card.Suit trump;


    public Game() {
        this.deck = new Deck();
        while (deck.hasNext()){
            Card.Suit suit = deck.next().getSuit();
            if(suit.equals(deck.getTrump())) {
                suit.setTrump(true);
            }
        }
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


    public Card.Suit getTrump() {
        return this.deck.getTrump();
    }

    public void setPlayersTurn (List<Player> players){

    }
}
