package view;

import model.Player;

import java.util.Scanner;

import static view.Printer.print;

public class ClearConsole {
    static Scanner scanner = new Scanner(System.in);
    public static void clearConsole(String name) {
        //this method only for IDEA console
        print("Передайте управление " + name + " и нажмите Enter");
        String answer = scanner.nextLine();
        while (!answer.isEmpty()) {
            print("Передайте управление " + name + " и нажмите Enter");
            answer = scanner.nextLine();
        }
        for (int i = 1; i <= 26; i++) {
            System.out.println();
        }
    }
}
