class BoxE<T>{
    private T ob;

    // 한 입 먹고 반환하는 행위의 메소드로 수정
    public T get() {
        return ob;
    }

    public void set(T ob) {
        this.ob = ob;
    }
}

class BoxFactoryE{
    public static <T extends Number> BoxE<T> mkaeBox(T o){
        BoxE<T> box = new BoxE<T>();
        box.set(o);

        System.out.println("Boxed data: " + o.intValue());
        return box;
    }

//    public static BoxC<String> makeBox2(String o){
//        BoxC<String> box = new BoxC<String>();
//        box.set(o);
//        return box;
//    }
//
//    public static BoxC makeBox2(String o){
//        BoxC box = new BoxC();
//        box.set(o);
//        return box;
//    }

}

class UnboxerE{
    public static <T extends Number> T openBox(BoxE<T> box) {
        System.out.println("Unboxed data: " + box.get().intValue());
        return box.get();
    }
}

public class C3_BoundedGenericMethod {
    public static void main(String[] args) {
        BoxE<Integer> sbox = BoxFactoryE.mkaeBox(new Integer((5959)));

        int n = UnboxerE.openBox(sbox);
        System.out.println("Returned data: " + n);
    }
}
