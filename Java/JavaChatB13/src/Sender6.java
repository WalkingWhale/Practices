import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Sender6 extends Thread{
    Socket socket;
    PrintWriter out = null;
    String name;

    // 생성자
    public Sender6(Socket socket, String name) {
        this.socket = socket;

        try{
            out = new PrintWriter(this.socket.getOutputStream(), true);
            this.name = name;
        } catch(Exception e){   // try  {}
            System.out.println("예외S3 : " + e);
        }   // catch {} // 예외 s3
    }   // Sender5 {}

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        try{
            out.println(name);

            while(out != null){
                try{
                    String s2 = sc.nextLine();
                    if(s2.equals("q") || s2.equals("Q")){
                        out.println(s2);
                        break;
                    }  else { // if {}
                        out.println(name + " => " + s2);
                    }
                } catch(Exception e){   // try{}
                    System.out.println("예외 S1 : " + e);
                }   // catch {} // 예외 s1

            }   // while{}

            out.close();

            socket.close();

        } catch(Exception e){ // try  {}
            System.out.println("예외 S2 : " + e);
        }   // catch {} // 예외 s2
    }   // run{}
}
