import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.StringTokenizer;

public class RoomUI extends JFrame {

    Client client;
    Room room;

    JTextArea chatArea;
    JTextField chatField;
    JList uList;
    DefaultListModel model;

    ImageIcon icon;

    public RoomUI(Client client, Room room) {
        this.client = client;
        this.room = room;
        setTitle("ChatRoom : " + room.toProtocol());
        icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());//타이틀바에 이미지넣기
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 502, 481);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        // 채팅 패널
        final JPanel panel = new JPanel();
        panel.setBounds(12, 10, 300, 358);
        getContentPane().add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        chatArea = new JTextArea();
        chatArea.setBackground(Color.WHITE);
        chatArea.setEditable(false); // 수정불가
        scrollPane.setViewportView(chatArea); // 화면 보임
        chatArea.append("매너 채팅 하세요!!\n");

        JPanel panel1 = new JPanel();
        // 글쓰는 패널
        panel1.setBounds(12, 378, 300, 34);
        getContentPane().add(panel1);
        panel1.setLayout(new BorderLayout(0, 0));

        chatField = new JTextField();
        panel1.add(chatField);
        chatField.setColumns(10);
        chatField.addKeyListener(new KeyAdapter() {
            // 엔터 버튼 이벤트
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    msgSummit();
                }
            }

        });

        // 참여자 패널
        JPanel panel2 = new JPanel();
        // 참여자 패널
        panel2.setBounds(324, 10, 150, 358);
        getContentPane().add(panel2);
        panel2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, BorderLayout.CENTER);

        uList = new JList(new DefaultListModel());
        model = (DefaultListModel) uList.getModel();
        scrollPane1.setViewportView(uList);

        // send button
        JButton roomSendBtn = new JButton("보내기");
        roomSendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgSummit();
                chatField.requestFocus();
            }
        });
        roomSendBtn.setBounds(324, 378, 75, 34);
        getContentPane().add(roomSendBtn);

        // exit button
        JButton roomExitBtn = new JButton("나가기");
        roomExitBtn.addMouseListener(new MouseAdapter() {		// 나가기 버튼
            @Override
            public void mouseClicked(MouseEvent e) {
                int ans = JOptionPane.showConfirmDialog(panel, "방을 나가시겠습니까?", "나가기", JOptionPane.OK_CANCEL_OPTION);

                if (ans == 0) { // 삭제
                    try {
                        client.getUser().getDos().writeUTF(User.GETOUT_ROOM + "/" + room.getRoomNum());
                        for (int i = 0; i < client.getUser().getRoomArray().size(); i++) {
                            if (client.getUser().getRoomArray().get(i).getRoomNum() == room.getRoomNum()) {
                                client.getUser().getRoomArray().remove(i);
                            }
                        }
                        setVisible(false);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else { // 안나감
//                    setVisible(false);
                }
            }
        });
        roomExitBtn.setBounds(400, 378, 75, 34);
        getContentPane().add(roomExitBtn);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        setVisible(true);

        chatField.requestFocus();
        this.addWindowListener(new WindowAdapter() {	// 윈도우 나가기
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.getUser().getDos().writeUTF(User.GETOUT_ROOM + "/" + room.getRoomNum());
                    for (int i = 0; i < client.getUser().getRoomArray().size(); i++) {
                        if (client.getUser().getRoomArray().get(i).getRoomNum() == room.getRoomNum()) {
                            client.getUser().getRoomArray().remove(i);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void msgSummit() {
        String string = chatField.getText();
        if (!string.equals("")) {
            if(string.substring(0,1).equals("/")){
                Commnadlist(string);
            } else{
                try {
                    // 채팅방에 메시지 보냄
                    client.getDos()
                            .writeUTF(User.ECHO02 + "/" + room.getRoomNum() + "/" + client.getUser().toString() + string);
                    chatField.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void Commnadlist(String str){
        StringTokenizer token = new StringTokenizer(str, " "); // 토큰 생성
        String menu = token.nextToken();

        if(menu.equals("/to") || menu.equals("/whisper")){
            String id = token.nextToken(); // 토큰으로 분리된 스트링
            String msg = token.nextToken();

            try {
                client.getDos().writeUTF( User.WHISPER  +"/"+ id + "/" + msg);
                chatArea.append(id + "님에게 귓속말 : " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatField.setText("");
        }

        if(menu.equals("/keepwhisper")){
            String id = token.nextToken(); // 토큰으로 분리된 스트링
            String msg = token.nextToken();

            try {
                client.getDos().writeUTF( User.WHISPER  +"/"+ id + "/" + msg);
                chatArea.append(id + "님에게 귓속말 : " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatField.setText("/keepwhisper " + id + " ");
        }

        if(menu.equals("/nick")){
            String nickname = token.nextToken();

            try{
                client.getDos().writeUTF( User.CHANGENICKNAME + "/room/" + nickname);
                chatArea.append("닉네임 " + nickname + "으로 변경요청.\n");
            } catch (IOException e){
                e.printStackTrace();
            }
            chatField.setText("");

        }


        if (menu.equals("/monitor")){
            if(client.user.getAdmin() == true){
                String rNum = JOptionPane.showInputDialog("모니터할 방 번호를 입력해 주십시오 : ");
                try{
                    client.getDos().writeUTF( User.MONITOR + "/" + rNum);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");
            } else{
                chatArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/ban")){
            String id;

            if(client.user.getAdmin() == true){
                id = JOptionPane.showInputDialog("밴할 아이디를 입력해 주십시오 : ");

                try{
                    client.getDos().writeUTF(User.BAN + "/" + id);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");

            } else{
                chatArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }

        }

        if(menu.equals("/roommax") || menu.equals("/max")){
            if(client.user.getAdmin() == true || client.user.equals(this.room.getMaster())){
                String rNum = Integer.toString(this.room.getRoomNum());
                String max = null;

                if(token.hasMoreTokens()){
                    max =  token.nextToken();
                    if(Integer.parseInt(max) > 1 && Integer.parseInt(max) > 20){
                        try{
                            client.getDos().writeUTF( User.CHANGEMAX + "/" + rNum + "/"+ max);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    room.setRoomMax(Integer.parseInt(max));
                    chatField.setText("");
                    return;
                }

                while(true){
                    if(max == null){
                        max = JOptionPane.showInputDialog("변경하고자 하는 최대 인원을 입력해 주십시오(최소 2명, 최대 20명) : ");
                    } else{
                        max = JOptionPane.showInputDialog("잘못된 범위 입니다 다시 입력해 주십시오(최소 2명, 최대 20명) : ");
                    }

                    if(Integer.parseInt(max) > 1 && Integer.parseInt(max) < 20){
                        break;
                    }
                }

                try{
                    client.getDos().writeUTF( User.CHANGEMAX + "/" + rNum + "/"+ max);
                } catch (IOException e){
                    e.printStackTrace();
                }

                room.setRoomMax(Integer.parseInt(max));
                chatField.setText("");

            }
        }

        if(menu.equals("/invite")){
            String rNum = Integer.toString(room.getRoomNum());
            String id = null;

            if(client.user.getAdmin() == true || client.user.equals(this.room.getMaster())){
                if(token.hasMoreTokens()){
                    id = token.nextToken();
                }
                else{
                    id = JOptionPane.showInputDialog("초대할 유저의 아이디를 적어주십시오 : ");
                }

                try{
                    client.getDos().writeUTF(User.INVITE + "/" + rNum + "/" + id);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");

            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/announce") || menu.equals("/an")){
            if(client.user.getAdmin() == true){
                String msg = JOptionPane.showInputDialog("공지할 내용을 적어주십시오 : ");
                try{
                    client.getDos().writeUTF( User.ANNOUNCE + "/" + msg);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");
            }else{
                chatArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/announceroom") || menu.equals("/anr")){
            if(client.user.getAdmin() == true || client.user.equals(this.room.getMaster())){
                String rNum = Integer.toString(this.room.getRoomNum());
                String msg = JOptionPane.showInputDialog("공지할 내용을 적어주십시오 : ");
                try{
                    client.getDos().writeUTF( User.ANNOUNCEROOM + "/" + rNum + "/"+ msg);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");

            }
        }
    }
}