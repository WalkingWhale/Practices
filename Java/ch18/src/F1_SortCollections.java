import java.util.*;

public class F1_SortCollections {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Toy","Box","Robot","Weapon");
        list = new ArrayList<>(list);

        // 정렬 이전 출력
        for(String s : list){
            System.out.print(s.toString() + "\t");
        }
        System.out.println();

        Collections.sort(list);

        for(Iterator<String> itr = list.iterator() ; itr.hasNext(); ){
            System.out.print(itr.next() + '\t');
        }
    }
}
