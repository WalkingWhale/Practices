import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class MyPhoneBook {
	
	Scanner sc = new Scanner(System.in);
	Connection con = null;
	PreparedStatement pstmt = null;
	String sql = null;
	ResultSet rs = null;
	
    static {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException chfe){
            chfe.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws SQLException{

		MyPhoneBook mpb = new MyPhoneBook();
		mpb.doRun();
	}
    
    public void doRun() throws SQLException {
    	connectDatabase();
		int choice;
		
		while (true) {
			showMenu();
			choice = sc.nextInt();
			sc.nextLine();
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
         		  if(rs != null) rs.close();
         		  if(pstmt != null) pstmt.close();
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
    	
		

	public void showMenu() {
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

	public void addNumber() {
		String name = null;
		String phoneNumber = null;
		String email = null;

		while (name == null) {
			System.out.print("이름 : ");
			name = sc.nextLine();
			if (name.trim().equals("")) {
				System.out.println("필수입력값입니다");
				name = null;
			}
	
		}

		while (phoneNumber == null) {
			System.out.print("전화번호 : ");
			phoneNumber = sc.nextLine();
			if (phoneNumber.trim().equals("")) {
				System.out.println("필수입력값입니다.");
				phoneNumber = null;
			}
		}
		
		System.out.print("이메일 : ");
		email = sc.nextLine();

		try {
			sql = "insert into phonebook (name,phonenumber,email) values(?, ?, ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phoneNumber);
			if (!email.trim().equals("")) {
				pstmt.setString(3, email);
			} else {
				pstmt.setString(3, null);
			}
			pstmt.executeUpdate();
			System.out.println("DB에 추가되었습니다");
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("중복된 이름이 있습니다.");
		} catch (SQLException sqle) {
			System.out.println("데이터베이스 입력 에러입니다.");
		} 

	}

	public void showNumber() {
		String name = null;
		while (name == null) {
			System.out.print("조회할 이름 : ");
			name = sc.nextLine();
			if (name.trim().equals("")) {
				System.out.println("다시입력해주세요");
				name = null;
			}
		}
		try {
			sql = "select * from phonebook where name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("이름: " + rs.getString(1));
				System.out.println("전화번호: " + rs.getString(2));
				if(rs.getString(3) != null) {
					System.out.println("이메일: " + rs.getString(3));
				}
			} else {
				System.out.println("해당값이없습니다");
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

	public void delNumber() {
		String name = null;
		while (name == null) {
			System.out.print("삭제할 이름 : ");
			name = sc.nextLine();
			if (name.trim().equals("")) {
				System.out.println("다시입력해주세요");
				name = null;
			}
		}

		try {
			sql = "delete phonebook where name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			if (pstmt.executeUpdate() > 0) {
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("해당 값이 없습니다.");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void saveInfo() {

		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void connectDatabase() {

		try {
    		con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "scott",
                    "tiger");
    	}catch(SQLException sqle){
    		sqle.printStackTrace();
    	}

	}
	
	public void setFavorite() {
    	System.out.println("   즐겨찾기 등록  ");
        System.out.print("즐겨찾기에 등록하고자하는 사람의 이름을 입력해 주십시오: ");
        String fav = sc.nextLine();
        
        //System.out.println(sql);
        
        try {
        	String sql = "update phonebook set favorite = '1' where name = ?";
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1,fav);
        	int updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public void unsetFavorite() {
    	System.out.println("   즐겨찾기 해제  ");
        System.out.print("즐겨찾기목록에서 해제하고자하는 사람의 이름을 입력해 주십시오: ");
        String nomorefav = sc.nextLine();
        
        //System.out.println(sql);
        
        try {
        	String sql = "update phonebook set favorite = '0' where name = ?";
        	pstmt = con.prepareStatement(sql);
        	pstmt.setString(1, nomorefav);
        	int updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

	public void showAll() throws SQLException{
    	String sql = "select * from phonebook";
    	pstmt = con.prepareStatement(sql);
    	rs = pstmt.executeQuery();
    	
    	System.out.println("이름\t전화번호\tEmail");
    	while(rs.next()) {
			System.out.println(rs.getString(1) + "\t" +
							   rs.getString(2) + "\t" +
							   rs.getString(3));
		}
    }
    
	public void showFavorite() throws SQLException{
    	String sql = "select * from phonebook where favorite ='1' order by name";
    	pstmt = con.prepareStatement(sql);
    	ResultSet rs = pstmt.executeQuery();
    	
    	System.out.println("이름\t전화번호\tEmail");
    	while(rs.next()) {
			System.out.println(rs.getString(1) + "\t " +
							   rs.getString(2) + "\t " +
							   rs.getString(3));
		}
    	
    	rs.close();
    }
    
    
}

