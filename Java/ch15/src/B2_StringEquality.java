public class B2_StringEquality {
    public static void main(String[] args) {
        String str1 = new String("So Simple");
        String str2 = new String("So Simple");

        if(str1 == str2){
            System.out.println("str1, str2 참조 대상 동일하다");
        }
        else{
            System.out.println("str1, str2 참조 대상 다르다");
        }

        if(str1.equals(str2)){
            System.out.println("str1, str2 내용 동일하다");
        }
        else{
            System.out.println("str1, str2 내용 다르다");
        }

    }
}
