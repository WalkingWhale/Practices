import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class C5_ConvertCollection {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Toy","Box","Box","Toy");
        ArrayList<String> list1 = new ArrayList<>(list);

        for(String s : list1){
            System.out.print(s.toString() +  '\t');
        }
        System.out.println();

        HashSet<String> set = new HashSet<>(list);
        list1 = new ArrayList<>(set);

        for(String s : list1){
            System.out.print(s.toString() +  '\t');
        }
        System.out.println();
    }
}
