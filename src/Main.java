
public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        game.addPlayer("Gleb");
        game.addPlayer("Elvis");
        game.addPlayer("Costello");

        game.setPlayersTurn(game.getPlayers());
        for (Player p :
                game.getPlayers()) {
            System.out.println(p);
        }
        Round round = new Round(game);
        round.attackMove();
        round.defendMove();

    }
}