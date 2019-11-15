class Book_2{
    int price;
    int num = 0;
    String title;

    Book_2(){
        title = "자바 프로그래밍";
        price = 5000;
    }

    Book_2(String t, int p){
        title = t;
        price = p;
    }

    void print(){
        System.out.println("제    목 : " + title);
        System.out.println("가    격 : " + price);
        System.out.println("주문수량 : " + num);
        System.out.println("합계금액 : " + price * num);

    }
}

public class Mybook_02 {
    public static void main(String[] args) {
        Book_2 book1 = new Book_2("자바 디자인패턴", 10000);
        book1.num = 10;
        book1.print();
    }
}
