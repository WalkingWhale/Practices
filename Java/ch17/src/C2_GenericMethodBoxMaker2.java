class BoxD<T>{
    private T ob;

    // 한 입 먹고 반환하는 행위의 메소드로 수정
    public T get() {
        return ob;
    }

    public void set(T ob) {
        this.ob = ob;
    }
}

class UnboxerD{
    public static <T> T openBox(BoxD<T> box) {
        return box.get();
    }

//   public static String T openBox(BoxD<String> box) {
//        return box.get();
//    }
//
//    public static String T openBox(BoxD> box) {
//        return box.get();
//    }

}


public class C2_GenericMethodBoxMaker2 {
    public static void main(String[] args) {
        BoxD<String > box = new BoxD<>();
        box.set("My Generic Method");

        String str = UnboxerD.<String>openBox(box);
        System.out.println(str);
    }
}
