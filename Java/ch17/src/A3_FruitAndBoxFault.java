class Apple3{
    @Override
    public String toString() {
        return "I am an apple.";
    }
}

class Orange3{
    @Override
    public String toString() {
        return "I am an Orange.";
    }
}

class Box3 {
    private Object ob;

    public Object get() {
        return ob;
    }

    public void set(Object o) {
        this.ob = o;
    }
}

public class A3_FruitAndBoxFault {
    public static void main(String[] args) {
        // 과일 담는 박스 생성
        Box3 aBox = new Box3();             // 프로그래머의 실수가 컴파일
        Box3 oBox = new Box3();             // 과정에서 발견되지 않는다.

        // 과일을 박스에 담는다.
        aBox.set("Apple");                  // 사과와 오렌지가 아닌
        oBox.set("Orange");                 // '문자열'을 담았다.

        // 박스에서 과일을 꺼낸다.
        Apple3 ap = (Apple3)aBox.get();
        Orange3 og = (Orange3)oBox.get();

        System.out.println(ap);
        System.out.println(og);
    }
}
