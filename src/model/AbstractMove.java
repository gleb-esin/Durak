package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.Printer.print;

public class AbstractMove {
    Scanner scanner = new Scanner(System.in);
    void addCardsToTable(List<Card> playerCards, Player player, Table table) {
        if (player.getRole().equals("defender")) {
            Card unbeatenCard;
            for (int i = 0; i < playerCards.size(); i++) {
                unbeatenCard = table.getUnbeatenCards().get(i);
                table.setBeatenCards(unbeatenCard);
                table.setBeatenCards(playerCards.get(i));
                player.getPlayerHand().remove(playerCards.get(i));
            }
            table.getUnbeatenCards().clear();

        } else {
            for (Card c : playerCards) {
                table.setUnbeatenCards(c);
                player.getPlayerHand().remove(c);
            }
        }
    }


    List<Card> askForCards(Player player) {
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
                    if (Integer.parseInt(s) <= player.getPlayerHand().size()) {
                        cards.add(player.getPlayerHand().get(Integer.parseInt(s) - 1));
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
