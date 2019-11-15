import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class Person2 implements Comparable<Person2>{
    String name;
    int age;

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.age;
    }

    @Override
    public int compareTo(Person2 p) {
        // 검색 조건을 필요에 따라 자유롭게 변경할 수 없어 불편하다.
        // 조건 변경시 제 컴파일 필요!!!
        // return this.age - p.age;     <- ASC
        // return p.age - this.age;     <- DESC
        // return this.name.compateTo(p.name);
        // 나이가 동일한 데이터는 추가되지 않는다.
        return p.age - this.age;
    }
}

class PersonComparator implements Comparator<Person2>{
    public int compare(Person2 p1, Person2 p2){
        return p2.age - p1.age;
    }
}

public class C3_ComparaotrPerson {
    public static void main(String[] args) {
        Set<Person2> tree = new TreeSet<>(new PersonComparator());
        tree.add(new Person2("SON", 37));
        tree.add(new Person2("HONG", 53));
        tree.add(new Person2("홍길동",22));
        tree.add(new Person2("JEON", 22));

        for(Person2 p : tree){
            System.out.println(p);
        }
    }
}
