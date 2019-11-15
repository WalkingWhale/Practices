/*콘솔 멀티채팅 클라이언트 프로그램 ver3*/
/*쓰래드 사용*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient4 {
    public static void main(String[] args) throws UnknownHostException,
            IOException
    {
        System.out.println("이름을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String sName = sc.nextLine();
        PrintWriter out = null;

        try{
            String ServerIP = "localhost";
            Socket socket = new Socket(ServerIP, 9999);
            System.out.println("서버와 연결이 되었습니다......");

            Thread receiver = new Receiver4(socket);
            receiver.start();

            out = new PrintWriter(socket.getOutputStream(),true);

            out.println(sName);

            while(out != null){
                try{
                    // 출력
                    String s2 = sc.nextLine();
                    if(s2.equals("q") || s2.equals("Q")){
                        out.println(s2);
                        break;
                    }
                    else{
                        out.println(sName+" => "+s2);
                    }
                }catch (Exception e){
                    System.out.println("예외 : " + e);
                }
            }

            out.close();
            socket.close();

        } catch (Exception e){ // try 끝
            System.out.println("예외[MultiClient class] : " + e);
        }   // catch 끝
    }// main 끝

}
