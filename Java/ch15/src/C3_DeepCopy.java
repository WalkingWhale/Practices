class Point3 implements Cloneable {
    private int xPos;
    private int yPos;

    public Point3(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void showPosition(){
        System.out.printf("[ %d, %d ]", xPos, yPos);
        System.out.println();
    }

    public void changePos(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Rectangle3 implements Cloneable{
    public Point3 upperLeft;        // 좌측 상단 좌표
    public Point3 lowerRight;       // 우측 상단 좌표

    public Rectangle3(int x1, int y1, int x2, int y2) {
        this.upperLeft = new Point3(x1, y1);
        this.lowerRight = new Point3(x2, y2);
    }

    // 좌표 정보를 수정함
    public void changePos(int x1, int y1, int x2, int y2){
        this.upperLeft.changePos(x1,y1);
        this.lowerRight.changePos(x2,y2);
    }

    public void showPosition(){
        System.out.print("좌측 상단: ");
        this.upperLeft.showPosition();

        System.out.print("우측 하단: ");
        this.lowerRight.showPosition();
        System.out.println();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Rectangle3 copy = (Rectangle3)super.clone();
        copy.upperLeft = (Point3)upperLeft.clone();
        copy.lowerRight = (Point3)lowerRight.clone();

        return copy;
    }
}

public class C3_DeepCopy {
    public static void main(String[] args) {
        Rectangle3 org = new Rectangle3(1,1,9,9);
        Rectangle3 cpy;

        try {
            // 인스턴스 복사
            cpy = (Rectangle3)org.clone();

            // 한 인스턴스의 좌표 정보를 수정
            org.changePos(2,2, 7,7);

            org.showPosition();
            cpy.showPosition();


        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}
