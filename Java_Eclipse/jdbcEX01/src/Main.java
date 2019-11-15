import java.sql.*;

public class Main {
    static {
        try{	// 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{	// 커넥션 구축
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");
            	// sql문을 쓰기 위해서 statement 선언
            Statement stmt = con.createStatement();
            	// sql문 변수에 저장
            StringBuffer sb = new StringBuffer();
            sb.append("select * from employee");
            	// executeQuery를 통해 sql문 실행
            ResultSet rs = stmt.executeQuery(sb.toString());

            while (rs.next()){
                System.out.print("eno : " + rs.getInt(1) + ", ");
                System.out.println("ename : " + rs.getString("ename"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException sqle){
            System.out.println("Connection Error");
            sqle.printStackTrace();
        }
    }
}
