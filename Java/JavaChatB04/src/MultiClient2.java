/*콘솔 멀티채팅 클라이언트 프로그램 ver2*/
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MultiClient2 {
    public static void main(String[] args) throws UnknownHostException,
                                                  IOException
    {
        System.out.println("이름을 입력해 주세요.");
        Scanner sc = new Scanner(System.in);
        String sName = sc.nextLine();

        ServerSocket serverSocket = null;
//        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;


        try{
            String ServerIP = "localhost";
            Socket socket = new Socket(ServerIP, 9999);
            System.out.println("서버와 연결이 되었습니다......");

            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(sName);
            while(out != null){
                try{
                    // 입력
                    if(in != null){
                        System.out.println("Receive : " + in.readLine());
                    }
                    // 출력
                    String s2 = sc.nextLine();
                    if(s2.equals("q") || s2.equals("Q")){
                        out.println(s2);
                        break;
                    }
                    else{
                        out.println(sName+" => "+s2);
                    }
                }catch (IOException e){
                    System.out.println("예외 : " + e);
                }
            }

            in.close();
            out.close();
            socket.close();


            System.out.println("Bye....");
        } catch (Exception e){ // try 끝
            System.out.println("예외[MultiClient class] : " + e);
        }   // catch 끝
    }// main 끝
}
