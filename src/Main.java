
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer("Luk");
        game.addPlayer("Dart");
        game.addPlayer("Yoda");
        game.setPlayersTurn(game.getPlayers());
        Round round = new Round(game);
        while (!game.isGameOver()){
            round.attackMove();
            round.defendMove();
            round.fillUpTheHands();
            round.changeTurn();
        }
    }
}
