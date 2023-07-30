import controller.Croupier;
import controller.PlayerController;
import controller.Game;
import model.Player;
import model.Table;

import java.util.List;

import static view.PlayersPicker.intro;

public class Main {
    public static void main(String[] args)  {
        Croupier croupier = new Croupier();
        List<Player> players = croupier.dealCards(intro());
        PlayerController playerController = new PlayerController();
        playerController.setPlayersTurn(players);
        Game game = new Game(new Table(), croupier, playerController);
        game.start();
    }
}
