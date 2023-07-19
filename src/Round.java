import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Round {
    private Game game;
    private Deck deck;
    private Table table;
    private Player attacker;
    private Player defender;
    private Player binder;
    private Deque<Player> queue;
    Scanner scanner = new Scanner(System.in);
    public Round(Game game) {
        this.deck = game.getDeck();
        this.queue = new LinkedList<>(game.getPlayers());
        this.attacker = this.queue.pop();
        this.defender = this.queue.pop();
        this.queue.addFirst(this.attacker);
        this.table = new Table();
        this.game = game;

    }

    public void attackMove() {
        if (!game.isGameOver()){
            clearConsole(getAttacker());
            print("Ход " + getAttacker().getName() + " под " + getDefender().getName()
                    + ", козырь " + getDeck().getTrump());
            print(getAttacker());
            List<Card> cards = askForCards(getAttacker());
            boolean isMoveCorrect = isMoveCorrect(cards);
            while (cards.isEmpty() || (cards.size() > 1 && !isMoveCorrect)) {
                print("Так пойти не получится.");
                cards = askForCards(getAttacker());
                isMoveCorrect = isMoveCorrect(cards);
            }
            addCardsToTable(cards, getAttacker());
            isPlayerWinner(getAttacker());
        }
    }


    public void defendMove() {
        if (!game.isGameOver()) {
            Player defender = getDefender();
            List<Card> unbeatenCards = getTable().getUnbeatenCards();
            clearConsole(defender);
            print(getTable());
            boolean canDefend = isDefendPossible(getTable().getUnbeatenCards(), defender.getPlayerHand());
            //If defender can't beat attacker cards...
            if (!canDefend) {
                print(defender);
                print(defender.getName() + " не может отбиться.");
                getTable().toString();
                //...we ask throwers for throw...
                throwMove(getQueue());
                //...and defender takes table cards
                print(defender.getName() + " забирает карты " + getTable().getAll());
                for (int i = 0; i < getTable().getAll().size(); i++) {
                    defender.getPlayerHand().add(getTable().getAll().get(i));
                }
                unbeatenCards.clear();
                setBinder(getDefender());
            } else {
                print("Отбивается " + defender.getName() + ", козырь " + getDeck().getTrump());
                print(defender);
                //If defender can beat attacker cards...
                //... defender lays out his cards...
                List<Card> cards = askForCards(defender);
                if (cards.isEmpty()) {
                    throwMove(getQueue());
                    print(defender.getName() + " забирает карты " + getTable().getAll());
                    for (int i = 0; i < unbeatenCards.size(); i++) {
                        defender.getPlayerHand().add(unbeatenCards.get(i));
                    }
                    unbeatenCards.clear();
                    setBinder(getDefender());
                } else {
                    boolean isDefendPossible = isDefendPossible(unbeatenCards, cards);
                    if (isDefendPossible) {
                        // If defenders cards are correct...
                        print(defender.getName() + " отбился");
                        //...we add these cards on the table...
                        addCardsToTable(cards, defender);
                        isPlayerWinner(getAttacker());
                        //...and then ask throwers for throw.
                        if (!game.isGameOver()) throwMove(getQueue());
                    } else {
                        //While defender cards aren't correct...
                        while (!isDefendPossible) {
                            //...we ask defender for correct cards.
                            print("Так не получится отбиться");
                            cards = askForCards(defender);
                            isDefendPossible = isDefendPossible(unbeatenCards, cards);
                            print(defender.getName() + " отбился");
                            //...we add these cards on the table...
                            addCardsToTable(cards, getDefender());
                            isPlayerWinner(getAttacker());
                            //...and then ask throwers for throw.
                            if (!game.isGameOver()) throwMove(getQueue());
                        }
                    }
                }

            }
            getTable().clear();
        }
    }


    public void throwMove(Deque<Player> throwers) {
        if (!game.isGameOver()) {
            for (Player thrower : throwers) {
                if (isThrowPossible(getTable().getAll(), thrower.getPlayerHand())) {
                    clearConsole(thrower);
                    print(getTable());
                    print(thrower.getName() + ", Вы можете подкинуть. Козырь " + getDeck().getTrump());
                    print(thrower);
                    List<Card> cards = askForCards(thrower);
                    if (cards.isEmpty()) {
                        print(thrower.getName() + ", не будет подкидывать.");
                    } else {
                        if (isThrowPossible(getTable().getAll(), thrower.getPlayerHand())) {
                            addCardsToTable(cards, thrower);
                            print(thrower);
                            getTable().toString();
                            if (!game.isGameOver()) defendMove();
                        }
                    }
                } else {
                    clearConsole(thrower);
                    print(thrower.getName() + " не может подкинуть.");
                }
            }
        }
    }

    private boolean isThrowPossible(List<Card> tableCards, List<Card> cards) {
        boolean isThrowPossible = false;
        for (Card tableCard : tableCards) {
            for (Card throwerCard : cards) {
                if (tableCard.getValue().equals(throwerCard.getValue())) {
                    isThrowPossible = true;
                    break;
                }
            }
            if (isThrowPossible) break;
        }
        return isThrowPossible;
    }


    private List<Card> askForCards(Player player) {
        List<Card> cards = new ArrayList<>();
        String message = "";
        if (getTable().isEmpty()) {
            message = player.getName() + ", введите порядковые номера карт в Вашей руке через пробел:";
        } else {
            message = player.getName() + ", введите порядковые номера карт в Вашей руке через пробел:\n(Если хотите пропустить ход, напечатайте \"0\")";
        }
        print(message);
        String cardIndexes = readNonEmptyLine(scanner);
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

    public Table getTable() {
        return table;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    public void setDefender(Player defender) {
        this.defender = defender;
    }

    public Player getBinder() {
        return binder;
    }

    public void setBinder(Player binder) {
        this.binder = binder;
    }

    private void fillUpThePlayersHand(Player player) {
        int playerCardGap = 6 - player.getPlayerHand().size();
        if (!getDeck().isEmpty()) {
            if (playerCardGap > 0)
                for (int i = 0; i < playerCardGap; i++) {
                    player.getPlayerHand().add(getDeck().getNextCard());
                }
        }
    }


    public void fillUpTheHands() {
        for (Player thrower : getQueue()) {
            fillUpThePlayersHand(thrower);
        }
        fillUpThePlayersHand(getDefender());
    }

    public void changeTurn() {
        getQueue().addLast(getQueue().pop());
        if (getBinder() == null) {
            setAttacker(getDefender());
        } else {
            getQueue().addLast(getBinder());
            if (getQueue().size() > 1) setAttacker(getQueue().pop());

        }
        setDefender(getQueue().pop());
        getQueue().addFirst(getAttacker());
        setBinder(null);
    }

    private void clearConsole(Player player) {
        print("Передайте управление " + player.getName() + " и нажмите Enter");
        String answer = scanner.nextLine();
        while (!answer.isEmpty()) {
            print("Передайте управление " + player.getName() + " и нажмите Enter");
            answer = scanner.nextLine();
        }
        for (int i = 1; i <= 8; i++) {
            System.out.println();
            ;
        }
    }

    private void print(Object o) {
        System.out.println(o.toString());
    }

    private void addCardsToTable(List<Card> playerCards, Player player) {
        if (getDefender().equals(player)) {
            Card unbeatenCard;
            for (int i = 0; i < playerCards.size(); i++) {
                unbeatenCard = getTable().getUnbeatenCards().get(i);
                getTable().setBeatenCards(unbeatenCard);
                getTable().setBeatenCards(playerCards.get(i));
                player.getPlayerHand().remove(playerCards.get(i));
            }
            getTable().getUnbeatenCards().clear();

        } else {
            for (Card c : playerCards) {
                getTable().setUnbeatenCards(c);
                player.getPlayerHand().remove(c);
            }
        }
    }

    private boolean isMoveCorrect(List<Card> cards) {
        boolean isMoveCorrect = false;
        for (int i = 0; i < cards.size() - 1; i++) {
            isMoveCorrect = cards.get(i).getValue().equals(cards.get(i + 1).getValue());
        }
        return isMoveCorrect;
    }

    private boolean isDefendPossible(List<Card> tableCards, List<Card> defenderCards) {
        int isDefendCorrect = -1;
        int cardsNumberToBeat = tableCards.size();
        int beatenCards = 0;
        for (Card defenderCard : defenderCards) {
            for (Card tableCard : tableCards) {
                isDefendCorrect = defenderCard.compareTo(tableCard);
                if (isDefendCorrect > 0) {
                    beatenCards++;
                    break;
                }
            }
            if (beatenCards == cardsNumberToBeat) break;
        }
        return beatenCards == cardsNumberToBeat;
    }

    public Deque<Player> getQueue() {
        return queue;
    }

    private boolean isPlayerWinner(Player player) {
        boolean isWinner = getDeck().isEmpty() && player.getPlayerHand().isEmpty();
        if (isWinner) {
            player.setWinner(true);
            this.game.setGameOver(true);
            this.game.setWinner(player);
        }
        return isWinner;
    }

    private String readNonEmptyLine(Scanner scanner) {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            print("Пустой ввод. Пожалуйста, введите значение.");
            input = scanner.nextLine().trim();
        }
        return input;
    }
}





