import java.io.*;
import java.net.*;

public class MultiServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String s = "";

        try{
            serverSocket = new ServerSocket(9999);
            System.out.println("서버가 시작되었습니다");

            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + " : " + socket.getPort());

            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            s = in.readLine();
            System.out.println(s);
            out.println(s);

            System.out.println("Bye....");
        } catch (Exception e){ // try 끝
            e.printStackTrace();
        }   // catch 끝
    }// main 끝
}
