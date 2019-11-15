import java.io.*;
import java.net.*;

public class Receiver6 extends Thread{
    Socket socket;
    BufferedReader in = null;

    // socker을 매개변수로 받는 생성자
    public Receiver6(Socket socket){
        this.socket = socket;

        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }catch(Exception e){
            System.out.println("예외1 : " + e);
        }
    }

    @Override
    public void run() {
        while(in != null){
            try{
                System.out.println(">> : " + URLDecoder.decode(in.readLine(),"UTF-8"));
                //System.out.println(">> : " + in.readLine());
            }catch (java.net.SocketException ne){
                break;
            }catch (Exception e){
                System.out.println("예외2 : " + e);
            }
        }

        try{
            in.close();
        } catch (Exception e){
            System.out.println("예외3 : " + e);
        }
    }
}
