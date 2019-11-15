import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StrComp implements Comparator<String>{
    @Override
    public int compare(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }
}

public class F3_StringComparator {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("Box");
        list.add("Robot");
        list.add("Apple");

        // 정렬과 탐색의 기준 생성
        StrComp cmp = new StrComp();

        // 정렬
        Collections.sort(list, cmp);

        // 탐색
        int idx = Collections.binarySearch(list, "Robot", cmp);
        //int idx = Collections.binarySearch(list, "R");
        //int idx2 = Collections.binarySearch(list, "S");

        // 탐색 결과 출력
        System.out.println(list.get(idx));

        /*for(int i = idx ; i < idx2; i++){
            나중에 이런식으로 씀
        }*/
    }
}
