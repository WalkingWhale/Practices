/*
*   정수를 입력받아 약수를 구하기
* */

import java.util.Scanner;


public class Prac_HS {
    static final int ARR_SIZE = 100;

    public static void main(String[] args) {

        print();

    }

    public static int[] get_ali(){

        int[] ali = new int[ARR_SIZE];
        int count = 0;

        int num = get_num();

        for(int i = 1 ; i < num + 1 ; i++ )
        {
            if((num % i)==0){
                ali[count] = i;
                count++;
            }
        }

        return ali;
    }

    public static int get_num(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Please Enter your number: ");
        int num = sc.nextInt();
        System.out.println();

        System.out.println("Your number is " + num);
        System.out.println();

        sc.close();

        return num;
    }

    public static void print(){
        int[] arr = new int[ARR_SIZE];

        arr = get_ali();

        System.out.println("Your Aliquots are");
        for(int i = 0 ; i < arr.length ; i++){
            if(arr[i] != 0){
                System.out.print(arr[i] + " ");
            }

            else{
                break;
            }
        }
    }
}
