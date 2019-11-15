import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBBadword {

    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    String sql = null;
    String sql2 = null;
    Properties info;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    int length;
    ArrayList<String> temp = new ArrayList<String>();

    public ArrayList<String> getBadword(){
        String word;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = con.createStatement();

            sql = "select * from badword";

            rs = stmt.executeQuery(sql);

            while(rs.next()){
                word = rs.getString(1);
                temp.add(word);
            }

            dbClose();


        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }

        return temp;
    }

    public void update(String word){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = con.createStatement();

            sql = "insert into badword values ('" + word + "')";

            int result = stmt.executeUpdate(sql);

            if(result == 1){
                System.out.println("금칙어 추가 성공");
            }

            dbClose();


        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }
    }

    public void dbClose() {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (con != null)
                con.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
