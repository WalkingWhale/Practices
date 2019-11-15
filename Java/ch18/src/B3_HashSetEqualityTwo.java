import java.util.HashSet;
import java.util.Set;

class Num2{
    private int num;

    public Num2(int num) {
        this.num = num;
    }

    @Override
    public int hashCode() {
        return num%3;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public boolean equals(Object obj) {
        if(num == ((Num2)obj).num){
            return true;
        }
        else{
            return false;
        }
    }
}

public class B3_HashSetEqualityTwo {
    public static void main(String[] args) {
        Set<Num2> set = new HashSet<>();
        set.add(new Num2(7799));
        set.add(new Num2(9955));
        set.add(new Num2(7799));


        System.out.println("인스턴스 수: "+ set.size());


        // for-each를 이용한 전체 출력
        for(Num2 n : set){
            System.out.print(n.toString() + '\t');
        }
    }
}
