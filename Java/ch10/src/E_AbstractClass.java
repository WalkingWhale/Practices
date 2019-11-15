abstract class Abstract_Animal{
    int age;
    abstract void cry();
}

class Abstract_Dog extends Abstract_Animal{
    void cry(){
        System.out.println("멍멍");
    }
}

class Abstract_Cat extends Abstract_Animal{
    void cry(){
        System.out.println("야옹");
    }
}


public class E_AbstractClass {
    public static void main(String[] args) {
        Abstract_Dog dog = new Abstract_Dog();
        dog.cry();

        Abstract_Cat cat = new Abstract_Cat();
        cat.cry();
    }
}
