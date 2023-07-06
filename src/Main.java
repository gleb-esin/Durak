public class Main {
    public static void main(String[] args) {

        Card card = new Card("K", SuitEnum.HEARTS);
        Card card2 = new Card("K",SuitEnum.DIAMONDS);

        System.out.println(card.equals(card2));

        System.out.println(card);
    }
}