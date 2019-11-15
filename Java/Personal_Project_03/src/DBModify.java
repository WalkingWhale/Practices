import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Properties;

public class DBModify implements MouseListener {

    String id = null;
    String pw;

    JFrame frame;
    JPanel logPanel;
    JPanel logPanel1;
    JPanel logPanel2;
    JPanel logPanel3;
    JTextField idTf, pwTf= null;
    JButton okBtn;

    MsgeBox msgbox = new MsgeBox();

    Statement stmt = null;
    ResultSet rs = null;
    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    String sql = null;
    String sql2 = null;
    Properties info = null;
    Connection cnn = null;

    // id를 받아와서 그것의 정보로 pw/name/barth 수정및 삭제
    void myInfo(String id) {
        this.id = id;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 conn으로 연결
            info = new Properties();
            info.setProperty("user", "scott");
            info.setProperty("password", "tiger");
            cnn = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = cnn.createStatement();

            sql = "select * from userinfo where id='" + id + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next() == true) { // 다음값의
                pw = rs.getString(2);
            }
        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }

        frame = new JFrame("비밀번호수정");
        logPanel = new JPanel();
        logPanel1 = new JPanel(new GridLayout(4, 1));
        logPanel2 = new JPanel(new GridLayout(4, 1));
        logPanel3 = new JPanel();

        JLabel idLabel = new JLabel(" I   D   ", JLabel.CENTER);
        JLabel pwLabel = new JLabel(" P   W   ", JLabel.CENTER);
        logPanel1.add(idLabel);
        logPanel1.add(pwLabel);

        idTf = new JTextField(20);
        idTf.setText(id);
        idTf.setEditable(false);
        pwTf = new JTextField(20);
        pwTf.setText(pw);
        logPanel2.add(idTf);
        logPanel2.add(pwTf);

        frame.add(logPanel, BorderLayout.NORTH);
        frame.add(logPanel1, BorderLayout.WEST);
        frame.add(logPanel2, BorderLayout.CENTER);
        frame.add(logPanel3, BorderLayout.EAST);

        JPanel logPanel4 = new JPanel();
        JLabel askLabel = new JLabel("변경하시겠습니까?");
        okBtn = new JButton("확인");
        JButton cancleBtn = new JButton("취소");
        okBtn.addMouseListener(this); 		// addMouseListener이벤트
        logPanel4.add(askLabel);
        logPanel4.add(okBtn);
        logPanel4.add(cancleBtn);
        frame.add(logPanel4, BorderLayout.SOUTH);

        // 취소 버튼
        cancleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                dbClose();
            }
        });

        frame.setBounds(450, 250, 350, 200);
        frame.setResizable(false);
        frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            // 확인 버튼
            if (e.getSource().equals(okBtn)) {
                if ((idTf.getText().isEmpty()) == true || (pwTf.getText().isEmpty()) == true) {
                    msgbox.messageBox(logPanel3, "비어있는 칸이 존재합니다.");
                } else {
                    sql = "update userinfo set password = '" + pwTf.getText() + "' where id = '" + id + "'";
                    //System.out.println(sql);
                    stmt.executeUpdate(sql);
                    msgbox.messageBox(logPanel3, "변경 되셨습니다.");
                    frame.dispose(); // 창 닫기
                    dbClose();
                }
            }

        } catch (Exception ee) {
            System.out.println("문제있음");
            ee.printStackTrace();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void dbClose() {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (cnn != null)
                cnn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}

