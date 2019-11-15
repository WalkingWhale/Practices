public class B3_IndexOf {
    public static void main(String[] args) {
        String str1 = "AppleBananaOragne";
        int num1 = str1.indexOf("B");
        int num2 = str1.indexOf("O");

        String str2 = str1.substring(num1, num2);
        System.out.println(str2);

    }
}
