import java.util.*;

public class A6_ConversionCollection {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Toy","Box","Robot","Box");
        list = new ArrayList<>(list);

        // ArrayList<E> 인스턴스의 순환
        for(Iterator<String> itr = list.iterator(); itr.hasNext() ; ){
            System.out.print(itr.next() + '\t');
        }
        System.out.println();

        // ArrayList<E>를 LinkedList<E>로 변환
        list = new LinkedList<>(list);

        // LinkedList<E> 인스턴스의 순환
        for(Iterator<String> itr = list.iterator() ; itr.hasNext() ; ){
            System.out.print(itr.next() + "\t");
        }
        System.out.println();
    }
}
