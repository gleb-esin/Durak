import controller.Croupier;
import controller.PlayerController;
import controller.Round;
import model.Player;
import model.Table;

import java.util.List;

import static view.Intro.intro;

public class Main {
    public static void main(String[] args)  {
        Croupier croupier = new Croupier();
        List<Player> players = croupier.dealCards(intro());
        players = croupier.setPlayersTurn(players);
        Round round = new Round(new Table(), croupier, new PlayerController(players));
        round.start();
    }
}
