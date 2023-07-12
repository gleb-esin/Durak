import java.util.*;

public class Deck implements Iterator<Card> {
    private List<Card> deck;
    private int iteratorIndex = 0;
    private Card.Suit trump;


    public Deck() {
        SuitEnum[] suitArr = {SuitEnum.SPADES, SuitEnum.DIAMONDS, SuitEnum.CLUBS, SuitEnum.HEARTS};
        String[] valuesArr = {"6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        List<Card> deck = new ArrayList<>();
        for (SuitEnum suitEnum : suitArr) {
            for (String value : valuesArr) {
                deck.add(new Card(value, suitEnum));
            }
        }
        Collections.shuffle(deck);

        this.trump = deck.get((int) (Math.random() * 36)).getSuit();
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            Card.Suit suit = card.getSuit();
            if (suit.equals(this.trump)){
                card.getSuit().setTrump(true);
            }

        }
        this.deck = deck;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Card card : this.deck) {
            stringBuffer.append(card + " ");
        }
        return stringBuffer.toString();
    }

    public Card getNextCard() {
        Card card = this.deck.get(0);
        this.deck.remove(0);
        return card;
    }

    public Card.Suit getTrump() {
        return this.trump;
    }

    public boolean hasNext() {
        return iteratorIndex < deck.size();
    }

    @Override
    public Card next() {
        Card nextCard = deck.get(iteratorIndex);
        iteratorIndex++;
        return nextCard;
    }
}
