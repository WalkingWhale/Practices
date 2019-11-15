import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class main {
    static String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    static String sql = null;
    static String sql2 = null;
    static Properties info;
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static int length;
    static ArrayList<String> temp = new ArrayList<String>();

    public static void main(String[] args) {
        String sTemp;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = con.createStatement();

            sql = "select * from badword";
            sql2 = "select count(*) from badword";

            rs = stmt.executeQuery(sql);

            while(rs.next()){
                sTemp = rs.getString(1);
                System.out.println(sTemp);
                temp.add(sTemp);
            }

            System.out.println(temp.size());
            for(int i = 0; i < temp.size() ; i++){
                System.out.print(temp.get(i) +" ");
            }
            dbClose();


        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }
    }

    static public void dbClose() {
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
