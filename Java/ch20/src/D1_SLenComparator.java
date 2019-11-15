import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SLenComp implements Comparator<String>{
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
}

public class D1_SLenComparator {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Robot");
        list.add("Lambda");
        list.add("Box");

        Collections.sort(list, new SLenComp());

        for(String s : list){
            System.out.println(s);
        }

    }
}
