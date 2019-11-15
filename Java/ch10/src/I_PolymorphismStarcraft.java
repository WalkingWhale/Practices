import java.util.Random;

abstract class Unit{
    int HP;
    int MP;

    abstract void move();
    abstract void attack();
}

class Marine extends Unit{

    Marine(){
        System.out.println("마린이 마린답게 생성됨");
    }
    @Override
    void move() {
        System.out.println("한 칸 움직이기"); // Unit의 공통 기능
        System.out.println("두 발로 걷기");   // 마린답게 꾸민 것
    }

    @Override
    void attack() {
        System.out.println("상대 HP 1 감소 시키기"); // Unit의 공통 기능
        System.out.println("총으로 쏘기");          // 마린답게 꾸민 것
    }
}

class Zergling extends Unit{

    Zergling(){
        System.out.println("저글링 생성됨");
    }
    @Override
    void move() {
        System.out.println("한 칸 움직이기"); // Unit의 공통 기능
        System.out.println("네 발로 걷기");   // 저글링답게 꾸민 것
    }

    @Override
    void attack() {
        System.out.println("상대 HP 1 감소 시키기"); // Unit의 공통 기능
        System.out.println("앞 발로 찍기");          // 저글링답게 꾸민 것
    }
}

public class I_PolymorphismStarcraft {

    static Unit UnitCreate(int kind){
        if(kind == 1)
        {
            return new Marine();
        }

        else{
            return new Zergling();
        }
    }

    public static void main(String[] args) {
        Random ran = new Random();

        // 공통 특성만을 이용해서 코딩한다.

        // 자식을 부모로 대입받기 : 다형성(폴리모피즘)
        // 만들때는 마린답게 만들고 사용은 유닛의 공통 특성으로 사용

        Unit unit1 = UnitCreate(ran.nextInt(2));
        unit1.move();
        unit1.attack();

        Unit unit2 = UnitCreate(ran.nextInt(2));
        unit2.move();
        unit2.attack();

    }
}
