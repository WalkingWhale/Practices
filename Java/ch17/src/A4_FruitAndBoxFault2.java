class Apple4{
    @Override
    public String toString() {
        return "I am an apple.";
    }
}

class Orange4{
    @Override
    public String toString() {
        return "I am an Orange.";
    }
}

class Box4 {
    private Object ob;

    public Object get() {
        return ob;
    }

    public void set(Object o) {
        this.ob = o;
    }
}

public class A4_FruitAndBoxFault2 {
    public static void main(String[] args) {
        // 과일 담는 박스 생성
        Box4 aBox = new Box4();             // 프로그래머의 실수가 컴파일
        Box4 oBox = new Box4();             // 과정에서 발견되지 않는다.

        // 과일을 박스에 담은 것일까?.
        aBox.set("Apple");                  // 프로그래머의 실수가 실행과정
        oBox.set("Orange");                 // 에서도 발견되지 않을 수 있다.
                                            // 정말 큰 문제!!
        System.out.println(aBox.get());
        System.out.println(oBox.get());
    }
}
/**
 * 불편함 = 상자에서 물건을 꺼낼 때 형 변환을 해야 한다는것.
 * 문제점 = 프로그래머가 실수를 해도 그 실수가 드러나지 않을 수 있다는점
 */
