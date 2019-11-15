public class UsePrinter {
    public static void main(String[] args) {
        Papers1 p = new Papers1("서류 내용: 행복합니다");
        Printable1 prn = p.getPrinter();
        prn.print();
    }
}
