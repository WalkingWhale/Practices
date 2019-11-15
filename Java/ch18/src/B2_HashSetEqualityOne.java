import java.util.HashSet;
import java.util.Set;

class Num1{
    private int num;

    public Num1(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }
}

public class B2_HashSetEqualityOne {
    public static void main(String[] args) {
        Set<Num1> set = new HashSet<>();
        set.add(new Num1(7799));
        set.add(new Num1(9955));
        set.add(new Num1(7799));


        System.out.println("인스턴스 수: "+ set.size());


        // for-each를 이용한 전체 출력
        for(Num1 n : set){
            System.out.print(n.toString() + '\t');
        }
    }
}
