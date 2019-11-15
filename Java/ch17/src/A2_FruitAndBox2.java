class Apple2{
    @Override
    public String toString() {
        return "I am an apple.";
    }
}

class Orange2{
    @Override
    public String toString() {
        return "I am an Orange.";
    }
}

class Box2 {
    private Object ob;

    public Object get() {
        return ob;
    }

    public void set(Object o) {
        this.ob = o;
    }
}

public class A2_FruitAndBox2 {
    public static void main(String[] args) {
        // 과일 담는 박스 생성
        Box2 aBox = new Box2();
        Box2 oBox = new Box2();

        // 과일을 박스에 담는다.                 // 어쩔 수 없이 형 변환의
        aBox.set(new Apple2());                 // 과정이 수반된다.
        oBox.set(new Orange2());                // 그리고 이는 컴파일러의
                                                // 오류 발견 가능성을
        // 박스에서 과일을 꺼낸다.               // 낮추는 결과로 이어진다.
        Apple2 ap = (Apple2)aBox.get();
        Orange2 og = (Orange2)oBox.get();

        System.out.println(ap);
        System.out.println(og);
    }
}
