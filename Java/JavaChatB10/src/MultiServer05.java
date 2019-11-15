import java.io.*;
import java.net.*;

// 객체화 - static 사용 최소화 - 소캣분화를 통해 여러명의 유저 접속 가능
public class MultiServer05 {
    ServerSocket serverSocket = null;
    Socket socket = null;

    // 생성자
    public MultiServer05() {

    }

    public void init() {

        try {   // try 1 시작
            serverSocket = new ServerSocket(9999); // 9999포트로 서버소켓 객체생성.
            System.out.println("서버가 시작되었습니다.");

            while (true) {    // while 1 시작
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + ":" + socket.getPort());

                Thread msr = new MultiServerT(socket);
                msr.start();    // 쓰레드 시동
            }   // while 1 끝
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }   // init 끝

    public static void main(String[] args) {
        // 서버객체 생성
        MultiServer05 ms = new MultiServer05();
        ms.init();
    }   // main 끝{


    ////////////////////////////////////////////////////////////////////
    // 내부 클래스
    // 클라이언트로부터 읽어온 메시지를 다른 클라이언트(socker)에 보내는 역할을 하는 메서드
    class MultiServerT extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;

        // 생성자
        public MultiServerT(Socket socket) {
            this.socket = socket;

            try {
                out = new PrintWriter(this.socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            } catch (Exception e) {
                System.out.println("예외 : " + e);
            }
        }   // 생성자 끝

        // 쓰레드를 사용하기 위해서 run()메서드 재정의
        @Override
        public void run() {
            String s;
            try {   // try 1 시작
                while (in != null) { // while 2 시작

                    s = in.readLine();

                    if (s == null) {
                        break;
                    }
                    if (s.equals("q") || s.equals("Q")) {
                        break;
                    }

                    System.out.println(s);
                    sendAllMsg(s, out);
                }   // while 2 끝

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

        } // run 끝

        // 접속된 모든 클라이언트들에게 메시지를 전달
        public void sendAllMsg(String msg, PrintWriter out) {
            try {
                out.println(msg);
            } catch (Exception e) {
                System.out.println("예외 : " + e);
            }
        }   // sendAllMsg 끝
    }   // MultiserverT끝

}

