class Apple6{
    @Override
    public String toString() {
        return "I am an apple.";
    }
}

class Orange6{
    @Override
    public String toString() {
        return "I am an Orange.";
    }
}

class Box6<T> {
    private T ob;

    public T get() {
        return ob;
    }

    public void set(T o) {
        this.ob = o;
    }
}

public class A6_FruitAndBoxFault_Generic {
    public static void main(String[] args) {
        // 과일 담는 박스 생성
        Box6<Apple6> aBox = new Box6<Apple6>();
        Box6<Orange6> oBox = new Box6<Orange6>();

        // 과일을 박스에 담은 것일까?
//        aBox.set("Apple");              // <- 프로그래머의 실수
//        oBox.set("Orange5");

        // 과일을 박스에 담는다.
        aBox.set(new Apple6());
        oBox.set(new Orange6());

        // 박스에서 과일을 제대로 꺼낼 수 있을까?
        Apple6 ap = aBox.get();
        Orange6 og = oBox.get();

        System.out.println(ap);
        System.out.println(og);
    }
}
