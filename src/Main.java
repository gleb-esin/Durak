import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Deck deck = new Deck();
        System.out.println(deck);
        Game game = new Game();
        game.addPlayer("Gleb");
        game.addPlayer("Alex");
        for (Player player:
                game.getPlayers()) {
            System.out.println(player.toString());
        }
        System.out.println(game.getDeck().toString());
    }
}