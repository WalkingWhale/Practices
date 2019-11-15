import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Client implements Runnable {

    static int PORT = 9999;     // 서버포트번호
    static String IP = null;    // 서버아이피주소
    Socket socket; // 소켓
    User user; // 사용자

    LoginUI login;
    WaitRoomUI waitRoom;
    ArrayList<String> userBadword;      // 클라이언트 사용자가 금칙어로 설정한 단어들
    ArrayList<String> userMute;         // 클라이언트 사용자가 차단한 user id
    DataInputStream dis = null;
    DataOutputStream dos = null;
    boolean ready = false;

    Client() {
        login = new LoginUI(this);
//        IP = login.getIp(); // login UI -> ServerAddress에서 입력된 ip 저장
        userMute = new ArrayList<String>();
        userBadword = new ArrayList<String>();
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {
        System.out.println("Client start");
        new Client();

    } // main end



    @Override
    public void run() {
        //
        // 소켓 통신 시작
        //
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 사용자가 객체 생성 및 아이피설정
        user = new User(dis, dos);
        user.setIP(socket.getInetAddress().getHostAddress());

        // 메시지 리딩
        while (true) {
            try {
                String receivedMsg = dis.readUTF(); // 메시지 받기(대기)
                dataParsing(receivedMsg); // 메시지 해석
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    user.getDis().close();
                    user.getDos().close();
                    socket.close();
                    break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        alarmMsg("서버프로그램이 먼저 종료되었습니다.");
        // 채팅프로그램 종료
        waitRoom.dispose();
    }

    public boolean serverAccess() {
        if (!ready) {
            // 소켓이 연결이 이루어지지 않은 경우에만 실행
            // 처음 연결시에만 실행
            socket = null;
            IP = login.ipBtn.getText();
            try {
                // 서버접속
                InetSocketAddress inetSockAddr = new InetSocketAddress(InetAddress.getByName(IP), PORT);
                socket = new Socket();

                // 지정된 주소로 접속 시도 (3초동안)
                socket.connect(inetSockAddr, 3000);
            } catch (UnknownHostException e) {
                System.out.println("잘못된 ip주소입니다. 다시한번 확인해주세요");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 접속이 되면 실행
            if (socket.isBound()) {
                // 입력, 출력 스트림 생성
                try {
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    userBadword = new ArrayList<String>();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ready = true;
            }
        }
        return ready;
    }

    // 데이터를 구분
    public synchronized void dataParsing(String data) {

        StringTokenizer token = new StringTokenizer(data, "/"); // 토큰 생성
        StringTokenizer msgToken;
        String protocol = token.nextToken(); // 토큰으로 분리된 스트링
        String id, pw, rNum, nick, rName, msg, msgCensored ,result;
        String roomType;
        System.out.println("받은 데이터 : " + data);

        switch (protocol) {
            case User.LOGIN: // 로그인
                // 사용자가 입력한(전송한) 아이디와 패스워드
                result = token.nextToken();
                if (result.equals("OK")) {
                    alarmMsg("로그인에 성공했습니다!");
                    nick = token.nextToken();
                    login(nick);
                } else {
                    msg = token.nextToken();
                    alarmMsg(msg);
                }
                break;
            case User.LOGOUT:
                logout();
                break;
            case User.MEMBERSHIP: // 회원가입 승인
                result = token.nextToken();
                break;
            case User.GETIN_ROOM:   // 공개방 입장
                getInRoom(token);
                break;
            case User.TRY_GETIN_PRIVATE:
                rNum = token.nextToken();
                tryIn_Private(rNum);
                break;
            case User.GETOUT_ROOM:
                rNum = token.nextToken();
                getOutRoom(rNum);
                break;
            case User.GETIN_ROOM_FAIL:
                msg = token.nextToken();
                alarmMsg(msg);
                break;
            case User.UPDATE_USERLIST: // 대기실 사용자 목록
                userList(token);
                break;
            case User.UPDATE_ROOM_USERLIST: // 채팅방 사용자 목록
                // 방번호읽기
                rNum = token.nextToken();
                userList(rNum, token);
                break;
            case User.UPDATE_SELECTEDROOM_USERLIST: // 대기실에서 선택한 채팅방의 사용자 목록
                selectedRoomUserList(token);
                break;
            case User.UPDATE_ROOMLIST: // 방 목록
                roomList(token);
                break;
            case User.ECHO01: // 대기실 에코
                msg = token.nextToken();
                if(muteUserCheck(msg)){
                    msgCensored = censor(msg);
                    echoMsg(msgCensored);
                }
                break;
            case User.ECHO02: // 채팅방 에코
                rNum = token.nextToken();
                msg = token.nextToken();
                if(muteUserCheck(msg)){
                    msgCensored = censor(msg);
                    echoMsgToRoom(rNum, msgCensored);
                }
                break;
            case User.CHANGENICKNAME:
                nick = token.nextToken();
                user.setNickName(nick);
//                waitRoom.waitRoomArea.append("닉네임 변경완료\n");
                break;
            case User.MONITOR:
                monitor(token);
                break;
            case User.ANNOUNCE:
                msg = token.nextToken();
                announce(msg);
                break;
            case User.ANNOUNCEROOM:
                rNum = token.nextToken();
                msg = token.nextToken();
                announceRoom(rNum, msg);
                break;
            case User.ALARM:
                msg = token.nextToken();
                alarmMsg(msg);
                break;
            case User.BAN:
                try{
                    this.getDos().writeUTF(User.LOGOUT);
//                    wait(1000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                logout();
                break;
            case User.INVITEYESNO:
                rNum = token.nextToken();
                id = token.nextToken();
                invite(rNum, id);
                break;
            case User.CHANGEMASTER:
                rNum = token.nextToken();
                changeMaster(rNum);
                break;

        }
    }

    private boolean muteUserCheck(String msg){
        StringTokenizer tokenizer = new StringTokenizer(msg, "(");
        tokenizer.nextToken();
        String temp = tokenizer.nextToken(")");
        //System.out.println(temp.substring(1));      // 이렇게 하면 id 검출 가능
        temp = temp.substring(1);
        if(this.getUserMute().size() !=0){
            for(int i = 0; i < this.getUserMute().size() ; i++){
                //System.out.println(this.getUserMute().get(i));
                if(temp.equals(this.getUserMute().get(i))){
                    System.out.println("차단 성공");
                    return false;
                }
            }
        }

        return true;
    }

    private void changeMaster(String rNum){
        for(int i = 0 ; i < this.getUser().getRoomArray().size() ; i++){
            if(rNum.equals(Integer.toString(this.getUser().getRoomArray().get(i).getRoomNum()))){
                this.getUser().getRoomArray().get(i).setMaster(this.getUser());
                String msg = rNum + "번 방의 방장으로 임명되셨습니다.";
                echoMsgToRoom(rNum, msg);
            }
        }
    }


    private void invite(String rNum, String id){
        int ans = JOptionPane.showConfirmDialog(null, rNum + "방에 초대되셨습니다 입장하시겠습니까?", "초대", JOptionPane.OK_CANCEL_OPTION);
        if(ans == 0){
            try{
                this.getDos().writeUTF(User.INVITEYESNO +"/yes/"+ rNum + "/" + id);
            } catch(IOException e){
                e.printStackTrace();
            }

        } else{
            try{
                this.getDos().writeUTF(User.INVITEYESNO + "/no/"+rNum+"/"+id);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void logout() {
        try {
            alarmMsg("채팅 프로그램을 종료합니다!");
            if(this.getUser().getUserroomsUI().size() != 0){
                for(int i = 0; i < this.getUser().getUserroomsUI().size(); i++){
                    this.getUser().getUserroomsUI().get(i).setVisible(false);
                }
            }
            waitRoom.dispose();
            user.getDis().close();
            user.getDos().close();
            socket.close();
            waitRoom = null;
            user = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 모니터 창 생성
    private void monitor(StringTokenizer token){
        String rNum, rName;
        rNum = token.nextToken();
        rName = token.nextToken();

        Room theRoom = new Room(rName); // 방 객체 생성
        theRoom.setRoomNum(Integer.parseInt(rNum)); // 방번호 설정
        theRoom.setrUI(new RoomUI(this, theRoom)); // UI
        theRoom.setMonitoring(true);    //모니터링 방으로 설정

        this.getUser().getRoomArray().add(theRoom);
        // 서버에 방에 접속된 인원 업데이트 요청
        try{
            this.getDos().writeUTF(User.UPDATE_ROOM_USERLIST + "/" + rNum);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    // 공개체팅방  입장
    private void getInRoom(StringTokenizer token){
        String rNum, rName, result;
        result = token.nextToken();
        rNum = token.nextToken();
        rName = token.nextToken();

        if(result.equals("OK")){
            Room theRoom = new Room(rName); // 방 객체 생성
            theRoom.setRoomNum(Integer.parseInt(rNum)); // 방번호 설정
            theRoom.setrUI(new RoomUI(this, theRoom)); // UI
            this.getUser().getUserroomsUI().add(theRoom.getrUI());
            this.user.setInRoom(true);
            // 클라이언트가 접속한 방 목록에 추가
            this.getUser().getRoomArray().add(theRoom);
            // 서버에 방에 접속된 인원 업데이트 요청
            try{
                this.getDos().writeUTF(User.UPDATE_ROOM_USERLIST + "/" + rNum);
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private void getOutRoom(String rNum){
        for (int i = 0; i < this.getUser().getRoomArray().size(); i++) {
            if ( rNum.equals(Integer.toString(this.getUser().getRoomArray().get(i).getRoomNum()))) {

                for(int j = 0 ; j < this.getUser().getUserroomsUI().size() ; j++){
                    if(this.user.getRoomArray().get(i).getRoomNum() == this.user.getUserroomsUI().get(j).getRoom().getRoomNum()){

//                        System.out.println("여기가 문제인것같은데");
                        this.getUser().getUserroomsUI().get(j).setVisible(false);
                        this.getUser().getUserroomsUI().remove(j);
                        this.getUser().getRoomArray().remove(i);

                        try {
                            this.getUser().getDos().writeUTF(User.GETOUT_ROOM + "/" + rNum);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    private void tryIn_Private(String rNum){
        String roomPw = JOptionPane.showInputDialog("비밀번호를 입력하세요");
        try {
            this.getDos().writeUTF(User.GETIN_ROOM_PRIVATE + "/" +rNum + "/" + roomPw );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 채팅방 내부 사용자 리스트
    private void userList(String rNum, StringTokenizer token) {
        for (int i = 0; i < user.getRoomArray().size(); i++) {
            if (Integer.parseInt(rNum) == user.getRoomArray().get(i).getRoomNum()) {

                // 기존에 리스트가 있을 경우 지워줌
                if (user.getRoomArray().get(i).getrUI().model != null)
                    user.getRoomArray().get(i).getrUI().model.removeAllElements();

                while (token.hasMoreTokens()) {
                    // 아이디와 닉네임을 읽어서 유저 객체 하나를 생성
                    String id = token.nextToken();
                    String nick = token.nextToken();
                    User tempUser = new User(id, nick);

                    user.getRoomArray().get(i).getrUI().model.addElement(tempUser.toString());
                }
            }
        }
    }

    // 선택한 채팅방의 사용자 리스트
    private void selectedRoomUserList(StringTokenizer token) {
        // 서버로부터 유저리스트(채팅방)를 업데이트하라는 명령을 받음

        if (!waitRoom.level2.isLeaf()) {
            // 리프노드가 아니고, 차일드가 있다면 모두 지움
            waitRoom.level2.removeAllChildren();
        }
        while (token.hasMoreTokens()) {
            // 아이디와 닉네임을 읽어서 유저 객체 하나를 생성
            String id = token.nextToken();
            String nick = token.nextToken();
            User tempUser = new User(id, nick);

            // 채팅방 사용자노드에 추가
            waitRoom.level2.add(new DefaultMutableTreeNode(tempUser.toString()));
        }
        waitRoom.userTree.updateUI();
    }

    // 대기실 사용자 리스트
    private void userList(StringTokenizer token) {
        // 서버로부터 유저리스트(대기실)를 업데이트하라는 명령을 받음

        if (waitRoom == null) {
            return;
        }

        if (!waitRoom.level3.isLeaf()) {
            // 리프노드가 아니고, 차일드가 있다면 모두 지움
            waitRoom.level3.removeAllChildren();
        }
        while (token.hasMoreTokens()) {
            // 아이디와 닉네임을 읽어서 유저 객체 하나를 생성
            String id = token.nextToken();
            String nick = token.nextToken();
            User tempUser = new User(id, nick);

            for (int i = 0; i < waitRoom.userArray.size(); i++) {
                if (tempUser.getId().equals(waitRoom.userArray.get(i))) {
                }
                if (i == waitRoom.userArray.size()) {
                    // 배열에 유저가 없으면 추가해줌
                    waitRoom.userArray.add(tempUser);
                }
            }
            // 대기실 사용자노드에 추가
            waitRoom.level3.add(new DefaultMutableTreeNode(tempUser.toString()));
        }
        waitRoom.userTree.updateUI();
    }

    // 서버로부터 방리스트를 업데이트하라는 명령을 받음
    private void roomList(StringTokenizer token) {
        String rNum, rName;
        Room room = new Room();

        // 기존에 리스트가 있을 경우 지워줌
        if (waitRoom.model != null) {
            waitRoom.model.removeAllElements();
        }

        while (token.hasMoreTokens()) {
            rNum = token.nextToken();
            rName = token.nextToken();
            int num = Integer.parseInt(rNum);

            // 라스트룸넘버를 업데이트 (최대값+1)
            if (num >= waitRoom.lastRoomNum) {
                waitRoom.lastRoomNum = num + 1;
            }
            room.setRoomNum(num);
            room.setRoomName(rName);

            waitRoom.model.addElement(room.toProtocol());
        }
    }

    private void alarmMsg(String string) {  // 공지사항 겸용
        int i = JOptionPane.showConfirmDialog(null, string, "시스템", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        // 확인 누르면 종료
        if (i == 0) { }
    }

    private void announce(String msg){
        int i = JOptionPane.showConfirmDialog(null, msg, "공지사항", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        // 확인 누르면 종료
        if (i == 0) { }
    }

    private void announceRoom(String rNum,String msg){
        int i = JOptionPane.showConfirmDialog(null, msg, rNum +"번방 공지사항", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        // 확인 누르면 종료
        if (i == 0) { }
    }

    private void login(String nick) {
        // 로그인정보 가져옴
        user.setId(login.idText.getText());
        user.setNickName(nick);

        if(user.getNickName().equals("관리자")){
            user.setAdmin(true);
        }

        // 로그인창 닫고 대기실창 열기
        login.dispose();
        waitRoom = new WaitRoomUI(Client.this);
        waitRoom.lbid.setText(user.getId());
        waitRoom.lbip.setText(user.getIP());
        waitRoom.lbnick.setText(user.getNickName());
    }

    private void echoMsg(String msg) {
        // 커서 위치 조정
        if (waitRoom != null) {
            waitRoom.waitRoomArea.setCaretPosition(waitRoom.waitRoomArea.getText().length());
            waitRoom.waitRoomArea.append(msg + "\n");
        }
    }

    private void echoMsgToRoom(String rNum, String msg) {
        for (int i = 0; i < user.getRoomArray().size(); i++) {
            if (Integer.parseInt(rNum) == user.getRoomArray().get(i).getRoomNum()) {
                // 사용자 -> 방배열 -> 유아이 -> 텍스트에어리어
                // 커서 위치 조정
                user.getRoomArray().get(i).getrUI().chatArea.setCaretPosition(user.getRoomArray().get(i).getrUI().chatArea.getText().length());
                // 에코
                user.getRoomArray().get(i).getrUI().chatArea.append(msg + "\n");
            }
        }
    }

    private String censor(String msg){
        StringTokenizer idMsg = new StringTokenizer(msg, ")");
        String newMsg = idMsg.nextToken() +") : ";
        StringTokenizer tempToken = new StringTokenizer(idMsg.nextToken(), " ");
        String temp;
        while(tempToken.hasMoreTokens()){
            temp = tempToken.nextToken();
            for(int i = 0; i < this.getUserBadword().size(); i++){
                if(temp.equals(this.getUserBadword().get(i))){
                    temp = "****";
                }
            }
            newMsg += temp + " ";
        }
//        System.out.println("검열된 메세지 " + newMsg);
        return newMsg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public ArrayList<String> getUserBadword() {
        return userBadword;
    }

    public void setUserBadword(ArrayList<String> userBadword) {
        this.userBadword = userBadword;
    }

    public ArrayList<String> getUserMute() {
        return userMute;
    }

    public void setUserMute(ArrayList<String> userMute) {
        this.userMute = userMute;
    }
}
