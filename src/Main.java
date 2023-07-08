
public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        game.addPlayer("Gleb");
        game.addPlayer("Jena");
        game.setPlayersTurn(game.getPlayers());
        System.out.println("trump " + game.getTrump());
        for (Player player :
                game.getPlayers()) {
            System.out.println(player.toString());
        }

    }
}