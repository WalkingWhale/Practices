import java.util.Arrays;

class Person1 implements Comparable{
    private String name;
    private int age;

    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ": " + age;
    }

    @Override
    public int compareTo(Object o) {
        Person1 p = (Person1)o;

        int nNum = this.name.compareTo(p.name);
        return nNum;

//        if(this.age > age){
//            return 1;
//        }
//        else if(this.age < age){
//            return -1;
//        }
//        else{
//            return 0;
//        }


    }
}

public class F7_ArrayObjSort {
    public static void main(String[] args) {
        Person1[] ar = new Person1[3];

        ar[0] = new Person1("홍길동", 29);
        ar[1] = new Person1("전우치", 15);
        ar[2] = new Person1("손오공", 37);

        Arrays.sort(ar);

        for(Person1 p : ar){
            System.out.println(p);
        }

    }
}
