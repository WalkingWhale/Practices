import java.io.*;
import java.net.*;
import java.util.*;

// 객체화 - static 사용 최소화 - 소캣분화를 통해 여러명의 유저 접속 가능
public class MultiServer06 {
    ServerSocket serverSocket = null;
    Socket socket = null;
    Map<String, PrintWriter> clientMap;

    // 생성자
    public MultiServer06() {
        // 클라이언트의 출력스트림을 저장할 해쉬맵 생성.
        clientMap = new HashMap<String, PrintWriter>();
        // 해쉬맵 동기화 설정
        Collections.synchronizedMap(clientMap);
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

    // 접속된 모든 클라이언트들에게 메시지를 전달
    public void sendAllMsg(String msg) {

        // 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
        Iterator it = clientMap.keySet().iterator();

        while (it.hasNext()){
            try{
                PrintWriter it_out = (PrintWriter) clientMap.get(it.next());
                it_out.println(msg);
            } catch ( Exception e){ // try 종료 catch 시작
                System.out.println("예외 : " + e);
            }   // catch 종료
        }   // while 종료
    }   // sendAllMsg 끝

    public static void main(String[] args) {
        // 서버객체 생성
        MultiServer06 ms = new MultiServer06();
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
            // String s;
            String name  = "";  // 클라이언트로부터 받은 이름을 저장할 변수.
            try {   // try 1 시작
                name = in.readLine();

                sendAllMsg(name + "님이 입장하셨습니다.");

                clientMap.put(name,out);
                System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

                String s = "";
                while (in != null) { // while 1 시작

                    s = in.readLine();
                    if (s.equals("q") || s.equals("Q")) {
                        break;
                    }

                    sendAllMsg(s);
                }   // while 1 끝

                System.out.println("Bye....");

            } catch (Exception e) { // try 1 끝 , catch 1시작
                e.printStackTrace();
            } finally {  // catch 1 끝 finally 시작
                // 예외가 발생할때 퇴장. 해쉬맵에서 해당 데이터 제거.
                // 보통 종료하거나 나가면 java.net.SocketException 예외발생
                clientMap.remove(name);
                sendAllMsg(name + "님이 퇴장하셨습니다");
                System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

                try {   // try 2 시작
                    in.close();
                    out.close();

                    socket.close();
                } catch (Exception e) { // try 2 끝 , catch 2 시작
                    e.printStackTrace();
                }   // catch 2 끝
            } // finally 끝

        } // run 끝

    }   // MultiserverT끝

}

