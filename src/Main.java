public class Main {
    public static void main(String[] args) {

        Card card = new Card("K", SuitEnum.HEARTS);
        Card card2 = new Card("K",SuitEnum.CLUBS);

        System.out.println(card.equals(card2));

        System.out.println(card);
        System.out.println(card2);
        String heart = "\u2665";
        String redHeart = "\u001B[31m" + heart + "\u001B[0m";  // ANSI-код для красного цвета и сброса цвета
        System.out.println(redHeart);

    }
}