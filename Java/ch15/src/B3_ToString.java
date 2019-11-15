class Book_B3 {
    int bookNumber;
    String bookTitle;

    public Book_B3(int bookNumber, String bookTitle) {
        this.bookNumber = bookNumber;
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        return bookTitle + ", " + bookNumber;
    }
}
public class B3_ToString {

    public static void main(String[] args) {
        Book_B3 book1 = new Book_B3(200,"자바의 기초");

        System.out.println(book1);
        System.out.println(book1.toString());
    }
}
