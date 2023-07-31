package controller;

import model.Card;
import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.Printer.print;

public class PlayerInputValidator {
    Scanner scanner = new Scanner(System.in);

    protected List<Card> askForCards(Player player) {
        List<Card> cards = new ArrayList<>();
        String message = "";
        if (player.getRole().equals("attacker")) {
            message = player.getName() + ", введите порядковые номера карт в Вашей руке через пробел:";
        } else {
            message = player.getName() + ", введите порядковые номера карт в Вашей руке через пробел:\n(Если хотите пропустить ход, напечатайте \"0\")";
        }
        print(message);
        String cardIndexes = readNonEmptyLine();
        String[] cardIndexesArr = cardIndexes.split(" ");
        Pattern pattern = Pattern.compile("^(0|[1-9]\\d*)$");
        for (String s : cardIndexesArr) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                if (Integer.parseInt(s) == 0) {
                    cards.clear();
                    break;
                } else {
                    boolean correctInput = Integer.parseInt(s) <= player.getPlayerHand().size();
                    if (correctInput) {
                        cards.add(player.getPlayerHand().get(Integer.parseInt(s) - 1));
                    } else {
                        while (!correctInput) {
                            print(player.getName() + ", такой карты нет. Повторите ввод.");
                            cardIndexes = readNonEmptyLine();
                            cardIndexesArr = cardIndexes.split(" ");
                            for (String st : cardIndexesArr) {
                                matcher = pattern.matcher(st);
                                if (matcher.find()) {
                                    if (Integer.parseInt(st) == 0) {
                                        cards.clear();
                                        break;
                                    } else {
                                        correctInput = Integer.parseInt(st) <= player.getPlayerHand().size();
                                        if (correctInput) {
                                            cards.add(player.getPlayerHand().get(Integer.parseInt(st) - 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(cards);
        return cards;
    }


    private String readNonEmptyLine() {
        String input = scanner.nextLine();
        input.trim();
        while (input.isEmpty()) {
            print("Пустой ввод. Пожалуйста, введите значение.");
            input = scanner.nextLine().trim();
        }
        return input;
    }
}
