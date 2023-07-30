import controller.Game;

import java.util.Scanner;

import static view.Intro.intro;

public class Main {
    public static void main(String[] args)  {
        Game game = new Game(intro());
        game.start();
    }

}
