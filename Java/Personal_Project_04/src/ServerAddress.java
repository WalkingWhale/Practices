import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerAddress extends JFrame {

    JButton confirmBtn;
    JTextField ipText;
    LoginUI loginUI;
    ImageIcon icon;

    public ServerAddress(LoginUI loginUI) {
        this.loginUI = loginUI;
        initialize();
    }

    private void initialize() {

        icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());//타이틀바에 이미지넣기

        setTitle("서버 아이피 주소 입력");
        setBounds(100, 100, 306, 95);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 10, 266, 37);
        getContentPane().add(panel);
        panel.setLayout(new BorderLayout(0, 0)); // 텍스트 크기

        ipText = new JTextField();
        ipText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginUI.ipBtn.setText(ipText.getText());
                    loginUI.setIp(ipText.getText());
                    loginUI.setVisible(true);
                    dispose();
                    loginUI.idText.requestFocus();
                }
            }
        });
        ipText.setText("192.168.219.130");
        panel.add(ipText, BorderLayout.CENTER);
        ipText.setColumns(10);

        confirmBtn = new JButton("확인");
        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginUI.ipBtn.setText(ipText.getText());
                loginUI.setIp(ipText.getText());
                loginUI.setVisible(true);
                dispose();
                loginUI.idText.requestFocus();
            }
        });
        panel.add(confirmBtn, BorderLayout.EAST);
        setVisible(true);
    }

}