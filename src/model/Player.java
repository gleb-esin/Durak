package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Comparable<Player> {
    static int id = 0;
    private String name;
    private List<Card> playerHand = new ArrayList<>();
    private boolean isWinner = false;
    private int turn;
    private int playerID;
    private String role;
    private Integer minTrumpWeight;


    public Player(String name) {
        this.name = name;
        this.playerID = id++;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        Collections.sort(playerHand);
        StringBuffer upperString = new StringBuffer();
        StringBuffer bottomString = new StringBuffer();
        upperString.append(this.name + " ");

        for (int i = 0; i < this.getName().length(); i++) {
            bottomString.append(" ");
        }
        int cardNumber = 1;
        for (Card c : playerHand) {
            for (int i = 0; i < 2; i++) {
                bottomString.append(" ");
            }
            upperString.append(c + " ");
            if (c.getValue().toString().equals("10")) bottomString.append(" ");
            bottomString.append(cardNumber++);
        }
        return upperString + "\n" + bottomString;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public Integer getMinTrumpWeight() {
        return minTrumpWeight;
    }

    public void setMinTrumpWeight(int minTrumpWeight) {
        this.minTrumpWeight = minTrumpWeight;
    }

    @Override
    public int compareTo(Player o) {
        if (this.minTrumpWeight == o.minTrumpWeight) return 0;
        else if (this.minTrumpWeight > o.minTrumpWeight) return 1;
        else return -1;
    }

    public String getName() {
        return name;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }
}
