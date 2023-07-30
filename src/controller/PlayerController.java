package controller;

import model.Deck;
import model.Player;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PlayerController {
    private Player attacker;
    private Player defender;
    private Player binder;
    private Deque<Player> queue;
    private boolean isGameOver = false;
    private Player winner;

    public PlayerController(List<Player> players) {
        this.queue = new LinkedList<>(players);
        setAttacker(this.queue.pop());
        setDefender(this.queue.pop());
        this.queue.addFirst(this.attacker);
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