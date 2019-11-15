import java.util.Set;
import java.util.TreeSet;

class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.age;
    }

    @Override
    public int compareTo(Person p) {
        // 검색 조건을 필요에 따라 자유롭게 변경할 수 없어 불편하다.
        // 조건 변경시 제 컴파일 필요!!!
        // return this.age - p.age
        // return p.age - this.age;
        // return this.name.compateTo(p.name);
        return p.age - this.age;
    }
}
public class C2_ComparablePerson {
    public static void main(String[] args) {
        Set<Person> tree = new TreeSet<>();
        tree.add(new Person("SON", 37));
        tree.add(new Person("HONG", 53));
        tree.add(new Person("JEON", 22));

        for(Person p : tree){
            System.out.println(p);
        }
    }
}
