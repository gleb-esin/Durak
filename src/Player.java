import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {
    private String name;
    private List<Card> playerHand;
    private boolean isWinner = false;
     private int turn;


    public Player(String name, List<Card> playerHand) {
        this.name = name;
        this.playerHand = playerHand;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        Comparator<Card> cardComparator = Card::compareTo;
        playerHand.sort(cardComparator);
        return name + " " +playerHand;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }
}
