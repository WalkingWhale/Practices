import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WaitRoomUI extends JFrame implements ActionListener {

    MsgeBox msgbox = new MsgeBox();
    String temp,id;

    int lastRoomNum = 100;
    JButton makeRoomBtn, getInRoomBtn, whisperBtn, sendBtn;
    JTree userTree;
    JList roomList;
    JTextField chatField;
    JTextArea waitRoomArea;
    JLabel lbid, lbnick;
    JTextField lbip;

    Client client;
    ArrayList<User> userArray; // 사용자 목록 배열
    String currentSelectedTreeNode;
    DefaultListModel model;
    DefaultMutableTreeNode level1;
    DefaultMutableTreeNode level2;
    DefaultMutableTreeNode level3;

    JScrollPane scrollPane;
    ImageIcon icon;
    LoginUI login;

    public WaitRoomUI(Client Client) {
        setTitle("Chatting");
        userArray = new ArrayList<User>();
        this.client = Client;
        initialize();
    }

    private void initialize() {

        icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());//타이틀바에 이미지넣기

        setBounds(100, 100, 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu basicMenus = new JMenu("파일");
        basicMenus.addActionListener(this);
        menuBar.add(basicMenus);

        JMenuItem exitItem = new JMenuItem("종료");
        exitItem.addActionListener(this);
        basicMenus.add(exitItem);

        JMenu updndel = new JMenu("변경/탈퇴");
        updndel.addActionListener(this);
        menuBar.add(updndel);

        JMenuItem changeInfo = new JMenuItem("비밀번호 변경");
        changeInfo.addActionListener(this);
        updndel.add(changeInfo);

        JMenuItem withdrawMem = new JMenuItem("회원 탈퇴");
        withdrawMem.addActionListener(this);
        updndel.add(withdrawMem);

        JMenu helpMenus = new JMenu("도움말");
        helpMenus.addActionListener(this);
        menuBar.add(helpMenus);

        JMenuItem proInfoItem = new JMenuItem("프로그램 정보");
        proInfoItem.addActionListener(this);
        helpMenus.add(proInfoItem);
        getContentPane().setLayout(null);

        JPanel room = new JPanel();
        room.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "채 팅 방", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        room.setBounds(12, 10, 477, 215);
        getContentPane().add(room);
        room.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        room.add(scrollPane, BorderLayout.CENTER);

        // 리스트 객체와 모델 생성
        roomList = new JList(new DefaultListModel());
        roomList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = roomList.getFirstVisibleIndex();
                // System.out.println(">>>>>>>>>>>" + i);
                if (i != -1) {
                    // 채팅방 목록 중 하나를 선택한 경우,
                    // 선택한 방의 방번호를 전송
                    String temp = (String) roomList.getSelectedValue();
                    if(temp.equals(null)){
                        return;
                    }

                    try {
                        client.getUser().getDos().writeUTF(User.UPDATE_SELECTEDROOM_USERLIST + "/" + temp.substring(0, 3));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        model = (DefaultListModel) roomList.getModel();
        scrollPane.setViewportView(roomList);

        JPanel panel2 = new JPanel();
        room.add(panel2, BorderLayout.SOUTH);

        makeRoomBtn = new JButton("방 만들기");
        makeRoomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        makeRoomBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // 방만들기 버튼 클릭
                createRoom();
            }
        });
        panel2.setLayout(new GridLayout(0, 2, 0, 0));
        panel2.add(makeRoomBtn);

        getInRoomBtn = new JButton("방 입장하기");
        getInRoomBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // 방 들어가기
                getIn();
            }
        });
        panel2.add(getInRoomBtn);

        JPanel user = new JPanel();
        user.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),	"사용자 목록", TitledBorder.CENTER,	TitledBorder.TOP, null, null));
        user.setBounds(501, 10, 171, 409);
        getContentPane().add(user);
        user.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane1 = new JScrollPane();
        user.add(scrollPane1, BorderLayout.CENTER);

        // 사용자목록, 트리구조
        userTree = new JTree();
        userTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                currentSelectedTreeNode = e.getPath().getLastPathComponent().toString();
            }
        });
        level1 = new DefaultMutableTreeNode("참여자");
        level2 = new DefaultMutableTreeNode("채팅방");
        level3 = new DefaultMutableTreeNode("대기실");
        level1.add(level2);
        level1.add(level3);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(new ImageIcon("user.png"));
        renderer.setClosedIcon(new ImageIcon("wait.png"));
        renderer.setOpenIcon(new ImageIcon("open.png"));

        userTree.setCellRenderer(renderer);
        userTree.setEditable(false);

        DefaultTreeModel model = new DefaultTreeModel(level1);
        userTree.setModel(model);

        scrollPane1.setViewportView(userTree);

        JPanel panel1 = new JPanel();
        user.add(panel1, BorderLayout.SOUTH);
        panel1.setLayout(new GridLayout(1, 0, 0, 0));

        whisperBtn = new JButton("귓속말");

        whisperBtn.addActionListener(this);
        panel1.add(whisperBtn);

        JPanel waitroom = new JPanel();

        waitroom.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "대 기 실",	TitledBorder.CENTER, TitledBorder.TOP, null, Color.DARK_GRAY));
        waitroom.setBounds(12, 235, 477, 185);
        getContentPane().add(waitroom);
        waitroom.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        waitroom.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JScrollPane scrollPane4 = new JScrollPane();
        panel.add(scrollPane4);

        chatField = new JTextField();

        chatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    msgSummit();
                }
            }

        });
        scrollPane4.setViewportView(chatField);
        chatField.setColumns(10);

        sendBtn = new JButton("보내기");
        sendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msgSummit();
                chatField.requestFocus();
            }
        });
        panel.add(sendBtn);

        JScrollPane scrollPane2 = new JScrollPane();
        waitroom.add(scrollPane2, BorderLayout.CENTER);

        waitRoomArea = new JTextArea();
        waitRoomArea.setEditable(false);
        scrollPane2.setViewportView(waitRoomArea);

        JPanel info = new JPanel();
        lbid = new JLabel("-");
        info.add(lbid);
        lbnick = new JLabel("-");
        info.add(lbnick);
        lbip = new JTextField();
        lbip.setEditable(false);
        info.add(lbip);
        lbip.setColumns(10);

        chatField.requestFocus();
        setVisible(true);
        chatField.requestFocus();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit01();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        id = client.user.getId();
        switch (e.getActionCommand()) {
            case "귓속말":
                // 닉네임 제외하고 아이디만 따옴 <- 닉네임은 중복가능 id는 unique 하기때문에 id로 귓속말을 보내는게 낫다고생각함
                StringTokenizer token = new StringTokenizer(currentSelectedTreeNode, "("); // 토큰 생성
                temp = token.nextToken(); // 토큰으로 분리된 스트링
                temp = token.nextToken();
                id = "/to " + temp.substring(0, temp.length() - 1) + " ";
                chatField.setText(id);
                chatField.requestFocus();
                break;
            // 메뉴1 파일 메뉴
            case "비밀번호 변경":
                ModifyUI reDB = new ModifyUI();
                reDB.userInfo(client);
                break;
            case "회원 탈퇴":
                int ans = JOptionPane.showConfirmDialog(this, "정말 탈퇴 하시겠습니까?", "탈퇴확인", JOptionPane.OK_CANCEL_OPTION);

                if( ans == 0){
                    try{
                        client.getDos().writeUTF(User.CLOSEACCOUNT + "/" + id);
                    } catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
                /*DBDelete delDB = new DBDelete();

                int ans = JOptionPane.showConfirmDialog(this, "정말 탈퇴 하시겠습니까?", "탈퇴확인", JOptionPane.OK_CANCEL_OPTION);

                if (ans == 0) {
                    int i = 0;
                    i = delDB.InfoDel(id);
                    if (i == 0) {
                        // msgbox.messageBox(this, "탈퇴는 신중히..:)");
                    } else {
                        msgbox.messageBox(this, "탈퇴 성공하였습니다..:(");
                        exit01();
                    }
                }*/
                break;
            case "종료":
                int ans1 = JOptionPane.showConfirmDialog(this, "정말 종료 하시겠습니까?", "종료확인", JOptionPane.OK_CANCEL_OPTION);
                if (ans1 == 0) {
                    // System.exit(0); // 강제 종료
                    try {
                        client.getUser().getDos().writeUTF(User.LOGOUT);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                break;
            case "프로그램 정보":
                maker();
                break;
        }

    }

    private void msgSummit() {
        String string = chatField.getText();// 메시지전송
        if (!string.equals("")) {
            if (string.substring(0, 1).equals("/")) {
                Commnadlist(string);
                /*StringTokenizer token = new StringTokenizer(string, " "); // 토큰 생성
                String id = token.nextToken(); // 토큰으로 분리된 스트링
                String msg = token.nextToken();

                try {
                    client.getDos().writeUTF(User.WHISPER + id + "/" + msg);
                    waitRoomArea.append(id + "님에게 귓속말 : " + msg + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chatField.setText("");*/
            } else {

                try {
                    // 대기실에 메시지 보냄
                    client.getDos().writeUTF(User.ECHO01 + "/" + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chatField.setText("");
            }
        }
    }

    private void Commnadlist(String str){
        StringTokenizer token = new StringTokenizer(str, " "); // 토큰 생성
        String menu = token.nextToken();
        String word;

        if(menu.equals("/noword")){
            String newWord;

            if(token.hasMoreTokens()){
                newWord = token.nextToken();
            } else{
                newWord = JOptionPane.showInputDialog("추가하고자 하는 금칙어를 입력해 주십시오 : ");
                if(newWord.length() == 0){
                    waitRoomArea.append("금칙어로 추가할 단어를 입력해 주십시오.\n");
                    chatField.setText("");
                    return;
                }

            }

            if(client.getUserBadword().size() != 0 && client.getUser().getPersonalBadword().size() !=0){
                for(int i = 0 ; i < client.getUserBadword().size(); i++){
                    if(newWord.equals(client.getUserBadword().get(i))){
                        waitRoomArea.append("중복된 금칙어입니다..\n");
                        chatField.setText("");
                        return;
                    }
                }
            }

            client.getUser().getPersonalBadword().add(newWord);
            client.getUserBadword().add(newWord);

            waitRoomArea.append(newWord + "(이)가 금칙어로 추가되었습니다..\n");
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
                    waitRoomArea.append(newWord + "(은)는 더이상 금칙어가 아닙니다..\n");
                } else{
                    waitRoomArea.append(newWord + "(은)는 금칙어가 아닙니다..\n");
                }

            } else{
                waitRoomArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/nowordlist")){
            if(client.getUser().getPersonalBadword().size() != 0){
                for(int i = 0; i < client.getUser().getPersonalBadword().size(); i++){
                    waitRoomArea.append( (i + 1) + " : " + client.getUser().getPersonalBadword().get(i) + "\n");
                }
            } else{
                waitRoomArea.append("금칙어 목록이 비어있습니다.\n");
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
                    waitRoomArea.append("id가 입력되지 않았습니다.\n");
                    chatField.setText("");
                    return;
                }

            }
            if(client.getUserMute().size() != 0){
                for(int i = 0 ; i < client.getUserMute().size() ; i++){
                    if(id.equals(client.getUserMute().get(i))){
                        waitRoomArea.append("이미 차단되어 있는 유저입니다..\n");
                        chatField.setText("");
                        return;
                    }
                }
            }


            client.getUser().getPersonalMute().add(id);
            client.getUserMute().add(id);
            waitRoomArea.append(id + "(이)가 차단되었습니다..\n");

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
                    waitRoomArea.append(id + "유저에 대한 차단이 해제되었습니다.\n");
                } else{
                    waitRoomArea.append(id + "유저가 차단되어 있지 않습니다..\n");
                }
            } else{
                waitRoomArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/mutelist")){
            if(client.getUser().getPersonalMute().size() != 0){
                for(int i = 0 ; i < client.getUser().getPersonalMute().size() ; i++){
                    waitRoomArea.append( (i + 1) + " : " + client.getUser().getPersonalMute().get(i) + "\n");
                }
            } else{
                waitRoomArea.append("차단 목록이 비어있습니다.\n");
            }

            chatField.setText("");
        }

        if(menu.equals("/badword")){
            String newWord;
            if(client.user.getAdmin() == true){
                if(token.hasMoreTokens()){
                    newWord = token.nextToken();
                } else{
                    newWord = JOptionPane.showInputDialog("추가할 금칙어를 입력해 주세요 : ");

                    if(newWord.length() != 0){
                        waitRoomArea.append("금칙어로 추가할 단어를 입력해 주십시오.\n");
                        chatField.setText("");
                        return;
                    }


                }

                try{
                    client.getDos().writeUTF(User.ADDBADWORD + "/" + newWord);
                } catch (IOException e){
                    e.printStackTrace();
                }

                chatField.setText("");

            } else{
                waitRoomArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/to") || menu.equals("/whisper")){
            String id = token.nextToken(); // 토큰으로 분리된 스트링
            String msg = token.nextToken();

            try {
                client.getDos().writeUTF( User.WHISPER  + "/" + id + "/" + msg);
                waitRoomArea.append(id + "님에게 귓속말 : " + msg + "\n");
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
                waitRoomArea.append(id + "님에게 귓속말 : " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            chatField.setText("/keepwhisper " + id + " ");
        }

        if(menu.equals("/nick")){
            String nickname = token.nextToken();

            try{
                client.getDos().writeUTF( User.CHANGENICKNAME + "/wait/" + nickname);
                waitRoomArea.append("닉네임 " + nickname + "으로 변경요청.\n");
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
                waitRoomArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
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
                waitRoomArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/announcetoroom") || menu.equals("/antr")){
            if(client.user.getAdmin() == true){
                String rNum = JOptionPane.showInputDialog("공지할 방 번호를 적어주십시오 : ");
                if(rNum.length() == 0){
                    waitRoomArea.append("공지할 방을 확인해 주세요");
                    chatField.setText("");
                } else{
                    String msg = JOptionPane.showInputDialog("공지할 내용을 적어주십시오 : ");
                    try{
                        client.getDos().writeUTF( User.ANNOUNCEROOM + "/" + rNum + "/"+ msg);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    chatField.setText("");
                }

            } else{
                waitRoomArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }

        if(menu.equals("/ban")){
            if(client.user.getAdmin() == true){
                String id = JOptionPane.showInputDialog("밴시킬 아이디를 적어주세요 : ");
                String really = JOptionPane.showInputDialog("정말로 밴하시겠습니까? (Y/N) : ");
                if(really.equals("y")|| really.equals("Y")){
                    try{
                        client.getDos().writeUTF(User.BAN + "/" + id);
                        waitRoomArea.append(id + "유저를 밴하겠습니다.\n");
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }

                chatField.setText("");
            }

            else{
                waitRoomArea.append("해당 명령어는 관리자만 사용할 수 있습니다.\n");
                chatField.setText("");
            }
        }
    }

    private void exit01() {
        try {
            client.getUser().getDos().writeUTF(User.LOGOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createRoom() {
        String roomname = JOptionPane.showInputDialog("대화방 이름을 입력하세요");
        String flag = JOptionPane.showInputDialog("비공개 방으로 만드시겠습니까? (Y/N)");
        if(roomname==null) {	// 취소 버튼

        } else {
            if(flag.equals("y") || flag.equals("Y")||flag.equals("yes")){
                String roompw = JOptionPane.showInputDialog("비밀번호를 입력하세요");
                Room newRoom = new Room(roomname,roompw);	// 방 객체 생성
                newRoom.setRoomNum(lastRoomNum);
                newRoom.setrUI(new RoomUI(client, newRoom));
                client.getUser().getUserroomsUI().add(newRoom.getrUI());
                newRoom.setPriv(true);
                newRoom.setMaster(client.getUser());    // 방장 설정
                // 클라이언트가 접속한 방 목록에 추가
                client.getUser().getRoomArray().add(newRoom);

                try {
                    client.getDos().writeUTF(User.CREATE_ROOM_PRIVATE + "/" + newRoom.toProtocolPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else{
                Room newRoom = new Room(roomname);	// 방 객체 생성
                newRoom.setRoomNum(lastRoomNum);
                newRoom.setrUI(new RoomUI(client, newRoom));
                client.getUser().getUserroomsUI().add(newRoom.getrUI());
                newRoom.setMaster(client.getUser());    // 방장 설정

                // 클라이언트가 접속한 방 목록에 추가
                client.getUser().getRoomArray().add(newRoom);

                try {
                    client.getDos().writeUTF(User.CREATE_ROOM + "/" + newRoom.toProtocol());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void getIn() {
        String selectedRoom = (String) roomList.getSelectedValue();
        StringTokenizer token = new StringTokenizer(selectedRoom, "/"); // 토큰 생성
        String rNum = token.nextToken();

        try {
            client.getDos().writeUTF(User.TRY_GETIN_ROOM + "/" + rNum);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void maker() {
        JDialog maker = new JDialog();
        Maker m = new Maker();
        maker.setTitle("프로그램 정보");
        maker.getContentPane().add(m);
        maker.setSize(400, 170);
        maker.setVisible(true);
        maker.setLocation(400, 350);
        maker.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}

class Maker extends JPanel {
    public Maker() {
        super();
        initialize();
    }

    private void initialize() {
        this.setLayout(new GridLayout(3, 1));

        JLabel j1 = new JLabel("       프로그램 제작자 : 그저 빛나는 구선생님의 여러분들		");
        JLabel j2 = new JLabel("       수정한 사람 : 한태준		");

        this.add(j1);
        this.add(j2);
    }
}
