import controller.Game;

import java.util.Scanner;

import static view.Intro.intro;

public class Main {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(intro(scanner));
        game.start();
        scanner.close();
    }

}
