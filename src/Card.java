import java.util.Arrays;

public class Card implements Comparable<Card> {
    private Suit suit;
    private Value value;

    public Card(String value, SuitEnum suitEnum) {
        this.value = new Value(value);
        this.suit = new Suit(suitEnum);
    }

    @Override
    public String toString() {
        if (this.suit.getSuit().equals("\u2665") || this.suit.getSuit().equals("\u2666")) {
            return "\u001B[38;2;128;0;0;47m" + this.value + this.suit + "\u001B[0m";
        } else {
            return "\u001B[30;47m" + this.value + this.suit + "\u001B[0m";
        }
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!suit.equals(card.suit)) return false;
        return value.equals(card.value);
    }

    public Value getValue() {
        return value;
    }

    @Override
    public int compareTo(Card o) {
        int suitComparison = this.suit.compareTo(o.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }
        return this.value.compareTo(o.value);
    }


    class Suit implements Comparable<Suit> {
        private String suit;
        private boolean isTrump = false;


        public Suit(SuitEnum suitEnum) {
            switch (suitEnum) {
                case SPADES -> this.suit = "♠";
                case CLUBS -> this.suit = "♣";
                case HEARTS -> this.suit = "♥";
                case DIAMONDS -> this.suit = "♦";
            }
        }

        @Override
        public String toString() {
            return suit;
        }

        public void setTrump(boolean trump) {
            isTrump = trump;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Suit suit1 = (Suit) o;

            if (isTrump != suit1.isTrump) return false;
            return suit.equals(suit1.suit);
        }

        public String getSuit() {
            return suit;
        }

        public boolean getTrump() {
            return isTrump;
        }

        @Override
        public int compareTo(Suit o) {
            if ((this.suit.equals(o.suit)) && this.isTrump == o.isTrump) {
                return 0;
            } else if (this.isTrump == true && o.isTrump == false) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    class Value implements Comparable<Value> {
        private String value;
        private String[] valuesArr = {"6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        private int weight;

        public Value(String value) {
            this.value = value;
            this.weight = Arrays.asList(valuesArr).indexOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Value value = (Value) o;

            return weight == value.weight;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public int compareTo(Value o) {
            if (this.weight == o.weight) {
                return 0;
            } else if (this.weight > o.weight) {
                return 1;
            } else return -1;
        }

        public int getWeight() {
            return weight;
        }
    }

}

enum SuitEnum {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS
}



