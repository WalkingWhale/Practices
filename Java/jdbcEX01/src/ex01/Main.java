package ex01;

import java.sql.*;

public class Main {
    static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String tName = "PHONEBOOK";
        String name = "1";
        String pNum = "11";
        String eAddress = "null";
        try{
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");

            String sql = "insert into " + tName + " ( name, phonenumber, email) " +
                    " values ("+name+", "+pNum+", "+eAddress+");";
            PreparedStatement stmt = con.prepareStatement(sql);
            int updateCount = stmt.executeUpdate();

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

