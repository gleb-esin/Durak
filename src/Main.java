import controller.DeckController;
import controller.Game;
import controller.PlayerController;
import controller.TableController;

import static view.PlayersPicker.playersPicker;

public class Main {
    public static void main(String[] args)  {
        String[] namesArr = playersPicker();
        PlayerController playerController = new PlayerController(namesArr);
        playerController.setPlayersTurn();
        DeckController deckController = new DeckController();
        deckController.fillUpTheHands(playerController.getQueue(), playerController.getDefender());

        Game game = new Game(new TableController(), deckController, playerController);
        game.start();
    }
}
