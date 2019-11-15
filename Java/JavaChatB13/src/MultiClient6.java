/*콘솔 멀티채팅 클라이언트 프로그램 ver3*/
/*쓰래드 사용*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient6 {
    public static void main(String[] args) throws UnknownHostException,
            IOException
    {
        System.out.println("이름을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String sName = sc.nextLine();

        try{
            String ServerIP = "localhost";
            // String ServerIP = args[0];

            if(args.length>0){
                ServerIP =args[0];
            }
            Socket socket = new Socket(ServerIP, 9999);
            System.out.println("서버와 연결이 되었습니다......");

            // 서버에서 보내는 메시지를 사용자의 콘솔에 출력하는 스레드
            Thread receiver = new Receiver6(socket);
            receiver.start();

            // 사용자로부터 얻은 문자열을 서버로 전송해주는 스레드
            //Thread sender = new Sender6(socket,sName);
            //sender.start();

            new ChatWin(socket,sName);

        } catch (Exception e){ // try 끝
            System.out.println("예외[MultiClient class] : " + e);
        }   // catch 끝
    }// main 끝

}
