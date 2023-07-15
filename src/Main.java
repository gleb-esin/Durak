import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имена игроков через пробел: ");
        String names = scanner.nextLine();
        String[] namesArr = names.split(" ");
        while (namesArr.length <2){
            System.out.println("А с кем играть-то? Поврторите ввод:");
            names = scanner.nextLine();
        }
        Game game = new Game(namesArr);
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
