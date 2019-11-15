import java.util.Comparator;
import java.util.TreeSet;

class StringComparator implements Comparator<String>{
    public int compare(String s1, String s2){
        return s1.length() - s2.length();
    }
}   // String Class의 정렬 기준은 사전 편찬순이 기본.
    // 해당 클래스는 이름 길이 순으로 정렬 기준을 바꾸는 Class

public class C4_ComparatorString {
    public static void main(String[] args) {
        TreeSet<String> tree = new TreeSet<>(new StringComparator());
        tree.add("Box");
        tree.add("Rabbit");
        tree.add("Robot1");
        tree.add("Robot");


        for(String s : tree){
            System.out.println(s);
        }
    }
}
