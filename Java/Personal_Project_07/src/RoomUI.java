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
        String word = null;

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

        if(menu.equals("/noword")){
            String newWord;
            if(token.hasMoreTokens()){
                newWord = token.nextToken();
            } else{
                newWord = JOptionPane.showInputDialog("추가하고자 하는 금칙어를 입력해 주십시오 : ");
                if(newWord.length() == 0){
                    chatArea.append("금칙어로 추가할 단어를 입력해 주십시오.\n");
                    chatField.setText("");
                    return;
                }

            }

            client.getUser().getPersonalBadword().add(newWord);
            client.getUserBadword().add(newWord);

            chatArea.append(newWord + "(이)가 금칙어로 추가되었습니다..\n");
            chatField.setText("");

        }

        if(menu.equals("/yesword")){
            String newWord;
            boolean flag = false;
            if(client.getUserBadword().size() != 0 && client.getUser().getPersonalBadword().size() !=0){
                if(token.hasMoreTokens()){
                    newWord = token.nextToken();
                } else {
                    newWord = JOptionPane.showInputDialog("금칙어를 해제하고자 하는 단어를 입력해주십시오 : ");
                }

                for(int i = 0 ; i < client.getUserBadword().size() ; i++){
                    if(newWord.equals(client.getUserBadword().get(i))){
                        client.getUserBadword().remove(i);
                        flag = true;
                    }
                }

                for(int i = 0 ; i < client.getUser().getPersonalBadword().size(); i++){
                    if(newWord.equals(client.getUser().getPersonalBadword().get(i))){
                        client.getUser().getPersonalBadword().remove(i);
                        flag = true;
                    }
                }

                if(flag){
                    chatArea.append(newWord + "(은)는 더이상 금칙어가 아닙니다..\n");
                } else{
                    chatArea.append(newWord + "(은)는 금칙어가 아닙니다..\n");
                }

            } else{
                chatArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/nowordlist")){
            if(client.getUser().getPersonalBadword() != null){
                for(int i = 0; i < client.getUser().getPersonalBadword().size(); i++){
                    chatArea.append( (i + 1) + " : " + client.getUser().getPersonalBadword().get(i) + "\n");
                }
            } else{
                chatArea.append("금칙어 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/mute")){
            String id;
            if(token.hasMoreTokens()){
                id = token.nextToken();
            } else{
                id = JOptionPane.showInputDialog("차단하고자 하는 유저의 id를 입력해 주십시오 : ");

                if(id.length() == 0){
                    chatArea.append("id가 입력되지 않았습니다.\n");
                    chatField.setText("");
                    return;
                }
            }

            client.getUser().getPersonalMute().add(id);
            client.getUserMute().add(id);

            chatArea.append(id + "(이)가 차단되었습니다..\n");

            chatField.setText("");
        }

        if(menu.equals("/nomute")){
            String id;
            boolean flag= false;
            if(client.getUserMute().size() != 0 && client.getUser().getPersonalMute().size() != 0){
                if(token.hasMoreTokens()){
                    id = token.nextToken();

                } else{
                    id = JOptionPane.showInputDialog("차단하고자 하는 유저의 id를 입력해 주십시오 : ");

                }

                for(int i = 0; i < client.getUserMute().size() ; i++){
                    if(id.equals(client.getUserMute().get(i))){
                        client.getUserMute().remove(i);
                        flag = true;
                    }
                }

                for(int i = 0; i < client.getUser().getPersonalMute().size() ; i++){
                    if(id.equals(client.getUser().getPersonalMute().get(i))){
                        client.getUser().getPersonalMute().remove(i);
                        flag = true;
                    }
                }

                if(flag){
                    chatArea.append(id + "유저에 대한 차단이 해제되었습니다.\n");
                } else{
                    chatArea.append(id + "유저가 차단되어 있지 않습니다..\n");
                }
            } else{
                chatArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/mutelist")){
            if(client.getUser().getPersonalMute().size() != 0){
                for(int i = 0 ; i < client.getUser().getPersonalMute().size() ; i++){
                    chatArea.append( (i + 1) + " : " + client.getUser().getPersonalMute().get(i) + "\n");
                }
            } else{
                chatArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/badword")){
            if(client.user.getAdmin() == true){
                if(token.hasMoreTokens()){
                    word = token.nextToken();

                    try{
                        client.getDos().writeUTF(User.ADDBADWORD + "/" + word);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else{
                    word = JOptionPane.showInputDialog("추가할 금칙어를 입력해 주세요 : ");

                    if(word.length() != 0){
                        chatArea.append("금칙어로 추가할 단어를 입력해 주십시오.\n");
                        chatField.setText("");
                        return;
                    }

                    try{
                        client.getDos().writeUTF(User.ADDBADWORD + "/" + word);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                chatField.setText("");

            } else{
                chatArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
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

        if(menu.equals("/master") || menu.equals("/setmaster")){
            boolean flag = false;
            User temp = null;
            String id = null;
            if(client.user.equals(this.room.getMaster())){
                if(token.hasMoreTokens()){
                    id = token.nextToken();
                } else{
                    id = JOptionPane.showInputDialog("방장으로 지정할 아이디를 입력해 주십시오 : ");
                }
                try{
                    client.user.getDos().writeUTF(User.CHANGEMASTER + "/" + this.room.getRoomNum() + "/" + id);
                } catch (IOException e){
                    e.printStackTrace();
                }

                for(int i = 0 ; i < this.room.getUserArray().size() ; i++){
                    if(id.equals(this.room.getUserArray().get(i).getId())){
                        temp = this.room.getUserArray().get(i);
                        System.out.println(temp.toProtocol());
                        this.room.setMaster(temp);
                    }
                }

                for(int i = 0 ; i < client.getUser().getRoomArray().size() ; i++){
                    if(this.room.getRoomNum() == client.getUser().getRoomArray().get(i).getRoomNum()){
                        client.getUser().getRoomArray().get(i).setMaster(null);
                    }
                }


//                System.out.println(temp.toProtocol());

                chatField.setText("");
            } else{
                chatArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/kick")){
            String kickid;
            if(client.user.getAdmin() == true || client.user.equals(this.room.getMaster())){
                if(token.hasMoreTokens()){
                    kickid = token.nextToken();
                } else{
                    kickid = JOptionPane.showInputDialog("강퇴할 아이디를 입력해 주십시오 : ");

                    if(kickid.length() == 0){
                        chatArea.append("id가 입력되지 않았습니다.\n");
                        chatField.setText("");
                    }
                }

                try{
                    client.getUser().getDos().writeUTF(User.KICK +"/" + room.getRoomNum() + "/" + kickid);
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/block")){
            String blockId;
            if(client.user.getAdmin() == true || client.user.equals(this.room.getMaster())){
                if(token.hasMoreTokens()){
                    blockId = token.nextToken();
                } else{
                    blockId = JOptionPane.showInputDialog("강퇴할 아이디를 입력해 주십시오 : ");

                    if(blockId.length() == 0){
                        chatArea.append("id가 입력되지 않았습니다.\n");
                        chatField.setText("");
                    }
                }

                try{
                    client.getUser().getDos().writeUTF(User.BLOCK +"/" + room.getRoomNum() + "/" + blockId);
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/boom")){
            String rNum;
            if(client.user.equals(this.room.getMaster()) && !(client.user.getAdmin())){
                try{
                    client.getUser().getDos().writeUTF(User.BOOM +"/" + room.getRoomNum());
                } catch(IOException e){
                    e.printStackTrace();
                }
            } else if(client.user.getAdmin()){
                if(token.hasMoreTokens()){
                    rNum = token.nextToken();
                } else{
                    rNum = JOptionPane.showInputDialog("폭파시킬 방 번호를 입력해 주십시오 : ");

                    if(rNum.length() == 0){
                        chatArea.append("방 번호가 입력되지않았습니다..\n");
                        chatField.setText("");
                    }
                }

                try{
                    client.getUser().getDos().writeUTF(User.BOOM +"/" + rNum + "/" + room.getRoomNum());
                } catch(IOException e){
                    e.printStackTrace();
                }
            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
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

            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
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

            } else{
                chatArea.append("해당 명령어는 방장 혹은 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }
    }

    public Room getRoom() {
        return room;
    }
}