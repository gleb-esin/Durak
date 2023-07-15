import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имена игроков через пробел: ");
        String[] namesArr = scanner.nextLine().split(" ");
        while (namesArr.length <2){
            System.out.println("А с кем играть-то? Повторите ввод:");
            namesArr = scanner.nextLine().split(" ");
        }
        Game game = new Game(namesArr);
        game.setPlayersTurn(game.getPlayers());
        Round round = new Round(game);
        int move = 1;
        while (!game.isGameOver()){
            round.attackMove();
            round.defendMove();
            round.fillUpTheHands();
            round.changeTurn();
        }
        System.out.println(game.getWinner() + " победитель!");
    }
}
