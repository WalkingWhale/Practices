import java.util.*;

public class A8_ListIteratorCollection {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Toy","Box","Robot","Box");
        list = new ArrayList<>(list);

        ListIterator<String> litr = list.listIterator();
        String str;

        // 왼쪽 -> 오른쪽    0 -> N
       while(litr.hasNext()){
           str = litr.next();
           System.out.print(str + "\t");

           if(str.equals("Toy")){
               litr.add("Toy2");
           }
       }
        System.out.println();

        // 오른쪽 -> 왼쪽    N -> 0
        while(litr.hasPrevious()){
            str = litr.previous();
            System.out.print(str + "\t");

            if(str.equals("Robot")){
                litr.add("Robot2");
            }
        }
        System.out.println();

        // 왼족 -> 오른쪽    0 -> N
        for(Iterator<String> itr = list.iterator() ; itr.hasNext() ; ){
            System.out.print(itr.next() + "\t");
        }
    }
}
