import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");

            Statement stmt = con.createStatement();            
            
            //---------------------------------------------------------------
            StringBuffer sb = new StringBuffer();
            sb.append("create table test1 ( ");
            sb.append(" id varchar(10), ");
            sb.append(" age number ) ");
            
            int updateCount = stmt.executeUpdate(sb.toString());
            System.out.println("createCount : " + updateCount);
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("insert into test1 ");
            sb.append("values ('홍길동', 10)");
            updateCount = stmt.executeUpdate(sb.toString());	// 결과 한개나오면 excuteUpdate
            System.out.println("createCount : " + updateCount);
            
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("select * from test1");
            ResultSet rs = stmt.executeQuery(sb.toString());	// 결과 여려개 나오면 executeQuery

            while (rs.next()){
                System.out.print("id : " + rs.getString(1) + ", ");
                System.out.println("age : " + rs.getString("age"));
            }
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("update test1 ");
            sb.append("	set id = '전우치', ");
            sb.append("		age = 20 ");
            sb.append("	where id = '홍길동'");
            updateCount = stmt.executeUpdate(sb.toString());
            System.out.println("createCount : " + updateCount);
            
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("select * from test1");
            rs = stmt.executeQuery(sb.toString());

            while (rs.next()){
                System.out.print("id : " + rs.getString(1) + ", ");
                System.out.println("age : " + rs.getString("age"));
            }
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("delete from test1");
            updateCount = stmt.executeUpdate(sb.toString());
            System.out.println("createCount : " + updateCount);
            
            //---------------------------------------------------------------
            sb.setLength(0);;
            sb.append("drop table test1");
            updateCount = stmt.executeUpdate(sb.toString());
            System.out.println("createCount : " + updateCount);
            
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException sqle){
            System.out.println("Connection Error");
            sqle.printStackTrace();
        }
    }
}
