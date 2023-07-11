import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Round {
    Game game;
    Table table;

    Scanner scanner = new Scanner(System.in);

    public Round(Game game) {
        this.game = game;
        this.table = new Table(game.getDeck(), game.getPlayers());
        System.out.println("trump " + table.getDeck().getTrump());
    }

    private boolean isMoveCorrect(List<Card> cards) {
        boolean isCorrectValue = false;
        for (int i = 0; i < cards.size() - 1; i++) {
            isCorrectValue = cards.get(i).getValue().equals(cards.get(i + 1).getValue());
        }
        return isCorrectValue;
    }

    private boolean isDefendPossible(List<Card> cards, List<Card> table) {
        int isDefendCorrect = -1;
        for (Card defenderCard : cards) {
            for (Card tableCard : table) {
                isDefendCorrect = tableCard.compareTo(defenderCard);
            }
        }
        return isDefendCorrect > 0;
    }

    public void attackMove() {
        Player attacker = table.getAttacker();
        System.out.println("Ход " + attacker.getName() + " под " + table.getDefender().getName());
        System.out.println(attacker);
        List<Card> cards = addCardsToTable(attacker);
        for (Card c :
                cards) {
            this.table.getTable().add(c);
        }
        System.out.println(this.table);
    }

    private List<Card> addCardsToTable(Player player) {
        System.out.println("Введите порядковые номера карт в Вашей руке через пробел:");
        String cardIndexes = scanner.nextLine();
        String[] cardIndexesArr = cardIndexes.split(" ");
        boolean cardNumberIsIncorrect = false;
        for (String s : cardIndexesArr) {
            cardNumberIsIncorrect = (player.getPlayerHand().size()<Integer.parseInt(s)) || (Integer.parseInt(s) <= 0);
        }
        while (cardNumberIsIncorrect){
            System.out.println("У Вас нет такой карты.");
            System.out.println("Введите порядковые номера карт в Вашей руке через пробел:");
            cardIndexes = scanner.nextLine();
            cardIndexesArr = cardIndexes.split(" ");
            cardNumberIsIncorrect = false;
            for (String s : cardIndexesArr) {
                cardNumberIsIncorrect = (player.getPlayerHand().size()<Integer.parseInt(s)) || (Integer.parseInt(s) <= 0);
            }
        }
        List<Card> cards = new ArrayList<>();
        for (String s : cardIndexesArr) {
            cards.add(player.getPlayerHand().get(Integer.parseInt(s) - 1));
        }
        if (cards.size() > 1) {
            boolean isMoveCorrect = isMoveCorrect(cards);
            while (!isMoveCorrect) {
                System.out.println("Данной комбинацией карт невозможно пойти");
                cardIndexes = scanner.nextLine();
                cardIndexesArr = cardIndexes.split(" ");
                for (String s : cardIndexesArr) {
                    cards.add(player.getPlayerHand().get(Integer.parseInt(s) - 1));
                }
                isMoveCorrect = isMoveCorrect(cards);
            }
        }
        return cards;
    }


    public void defendMove() {
        List<Card> tableCards = this.table.getTable();
        Player defender = table.getDefender();
        System.out.println("Отбивается " + defender.getName());
        System.out.println(defender);
        boolean canDefend = isDefendPossible(tableCards, defender.getPlayerHand());
        if (!canDefend) {
            System.out.println(defender.getName() + " не может отбиться.");

            System.out.println(this.table);
            for (Player thrower : this.table.getThrowers()) {
                if(isThrowPossible(this.table.getTable(), thrower.getPlayerHand())) throwMove(thrower);
            }
            System.out.println(defender.getName() + " забирает карты.");
            for (Card tableCard : tableCards) {
                defender.getPlayerHand().add(tableCard);
            }
        } else {
            List<Card> cards = addCardsToTable(defender);
            boolean isDefendPossible = isDefendPossible(table.getTable(), cards);
            if (isDefendPossible) {
                System.out.println();
                System.out.println(defender.getName() + " отбился");
                table.getTable().addAll(cards);
                System.out.println(tableCards);
                for (Player thrower : this.table.getThrowers()) {
                    if(isThrowPossible(this.table.getTable(), thrower.getPlayerHand())) throwMove(thrower);
                }
            } else {
                while (!isDefendPossible) {
                    System.out.println("Так не получится отбиться");
                    cards = addCardsToTable(defender);
                    isDefendPossible = isDefendPossible(table.getTable(), cards);
                }
            }
        }
        tableCards.clear();
    }

    private void throwMove(Player thrower) {
        System.out.println(thrower.getName() + ", Вы будете подкидывать?\n" +
                "Напечатайте ответ: \"да\" или \"нет\"");
        System.out.println(thrower);
        String answer = scanner.nextLine().toLowerCase().trim();
        answerChecking:
        if (answer.equals("да")) {
            List<Card> cards = addCardsToTable(thrower);
            this.table.getTable().addAll(cards);
                System.out.println( this.table.getTable());
            defendMove();
        } else if (answer.equals("нет")) {
            break answerChecking;
        } else {
            System.out.println("Ответ  не распознан.\n\"Напечатайте ответ: \\\"да\\\" или \\\"нет\\\"\"");
        }
    }

    private boolean isThrowPossible(List<Card> tableCards, List<Card> throwerCards) {
        boolean isThrowPossible = true;
        for (Card tableCard: tableCards) {
            for (Card throwerCard: throwerCards) {
                if(tableCard.getValue().equals(throwerCard.getValue())) isThrowPossible = false;
                break;
            }
        }
        return isThrowPossible;
    }


}




