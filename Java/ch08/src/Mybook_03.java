class Book_3{
    int price;
    String title;

    Book_3(String t, int p){
        title = t;
        price = p;
    }

    //복제 생성자
    Book_3(Book_3 copy){
        title = copy.title;
        price = copy.price;
    }

    void print(){
        System.out.println("제    목 : " + title);
        System.out.println("가    격 : " + price);
    }
}

public class Mybook_03 {
    public static void main(String[] args) {
        Book_3 book1 = new Book_3("자바 프로그래밍", 10000);
        book1.print();

        Book_3 book2 = new Book_3(book1);
        book2.title = "자바 디자인패턴";
        book2.print();
    }
}