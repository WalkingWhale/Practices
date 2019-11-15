class SimpleBox{
    private int num;

    public SimpleBox(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num + 1;
    }
}

public class J_ThisUseEx {

    public static void main(String[] args) {
        SimpleBox box = new SimpleBox(5);
        box.setNum(10);
        System.out.println(box.getNum());
    }
}
