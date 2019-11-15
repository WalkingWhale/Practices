import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class step01{
	
	static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

	public static void main(String[] args) throws UnknownHostException, IOException{
		Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	String columnName = null;
    	String dbmsType = null;
    	String sql = null;
    	
        try{
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");
            
            con.setAutoCommit(false);
            
            sql = "select * from userinfo";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	System.out.println(rs.getString(1));
            }
            
            System.out.print("ID를 입력해 주세요 : ");
            Scanner sc = new Scanner(System.in);
            String ID = sc.nextLine();
            
            sql = "select * from userinfo where ID = '" + ID + "'";

			pstmt = con.prepareStatement(sql);
			sql = pstmt.toString();
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	if(rs.getString("id").equals(ID)) {
               	 System.out.print("비밀번호를 입력해 주세요 : ");
                    sc = new Scanner(System.in);
                    String password = sc.nextLine();
                    
                    if(rs.getString("password").equals(password)) {
                   	 System.out.println("로그인 되었습니다");
                    }
                    else {
                   	 System.out.println("비밀번호가 틀렸습니다.");
                   	 System.out.println("종료합니다.");
                    }
               }
               else {
               	System.out.println("등록되지 않은 id입니다");
               	System.out.println("종료합니다");
               	return;
               }
            }
            
            try{
                String ServerIP = "localhost";
                // String ServerIP = args[0];

                if(args.length>0){
                    ServerIP =args[0];
                }
                Socket socket = new Socket(ServerIP, 9999);
                System.out.println("서버와 연결이 되었습니다......");

                // 서버에서 보내는 메시지를 사용자의 콘솔에 출력하는 스레드
                Thread receiver = new Receiver01(socket);
                receiver.start();

                new ChatWin(socket,ID);

            } catch (Exception e){ // try 끝
                e.printStackTrace();           
            }
            
            
        } catch (Exception e){
            System.out.println("Connection Error");
            e.printStackTrace();
        } finally {
        	try {
        		if(rs != null) rs.close();
        		if(pstmt != null) pstmt.close();
        		if(con != null) con.close();
        		
        	}catch(SQLException sqle) {}
        }
    }

	

}
