class Apple5{
    @Override
    public String toString() {
        return "I am an apple.";
    }
}

class Orange5{
    @Override
    public String toString() {
        return "I am an Orange.";
    }
}

class Box5<T> {
    private T ob;

    public T get() {
        return ob;
    }

    public void set(T o) {
        this.ob = o;
    }
}

public class A5_FruitAndBox2_Generic {
    public static void main(String[] args) {
        // 과일 담는 박스 생성
        Box5<Apple5> aBox = new Box5<Apple5>();
        Box5<Orange5> oBox = new Box5<Orange5>();

        // 과일을 박스에 담는다.
        aBox.set(new Apple5());
        oBox.set(new Orange5());

        // 박스에서 과일을 꺼내는데 형 변환 하지 않는다.
        Apple5 ap = aBox.get();
        Orange5 og = oBox.get();

        System.out.println(ap);
        System.out.println(og);
    }
}
