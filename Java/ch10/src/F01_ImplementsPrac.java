interface Greet_org{
    void greet();
}

interface Talk{
    void talk();
}

class Morning implements Greet_org, Talk{

    public void greet(){
        System.out.println("안녕하세요!");
    }

    public void talk(){
        System.out.println("날씨 좋네요.");
    }
}

public class F01_ImplementsPrac {

    public static void main(String[] args) {
        Morning morning = new Morning();
        morning.greet();
        morning.talk();
    }

}
