import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

// 객체화 - static 사용 최소화 - 소캣분화를 통해 여러명의 유저 접속 가능
public class P_server01 {
    ServerSocket serverSocket = null;
    Socket socket = null;
    Map<String, PrintWriter> clientMap;
    Scanner sc = new Scanner(System.in);
    Connection con = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe",
            "scott",
            "tiger");

    static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    // 생성자
    public P_server01() throws SQLException {
        // 클라이언트의 출력스트림을 저장할 해쉬맵 생성.
        clientMap = new HashMap<String, PrintWriter>();
        // 해쉬맵 동기화 설정
        Collections.synchronizedMap(clientMap);
    }

    public void init() {
        boolean flag = false;
        try {   // try 1 시작
            serverSocket = new ServerSocket(9999); // 9999포트로 서버소켓 객체생성.
            System.out.println("서버가 시작되었습니다.");

            while(!flag){

                break;
            }

            while (true) {    // while 1 시작
                socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + ":" + socket.getPort());

                Thread msr = new MultiServerT(socket);  // 쓰레드 생성.
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

    // 접속자 리스트 보내기
    public void list(PrintWriter out) {

        // 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
        Iterator<String> it = clientMap.keySet().iterator();
        String msg = "사용자 리스트[";

        while (it.hasNext()){   // while 시작
            msg += (String)it.next() + ", ";
        }   // while 종료
        msg = msg.substring(0,msg.length() -1) + "]";

        try{
            out.println(URLEncoder.encode(msg,"UTF-8"));
            //out.println(msg);
        } catch (Exception e){

        }

    }   // sendAllMsg 끝

    // 접속된 모든 클라이언트들에게 메시지를 전달
    public void sendAllMsg(String user, String msg) {

        // 출력스트림을 순차적으로 얻어와서 해당 메시지를 출력한다.
        Iterator it = clientMap.keySet().iterator();

        while (it.hasNext()){
            try{
                PrintWriter it_out = (PrintWriter) clientMap.get(it.next());
                if(user.equals("")){
                    it_out.println(URLEncoder.encode(msg,"UTF-8"));
                    //it_out.println(msg);
                }
                else{
                    it_out.println("["+URLEncoder.encode(user,"UTF-8")+"]"
                            +URLEncoder.encode(msg,"UTF-8"));
                    //it_out.println("["+user+"]"+msg);
                }
            } catch ( Exception e){ // try 종료 catch 시작
                System.out.println("예외 : " + e);
            }   // catch 종료
        }   // while 종료
    }   // sendAllMsg 끝

    public void ShowMenu() {
        System.out.println("메뉴를 입력해 주세요 : ");
        System.out.println("1: 로그인");
        System.out.println("2: 회원가입");
        System.out.println("3: 종료");
        System.out.print("입력 : ");
    }

    public static void main(String[] args) throws SQLException {
        // 서버객체 생성
        P_server01 server = new P_server01();
        server.init();
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
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"UTF-8"));
                //in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
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
                name = URLDecoder.decode(name, "UTF-8");

                sendAllMsg("",name + "님이 입장하셨습니다.");

                // 현재 객체가 가지고있는 소켓을 제외하고 다른 소켓(클라이언트)들에게 접속을 알림
                clientMap.put(name,out);    // 해쉬맵에 키를 name으로 출력스트림 객체를 저장.
                System.out.println("현재 접속자 수는 " + clientMap.size() + "명 입니다.");

                String s = "";
                while (in != null) { // while 1 시작

                    s = in.readLine();
                    try{
                        s = URLDecoder.decode(s,"UTF-8");
                    } catch (NullPointerException e){

                    }
                    System.out.println(s);

                    try{
                        if(s.equals("/list")){
                            list(out);
                        }

                        else{
                            sendAllMsg(name, s);
                        }
                    } catch (NullPointerException e){

                    }


                }   // while 1 끝

                System.out.println("Bye....");

            } catch (Exception e) { // try 1 끝 , catch 1시작
                e.printStackTrace();
            } finally {  // catch 1 끝 finally 시작
                // 예외가 발생할때 퇴장. 해쉬맵에서 해당 데이터 제거.
                // 보통 종료하거나 나가면 java.net.SocketException 예외발생
                clientMap.remove(name);
                sendAllMsg("",name + "님이 퇴장하셨습니다");
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

