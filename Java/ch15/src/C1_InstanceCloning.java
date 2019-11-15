class Point1 implements Cloneable{
    private int xPos;
    private int yPos;

    public Point1(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void showPosition(){
        System.out.printf("[ %d, %d ]", xPos, yPos);
        System.out.println();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

public class C1_InstanceCloning {
    public static void main(String[] args) {
        Point1 org = new Point1(3,5);
        Point1 cpy;

        try {
            cpy = (Point1)org.clone();
            org.showPosition();
            cpy.showPosition();

            if (org.equals(cpy)){
                System.out.println("aaaaaaaaa");
            }
            else{
                System.out.println("bbbbbbbbb");
            }
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }
}
