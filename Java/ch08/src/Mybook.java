class Book{

    String title;
    String author;
    int money;

}

public class Mybook {

    public static void main(String[] args) {
        Book book1 = new Book();

        book1.title = "자바 프로그래밍";
        book1.author = "홍길동";
        book1.money = 15000;

        System.out.println( book1.title + " : " +
                            book1.author + " : " +
                            book1.money);
    }
}
