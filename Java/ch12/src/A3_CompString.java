public class A3_CompString {
    public static void main(String[] args) {
        String str1 = "A";  //Apple, Apple
        String str2 = "B";  //apple, Banna
        int cmp;

        if(str1.equals(str2)){
            System.out.println("두 문자열은 같습니다");
        }
        else{
            System.out.println("두 문자열은 다릅니다");
        }

        cmp = str1.compareTo(str2);

        if(cmp == 0){
            System.out.println("두 문자열은 일치합니다");
        }
        else if(cmp < 0){
            System.out.println("사전의 앞에 위치하는 문자: " + str1);
        }
        else{
            System.out.println("사전의 앞에 위치하는 문자: " + str2);
        }

        if (str1.compareToIgnoreCase(str2) == 0){
            System.out.println("두 문자열은 같습니다");
        }
        else{
            System.out.println("두 문자열은 다릅니다");
        }
    }
}
