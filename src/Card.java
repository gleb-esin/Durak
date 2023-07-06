import java.util.Arrays;

public class Card {
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

    class Suit {
        private String suit;
        private boolean isTrump = false;


        public Suit(SuitEnum suitEnum) {
            switch (suitEnum) {
                case SPADES -> this.suit = "\u2660";
                case CLUBS -> this.suit = "\u2663";
                case HEARTS -> this.suit = "\u2665";
                case DIAMONDS -> this.suit = "\u2666";
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
    }

    class Value {
        String value;
        String[] valuesArr = {"6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int weigth;

        public Value(String value) {
            this.value = value;
            this.weigth = Arrays.asList(valuesArr).indexOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Value value = (Value) o;

            return weigth == value.weigth;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}

enum SuitEnum {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS
}


