import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

// To create UI and listen to client access
public class Server extends JFrame implements Runnable {

    static final int PORT = 9999; // 서버프로그램의 포트번호
    Socket socket;
    ServerSocket serverSocket; // 서버소켓
    DataOutputStream dos;
    DataInputStream dis;
    ArrayList<User> userArray; // 서버에 접속한 사용자들
    ArrayList<Room> roomArray; // 서버가 열어놓은 채팅방들
    ArrayList<User> waitArray;  // 대기방에 있는 사용자들
    ArrayList<String> badwordArray; // 서버 금칙어 Array
    int sizeX = 600, sizeY = 600;
    Dimension whole, part;
    int xPos, yPos;
    JTextArea jta;
    ImageIcon icon;
    JPanel jp;

    Server() {
        userArray = new ArrayList<User>();
        roomArray = new ArrayList<Room>();
        waitArray = new ArrayList<User>();
        badwordArray = new ArrayList<String>();
        setTitle("Server");
        setSize(sizeX, sizeY);

        icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());//타이틀바에 이미지넣기

        jta = new JTextArea();
        jp = new JPanel();

        jp.setLayout(new GridLayout(1, 2)); // 그리드 레이아웃
        jta.setEditable(false); // 에디팅 불가
        jta.setLineWrap(true); // 자동줄바꿈

        JScrollPane jsp = new JScrollPane(jta); // 텍스트에어리어에 스크롤 추가
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jp.add(jsp);// 패널에 스크롤 붙임
        jta.setText("Server Start\n");

        add(jp); // 프레임에 패널 붙임

        // 윈도우 위치 계산
        whole = Toolkit.getDefaultToolkit().getScreenSize();
        part = this.getSize();
        xPos = (int) (whole.getWidth() / 2 - part.getWidth() / 2);
        yPos = (int) (whole.getHeight() / 2 - part.getHeight() / 2);

        setLocation(xPos, yPos);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create server UI
        System.out.println("Server start");
        Server server = new Server();
        Thread thread = new Thread(server);
        thread.start();
    }

    @Override
    public void run() {
        // 클라이언트 대기 모드

        // 서버소켓 생성
        try {
            InetAddress addr = InetAddress.getLocalHost(); // 로컬호스트 주소
            serverSocket = new ServerSocket(PORT); // 서버소켓 생성
            jta.append(PORT + "번 포트로 정상적으로 소켓이 생성되었습니다.\n" + "현재 열린 서버의 IP 주소는 "
                    + addr.getHostAddress().toString() + "입니다. \n");
        } catch (IOException e1) {
            e1.printStackTrace();
            jta.append("서버 소켓 생성에러\n");
        }

        while (true) {
            socket = null;
            dis = null;
            dos = null;
            try {
                // 무한반복, 입출력 에러가 나거나 프로그램이 종료될 때까지 실행
                socket = serverSocket.accept(); // 클라이언트 접속 대기
                jta.append("클라이언트 " + socket.getInetAddress().getHostAddress()	+ "가 접속되었습니다.\n");

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                jta.append("클라이언트 접속에러\n");
            }
            try {
                // 스트림 객체 생성
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    jta.append("스트림 해제에러\n");
                }
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    jta.append("소켓 해제에러\n");
                }
                jta.append("스트림 생성에러\n");
            }
            User person = new User(dis, dos); // 가명의 사용자 객체 생성
            person.setIP(socket.getInetAddress().getHostName()); // 아이피주소 설정 부여
            DBBadword badword = new DBBadword();
            badwordArray = badword.getBadword();

            Thread thread = new Thread(new ServerThread(jta, person, userArray, waitArray, roomArray, badwordArray));
            thread.start(); // 스레드 시작
        }
    }
}