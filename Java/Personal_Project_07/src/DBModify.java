import java.sql.*;
import java.util.Properties;

public class DBModify{

    String id = null;
    String pw;

//    JFrame frame;
//    JPanel logPanel;
//    JPanel logPanel1;
//    JPanel logPanel2;
//    JPanel logPanel3;
//    JTextField idTf, pwTf= null;
//    JButton okBtn;
//
//    MsgeBox msgbox = new MsgeBox();

    Statement stmt = null;
    ResultSet rs = null;
    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    String sql = null;
    String sql2 = null;
    Properties info = null;
    Connection con = null;

    // id를 받아와서 그것의 정보로 pw 수정및 삭제
    void changePW(String id, String pw) {
        this.id = id;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = con.createStatement();

            sql = "update userinfo set password = '" + pw + "' where id = '" + id + "'";
            stmt.executeUpdate(sql);

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

