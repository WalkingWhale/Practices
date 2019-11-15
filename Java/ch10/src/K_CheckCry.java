interface Cry{
    void cry();
}

class Cat_cry implements Cry{
    @Override
    public void cry() {
        System.out.println("야옹~");
    }
}

class Dog_cry implements Cry{
    @Override
    public void cry() {
        System.out.println("멍멍!");
    }
}

public class K_CheckCry {
    public static void main(String[] args) {
        //Cry test1 = new Cat_cry();
        Cry test1 = new Dog_cry();


        if(test1 instanceof Cat_cry){
            test1.cry();
        }

        else if(test1 instanceof Dog_cry){
            System.out.println("고양이가 아닙니다");
        }
    }

}
