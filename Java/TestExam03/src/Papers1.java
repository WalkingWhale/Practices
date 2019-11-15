public class Papers1 {
    private String con;

    public Papers1(String s){
        con = s;
    }

    public Printable1 getPrinter(){
        return new Printer1();
    }

    private class Printer1 implements Printable1{
        public void print(){
            System.out.println(con);
        }
    }
}
