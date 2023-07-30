package controller;

import controller.move.AttackMove;
import controller.move.DefenceMove;
import controller.move.MoveInterface;
import controller.move.ThrowMove;
import controller.moveValidator.ThrowValidator;
import model.*;

import java.util.*;

import static view.ClearConsole.clearConsole;
import static view.Printer.print;

public class Game {
    private Deck deck;
    private Table table;
    private Player attacker;
    private Player defender;
    private Player binder;
    private Deque<Player> queue;
    private List<Player> players = new ArrayList<>();
    private boolean isGameOver = false;
    private Player winner;

    public Game(String[] namesArr) {
        this.deck = new Deck();
        while (deck.hasNext()) {
            Card.Suit suit = deck.next().getSuit();
            if (suit.equals(deck.getTrump())) {
                suit.setTrump(true);
            }
        }
        Comparator<Card> cardComparator = Card::compareTo;
        for (String s : namesArr) {
            List<Card> playerHand = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                playerHand.add(deck.getNextCard());
            }
            playerHand.sort(cardComparator);
            this.players.add(new Player(s, playerHand));
        }
        this.queue = new LinkedList<>(this.players);
        setAttacker(this.queue.pop());
        setDefender(this.queue.pop());
        this.queue.addFirst(this.attacker);
        this.table = new Table();
        table.setTrump(deck.getTrump());
    }

    public Table getTable() {
        return table;
    }

    public Deque<Player> getQueue() {
        return queue;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public void setAttacker(Player player) {
        player.setRole("attacker");
        this.attacker = player;
    }

    public void setDefender(Player player) {
        player.setRole("defender");
        this.defender = player;
    }

    public Player getBinder() {
        return binder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayersTurn(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < player.getPlayerHand().size(); i++) {
                if (player.getPlayerHand().get(i).getSuit().getTrump() == true) {
                    if (player.getMinTrumpWeight() == null) {
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight());
                    } else {
                        if (player.getMinTrumpWeight() > player.getPlayerHand().get(i).getValue().getWeight()) {
                            player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight());
                        }
                    }
                } else {
                    if (player.getMinTrumpWeight() == null) {
                        player.setMinTrumpWeight(player.getPlayerHand().get(i).getValue().getWeight() + 100);
                    }
                }
            }
        }
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i + 1);
        }
    }

    public Deck getDeck() {
        return deck;

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    private boolean isPlayerWinner(Player player) {
        boolean isWinner = getDeck().isEmpty() && player.getPlayerHand().isEmpty();
        if (isWinner) {
            player.setWinner(true);
            setGameOver(true);
            setWinner(player);
        }
        return isWinner;
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

    public void start() {
        setPlayersTurn(getPlayers());
        MoveInterface attackMove = new AttackMove();
        MoveInterface defenceMove = new DefenceMove();
        MoveInterface throwMove = new ThrowMove();
        ThrowValidator moveValidator = new ThrowValidator();
        gameloop:
        while (!isGameOver()) {

            //attack move
            clearConsole(getAttacker().getName());
            print("Ход " + attacker.getName() + " под " + getDefender().getName()
                    + ", козырь " + getDeck().getTrump());
            print(attacker);
            attackMove.move(getAttacker(), getTable());
            if(isPlayerWinner(getAttacker())) break gameloop;

            //defence move
            clearConsole(getDefender().getName());
            print(getTable());
            defenceMove.move(getDefender(), getTable());
            if (getDefender().getRole().equals("binder")) {
                setBinder(getDefender());
            } else {
                if(isPlayerWinner(getDefender())) break gameloop;
            }
            //throw move
            if (!isGameOver()) {
                for (Player thrower : getQueue()) {
                    throwloop:
                    while (moveValidator.isThrowPossible(getTable().getAll(), thrower.getPlayerHand()) && !getDefender().getPlayerHand().isEmpty()) {
                        clearConsole(thrower.getName());
                        print(getTable());
                        int numberOfUnbeatenCards = getTable().getUnbeatenCards().size();
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, getTable());
                        if (numberOfUnbeatenCards == getTable().getUnbeatenCards().size()) break throwloop;
                        if (!isGameOver() && !getTable().getUnbeatenCards().isEmpty()) {
                            if (!getDefender().getRole().equals("binder")) {
                                clearConsole(getDefender().getName());
                                print(getTable());
                                defenceMove.move(getDefender(), getTable());
                                print(getTable());
                            } else {
                                setBinder(getDefender());
                                break throwloop;
                            }
                        }
                    }
                    if(isPlayerWinner(thrower)) break gameloop;
                }
            }
            if (getBinder() != null) {
                for (Player thrower : getQueue()) {
                    if (moveValidator.isThrowPossible(getTable().getAll(), thrower.getPlayerHand())) {
                        clearConsole(thrower.getName());
                        print(getTable());
                        print(thrower.getName() + ", Вы можете подкинуть. Козырь " + getDeck().getTrump());
                        print(thrower);
                        throwMove.move(thrower, getTable());
                        if(isPlayerWinner(thrower)) break gameloop;
                    }
                }
                print(getBinder().getName() + " забирает карты " + table.getAll());
                getBinder().getPlayerHand().addAll(getTable().getAll());
            }
            table.clear();
            fillUpTheHands();
            changeTurn();
        }
        print(getWinner().getName() + " победитель!");
    }

    public void setBinder(Player binder) {
        this.binder = binder;
    }
}
