class INum_01{
    private int num;

    @Override
    public boolean equals(Object obj) {
        if(this.num == ((INum_01)obj).num){
            return true;
        }
        else{
            return false;
        }
    }

    public INum_01(int num) {
        this.num = num;
    }
}

public class B1_ObjectEquality {
    public static void main(String[] args) {
        INum_01 num1 = new INum_01(10);
        INum_01 num2 = new INum_01(12);
        INum_01 num3 = new INum_01(10);

        if(num1.equals(num2)){
            System.out.println("num1, num2 내용 동일하다");
        }
        else{
            System.out.println("num1, num2 내용 다르다");
        }

        if(num1.equals(num3)){
            System.out.println("num1, num3 내용 동일하다");
        }
        else{
            System.out.println("num1, num3 내용 다르다");
        }
    }
}
