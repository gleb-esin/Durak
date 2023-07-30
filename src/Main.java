import controller.DeckController;
import controller.PlayerController;
import controller.Game;
import model.Player;
import model.Table;

import java.util.List;

import static view.PlayersPicker.intro;

public class Main {
    public static void main(String[] args)  {
        DeckController deckController = new DeckController();
        List<Player> players = deckController.dealCards(intro());
        PlayerController playerController = new PlayerController();
        playerController.setPlayersTurn(players);
        Game game = new Game(new Table(), deckController, playerController);
        game.start();
    }
}
