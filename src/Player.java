import java.util.List;

public class Player implements Comparable<Player>{
    static int id = 0;
    private String name;
    private List<Card> playerHand;
    private boolean isWinner = false;
     private int turn;
     private int playerID;
     private Integer minTrumpWeight;


    public Player(String name, List<Card> playerHand) {
        this.name = name;
        this.playerHand = playerHand;
        this.playerID = id++;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return name + " " + playerHand;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public int getPlayerId() {
        return this.playerID;
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


}
