package controller;

import model.Card;
import model.Deck;
import model.Player;

import java.util.*;

/**
 * This class provides control over players' behavior during round*/
public class PlayerController {
    private Player attacker;
    private Player defender;
    private Player binder;
    private Deque<Player> queue;
    private boolean isGameOver = false;
    private Player winner;
    private List<Player> players;

    public PlayerController(String[] namesArr) {
        List<Player> players = new ArrayList<>();
        for (String s : namesArr) {
            players.add(new Player(s));
        }
        this.players = players;
    }

    public void setPlayersTurn() {
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
        queue = new LinkedList<>(players);
        setAttacker(queue.pop());
        setDefender(queue.pop());
        queue.addFirst(attacker);
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
        attacker = player;
    }

    public void setDefender(Player player) {
        player.setRole("defender");
        this.defender = player;
    }

    public Player getBinder() {
        return binder;
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

    boolean isPlayerWinner(Player player, Deck deck) {
        boolean isWinner = deck.isEmpty() && player.getPlayerHand().isEmpty();
        if (isWinner) {
            player.setWinner(true);
            setGameOver(true);
            setWinner(player);
        }
        return isWinner;
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

    public void setBinder(Player binder) {
        this.binder = binder;
    }
}
