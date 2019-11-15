import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	static Connection con;
	static PreparedStatement stmt;
	static String tName;
	//static ResultSet rs;
	
	static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }	

    public static void showMenu(){
        System.out.println("   [메뉴 선택]  ");
        System.out.println("1. 전화번호 입력");
        System.out.println("2. 전화번호 조회");
        System.out.println("3. 전화번호 삭제");
        System.out.println("4. 즐겨찾는 번호 설정");
        System.out.println("5. 즐겨찾는 번호 해제");
        System.out.println("0. 프로그램 종료");
        System.out.println("10. 전화번호 전체 조회");
        System.out.println("11. 즐겨찾는 번호 조회");
        System.out.print(" 메뉴 선택: ");
    }

    public static void addNumber() {    	
    
    	String name;
        String pNum;
        String eAddress;
        while(true) {
        	try {
            	while(true){
                    System.out.print("이름을 입력해 주십시오 : ");
                    name = sc.nextLine();
                    if(name.length() == 0){
                        System.out.println("이름이 입력 되지 않았습니다.");
                        continue;
                    }
                    System.out.print("전화번호를 입력해 주십시오 : ");
                    pNum = sc.nextLine();
                    if(pNum.length() == 0){
                        System.out.println("전화번호가 입력 되지 않았습니다.");
                        continue;
                    }
                    System.out.print("Email주소를 입력해 주십시오 : ");
                    eAddress = sc.nextLine();
                    break;
                }
                
                if (eAddress.length() != 0){
                	String sql = "insert into " + tName + "( name, phonenumber, email )" +  
                			" values ('" + name + "', '" + pNum + "', '" + eAddress + "')";
                	//System.out.println(sql);
                	stmt = con.prepareStatement(sql);
                	
                	try {
                		int updateCount = stmt.executeUpdate();
                	}catch(SQLIntegrityConstraintViolationException ce) {
                		System.out.println("중복된 전화번호가 존재합니다. 확인해 주십시오");
                    	return;
                    }
                    
                    System.out.println("Insert 완료");
                }
                else{
                	String sql = "insert into " + tName + " (name, phonenumber)" +
                			" values ( '" + name + "', '" + pNum + "')";
                	//System.out.println(sql);
                	stmt = con.prepareStatement(sql);
                	try {
                		int updateCount = stmt.executeUpdate();
                	}catch(SQLIntegrityConstraintViolationException ce) {
                		System.out.println("중복된 전화번호가 존재합니다. 확인해 주십시오");
                    	return;
                    }
                    System.out.println("Insert 완료");
                }
                break;
                
            }catch(SQLException sqle) {
            	sqle.printStackTrace();
            }
        }
        
        
    }

    public static void showNumber() throws SQLException{
    	String srch;
        System.out.println("   전화번호 조회  ");
        System.out.print("찾고자 하는 사람의 이름을 입력해 주십시오: ");
        srch = sc.nextLine();
        
        String sql = "select * from " + tName + " where name = '" + srch + "'";
        //System.out.println(sql);
        
        stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        System.out.println("------검색결과------");
        System.out.println("이름\t전화번호\tEmail");
    	while(rs.next()) {
			System.out.println(rs.getString(1) + ", " +
							   rs.getString(2) + ", " +
							   rs.getString(3));
		}
    	
    	rs.close();
    }
    
    public static void delNumber() throws SQLException{
    	
    	System.out.println("   전화번호 삭제  ");
        System.out.print("삭제하고자 하는 사람의 이름을 입력해 주십시오: ");
        String del = sc.nextLine();
        
        String sql = "delete phonebook where name = '" + del + "'" ;
        //System.out.println(sql);
        stmt = con.prepareStatement(sql);
    	int updateCount = stmt.executeUpdate();
    }

    public static void showAll() throws SQLException{
    	String sql = "select * from phonebook";
    	stmt = con.prepareStatement(sql);
    	ResultSet rs = stmt.executeQuery();
    	
    	System.out.println("이름\t전화번호\tEmail");
    	while(rs.next()) {
			System.out.println(rs.getString(1) + "\t" +
							   rs.getString(2) + "\t" +
							   rs.getString(3));
		}
    	
    	rs.close();
    }
    
    public static void showFavorite() throws SQLException{
    	String sql = "select * from phonebook where favorite ='1' order by name";
    	stmt = con.prepareStatement(sql);
    	ResultSet rs = stmt.executeQuery();
    	
    	System.out.println("이름\t전화번호\tEmail");
    	while(rs.next()) {
			System.out.println(rs.getString(1) + "\t " +
							   rs.getString(2) + "\t " +
							   rs.getString(3));
		}
    	
    	rs.close();
    }
    
    public static void setFavorite() {
    	System.out.println("   즐겨찾기 등록  ");
        System.out.print("즐겨찾기에 등록하고자하는 사람의 이름을 입력해 주십시오: ");
        String fav = sc.nextLine();
        String sql = "update " + tName + " set favorite = '1' where name = '" + fav + "'";
        //System.out.println(sql);
        
        try {
        	stmt = con.prepareStatement(sql);
        	int updateCount = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public static void unsetFavorite() {
    	System.out.println("   즐겨찾기 해제  ");
        System.out.print("즐겨찾기목록에서 해제하고자하는 사람의 이름을 입력해 주십시오: ");
        String nomorefav = sc.nextLine();
        String sql = "update " + tName + " set favorite = '0' where name = '" + nomorefav + "'";
        //System.out.println(sql);
        
        try {
        	stmt = con.prepareStatement(sql);
        	int updateCount = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public static void saveInfo() throws SQLException{
    	System.out.println("Commit 완료");
    	con.commit();
    }

    public static void readInfo() throws SQLException{
    	con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "scott",
                "tiger");
    	
        tName = "PHONEBOOK";
    }

    public static void main(String[] args) throws SQLException {
    	int choice;

        readInfo();
        
        con.setAutoCommit(false);

        while (true){
        	
            showMenu();
            
            while(true){
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. 다시 입력해 주십시오");
                    sc = new Scanner(System.in);
                }

            }
            
            switch (choice) {
                case 1:{
                    addNumber();
                    break;
                }
                
                case 2:{
                    showNumber();
                    break;
                }
                
                case 3:{
                    delNumber();
                    break;
                }
                
                case 4:{
                	setFavorite();
                	break;
                }
                
                case 5:{
                	unsetFavorite();
                	break;
                }                
                
                case 0:{
                    System.out.println("정보를 저장중입니다.");
                    saveInfo();
                    System.out.println("프로그램을 종료합니다.");
                    //if(rs != null) rs.close();
            		if(stmt != null) stmt.close();
            		if(con != null) con.close();   
                    return;
                }
                
                case 10:{
                    showAll();
                    break;
                }
                
                case 11:{
                	showFavorite();
                	break;
                }
                
                default:{
                    System.out.println("잘못입력하셨습니다.");
                    break;
                }

            } 
        }
    }
}
