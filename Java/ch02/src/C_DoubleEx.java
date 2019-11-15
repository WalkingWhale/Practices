public class C_DoubleEx {

    public static void main(String[] args) {

        double dnum = 1;
        int i=0;

        for ( ; i<10000; i++){    //dnum을 0.1씩 10000번 증가
            dnum = dnum + 0.1;
        }

        /*while(i<10000){
            dnum = dnum + 0.1;
            i++;
        }*/

        /*do{
            dnum = dnum + 0.1;
            i++;
        }while(i<9999);*/

        System.out.println(dnum);       //dnum 출력
    }
}
