import java.sql.*;
import java.util.Properties;

public class DBBlock {

    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    String sql = null;
    Properties info;
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    String id;
    int run = 0;        // 0이면 해당 id 유저 없음

    int blockID(String id){        // id 받아와서 해당 대상 서버에서 block
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = con.createStatement();

            this.id = id;

            sql = "update userinfo set blacklist = '1' where id = '" + id + "'";
            run = stmt.executeUpdate(sql);      // 1이면 재대로 ban

            dbClose();


        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }

        return run;
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