import java.util.ArrayList;
import java.util.List;

public class Table {
    private Deck deck;
    private List<Card> table;
    private Player attacker;
    private Player defender;
    private List<Player> throwers = new ArrayList<>();

    public Table(Deck deck, List<Player> players) {
        this.deck = deck;
        this.attacker = players.get(0);
        this.defender = players.get(1);
        for (int i = 2; i < players.size(); i++) {
            this.throwers.add(players.get(i));
        }
        this.throwers = throwers;
        this.table = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Table ");
        for (int i = 0; i < this.table.size(); i++) {
            if (table.get(i) == null) {
                stringBuilder.append(" " + " ");
            } else {
                stringBuilder.append(table.get(i).toString() + " ");
            }
        }
        return stringBuilder.toString();
    }

    public List<Card> getTable() {
        return table;
    }

    public void setTable(List<Card> table) {
        this.table = table;
    }

    public Player getAttacker() {
        return attacker;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public void setDefender(Player defender) {
        this.defender = defender;
    }

    public List<Player> getThrowers() {
        return throwers;
    }

    public void setThrowers(List<Player> throwers) {
        this.throwers = throwers;
    }

    public Deck getDeck() {
        return deck;
    }
}
