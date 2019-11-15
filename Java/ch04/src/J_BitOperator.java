public class J_BitOperator {

    public static void main(String[] args) {
        byte n1 = 5;        // 00000101
        byte n2 = 3;        // 00000011
        byte n3 = -1;       // 11111111

        byte r1 = (byte)(n1 & n2);  // AND 연산
        byte r2 = (byte)(n1 | n2);  // OR 연산
        byte r3 = (byte)(n1 ^ n2);  // XOR 연산 - 기본적으로 OR 연산 But 두개의 비트가 같으면 0
        byte r4 = (byte)(~n3);      // not연산 - 부호 변경 - 0은 1, 1은 0

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
    }
}
