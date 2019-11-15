import java.io.*;
import java.net.*;

// 객체화 - static 사용
public class MultiServer03 {
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static String s ="";

    // 생성자
    public MultiServer03(){

    }

    public static void init(){

        try {   // try 1 시작
            serverSocket = new ServerSocket( 9999);
            System.out.println("서버가 시작되었습니다.");

            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + ":" + socket.getPort() );

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(in != null){

                s = in.readLine();

                if(s==null){
                    break;
                }
                if(s.equals("q")|| s.equals("Q")){
                    break;
                }

                System.out.println(s);
                sendAllMsg(s);
            }   // while 끝

            System.out.println("Bye....");

        } catch (Exception e) { // try 1 끝 , catch 1시작
            e.printStackTrace();
        } finally {  // catch 1 끝
            try {   // try 2 시작
                in.close();
                out.close();

                socket.close();
                serverSocket.close();
            } catch (Exception e) { // try 2 끝 , catch 2 시작
                e.printStackTrace();
            }   // catch 2 끝
        } // finally 끝
    }   // init 끝

    // 접속된 모든 클라이언트들에게 메시지를 전달
    public static void sendAllMsg(String msg){
        try{
            out.println(msg);
        } catch(Exception e){
            System.out.println("예외 : " + e);
        }
    }   // sendAllMsg 끝

    public static void main(String[] args) {
        init();
    }   // main 끝{
}

