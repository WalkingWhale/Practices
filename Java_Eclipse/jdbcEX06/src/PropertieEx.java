import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PropertieEx {
	static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	Properties pro = null;
    	FileInputStream fis = null;
    	
        try{
        	
        	pro = new Properties();
        	fis = new FileInputStream("D:\\Java_Eclipse\\jdbc.properties");
        	pro.load(fis);
        	
        	String url = pro.getProperty("url");
        	String user = pro.getProperty("user");
        	String pwd = pro.getProperty("password");
        	
            con = DriverManager.getConnection(
                    url,
                    user,
                    pwd);
            
           String sql = "select * from employee";
           pstmt = con.prepareStatement(sql);
           rs = pstmt.executeQuery();
           
           while(rs.next()) {
        	   System.out.print("eno : " + rs.getInt(1) + ", ");
        	   System.out.println("ename : " + rs.getString(2));
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
