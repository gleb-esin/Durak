import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        game.addPlayer("Gleb");
        game.addPlayer("Jena");
        for (Player player :
                game.getPlayers()) {
            System.out.println(player.toString());
        }
        System.out.println("trump " + game.getTrump());
    }
}