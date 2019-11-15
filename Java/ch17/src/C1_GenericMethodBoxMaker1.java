class BoxC<T>{
    private T ob;

    // 한 입 먹고 반환하는 행위의 메소드로 수정
    public T get() {
        return ob;
    }

    public void set(T ob) {
        this.ob = ob;
    }
}

class BoxFactoryC{
    public static <T> BoxC<T> mkaeBox(T o){
        BoxC<T> box = new BoxC<>();
        box.set(o);
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


public class C1_GenericMethodBoxMaker1 {
    public static void main(String[] args) {
        BoxC<String > sBox = BoxFactoryC.<String>mkaeBox("Sweet");
        System.out.println(sBox.get());

        BoxC<Double> dBox = BoxFactoryC.<Double>mkaeBox(7.59);
        System.out.println(dBox.get());
    }
}
