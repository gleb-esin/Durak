import java.util.ArrayList;
import java.util.List;

public class Player {
    static int id = 0;
    String name;
    List<Card> playerHand = new ArrayList<>();
    boolean isWinner = false;

    public Player(String name, List<Card> playerHand) {
        this.name = name;
        this.playerHand = playerHand;
    }

    @Override
    public String toString() {
        return name + " " +playerHand;
    }
}
