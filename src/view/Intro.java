package view;

import java.util.Scanner;

import static view.Printer.print;

public class Intro {
    public static String[] intro(Scanner scanner){
        System.out.println(
                        "██████╗ ██╗   ██╗██████╗  █████╗ ██╗  ██╗\n" +
                        "██╔══██╗██║   ██║██╔══██╗██╔══██╗██║ ██╔╝\n" +
                        "██║  ██║██║   ██║██████╔╝███████║█████╔╝ \n" +
                        "██║  ██║██║   ██║██╔══██╗██╔══██║██╔═██╗ \n" +
                        "██████╔╝╚██████╔╝██║  ██║██║  ██║██║  ██╗\n" +
                        "╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝");

        print("Введите имена игроков через пробел: ");
        String[] namesArr = scanner.nextLine().split(" ");
        while (namesArr.length < 2) {
            System.out.println("А с кем играть-то? Повторите ввод:");
            namesArr = scanner.nextLine().split(" ");
        }
        while (namesArr.length > 6) {
            System.out.println("Максимум 6 игроков - карт-то всего 36... Повторите ввод:");

            namesArr = scanner.nextLine().split(" ");
        }
        return namesArr;
    }
}
