public class C3_NullPointer {
    public static void main(String[] args) {
        String str = null;
        System.out.println(str);
        int len = str.length();

        if(str != null){
            len = str.length();
        }
    }
}
