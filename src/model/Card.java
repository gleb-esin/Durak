package model;

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
        if (suit.getSuit().equals("\u2665") || suit.getSuit().equals("\u2666")) {
            return "\u001B[38;2;128;0;0;47m" + value + suit + "\u001B[0m";
        } else {
            return "\u001B[30;47m" + value + suit + "\u001B[0m";
        }
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        Card card = (Card) o;
        if (!suit.equals(card.suit)) return false;
        return value.equals(card.value);
    }

    public Value getValue() {
        return value;
    }

    @Override
    public int compareTo(Card o) {
        int suitComparison = suit.compareTo(o.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }
        return value.compareTo(o.value);
    }


    public class Suit implements Comparable<Suit> {
        private String suit;
        private boolean isTrump = false;


        public Suit(SuitEnum suitEnum) {
            switch (suitEnum) {
                case SPADES -> suit = "♠";
                case CLUBS -> suit = "♣";
                case HEARTS -> suit = "♥";
                case DIAMONDS -> suit = "♦";
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
            Suit suit1 = (Suit) o;
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
            if (suit.equals(o.suit)) {
                return 0;
            } else {
                if (isTrump == true && o.isTrump == false) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }


    public class Value implements Comparable<Value> {
        private String value;
        private String[] valuesArr = {"6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        private int weight;

        public Value(String value) {
            this.value = value;
            weight = Arrays.asList(valuesArr).indexOf(value);
        }

        @Override
        public boolean equals(Object o) {
            Value value = (Value) o;
            return weight == value.weight;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public int compareTo(Value o) {
            if (weight == o.weight) {
                return 0;
            } else if (weight > o.weight) {
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



