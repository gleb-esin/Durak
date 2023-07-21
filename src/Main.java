import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "██████╗ ██╗   ██╗██████╗  █████╗ ██╗  ██╗\n" +
                "██╔══██╗██║   ██║██╔══██╗██╔══██╗██║ ██╔╝\n" +
                "██║  ██║██║   ██║██████╔╝███████║█████╔╝ \n" +
                "██║  ██║██║   ██║██╔══██╗██╔══██║██╔═██╗ \n" +
                "██████╔╝╚██████╔╝██║  ██║██║  ██║██║  ██╗\n" +
                "╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝");

        System.out.println("Введите имена игроков через пробел: ");
        String[] namesArr = scanner.nextLine().split(" ");
        while (namesArr.length <2){
            System.out.println("А с кем играть-то? Повторите ввод:");
            namesArr = scanner.nextLine().split(" ");
        }
        while (namesArr.length >6){
            System.out.println("Максимум 6 игроков - карт-то всего 36... Повторите ввод:");

            namesArr = scanner.nextLine().split(" ");
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
        System.out.println(game.getWinner() + " победитель!");
    }
}
