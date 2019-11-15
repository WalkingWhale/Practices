import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BatchInsertEx {
	static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	String sql = null;
    	Boolean commit = false;
    	
        try{
        	
        	con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");
            
        	stmt = con.createStatement();
        	sql = 	"create table test4 (" +
        			" 	id varchar2(10), " +
        			" 	password varchar2(10) )";
        	
        	stmt.execute(sql);
        	
        	con.setAutoCommit(false);
            
            stmt.addBatch("insert into test4 " +
            		"values('홍길동', '1111')");
            stmt.addBatch("insert into test4 " +
        			"values('전우치', '2222')");
            stmt.addBatch("insert into test4 " +
        			"values('손오공', '3333')");
            stmt.addBatch("insert into test4 " +
        			"values('이지암', '4444')");
            
            int[] updateCount = stmt.executeBatch();
            commit = true;
            con.commit();
            
            rs = stmt.executeQuery("select * from test4");
            
            while(rs.next()) {
            	String id = rs.getString("id");
            	String password = rs.getString("password");
            	
            	System.out.println("id : " + id + ", password : " + password );
            }
            
            //-----------------------------------------------------------------
            sql = "drop table test4";
            stmt.execute(sql);
            
        } catch (Exception e){
            System.out.println("Connection Error");
            e.printStackTrace();
        } finally {
        	try {
        		if(!commit) con.rollback();
        		if(rs != null) rs.close();
        		if(stmt != null) stmt.close();
        		if(con != null) con.close();
        		
        	}catch(SQLException sqle) {}
        }
    }
}
