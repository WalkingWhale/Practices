import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerThread implements Runnable {

    MsgeBox msgbox = new MsgeBox();

    ArrayList<User> userArray; // 서버에 접속한 사용자들
    ArrayList<User> waitArray; // 대기방에 있는 사람들
    ArrayList<Room> roomArray; // 서버가 열어놓은 채팅방들
    User user; // 현재 스레드와 연결된(소켓이 생성된) 사용자
    JTextArea jta;
    boolean onLine = true;

    private DataOutputStream thisUser;

    ServerThread(JTextArea jta, User person, ArrayList<User> userArray, ArrayList<User> waitArray ,ArrayList<Room> roomArray) {

        this.roomArray = roomArray;
        this.userArray = userArray;
        this.userArray.add(person); // 배열에 사용자 추가
        this.waitArray = waitArray;
        this.waitArray.add(person); // 대기방 배열에 사용자 추가
        this.user = person;
        this.jta = jta;
        this.thisUser = person.getDos();
    }

    @Override
    public void run() {
        DataInputStream dis = user.getDis(); // 입력 스트림 얻기

        while (onLine) {
            try {
                String receivedMsg = dis.readUTF(); // 메시지 받기(대기)
                dataParsing(receivedMsg); // 메시지 해석
                jta.append("성공 : 메시지 읽음 -" + receivedMsg + "\n");
                jta.setCaretPosition(jta.getText().length());
            } catch (IOException e) {
                try {
                    user.getDis().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    jta.append("에러 : 서버스레드-읽기 실패\n");
                    break;
                }
            }
        }
    }

    // 데이터를 구분
    public synchronized void dataParsing(String data) {

        StringTokenizer token = new StringTokenizer(data, "/"); // 토큰 생성
        String protocol = token.nextToken(); // 토큰으로 분리된 스트링을 숫자로
        String id, pw, room, rNum, rName, rPw, msg, nickname;
        System.out.println("서버가 받은 데이터 : " + data);

        switch (protocol) {
            case User.LOGIN: // 로그인
                // 사용자가 입력한(전송한) 아이디와 패스워드
                id = token.nextToken();
                pw = token.nextToken();
                login(id, pw);
                break;
            case User.LOGOUT: // 로그아웃
                logout();
                break;
            case User.MEMBERSHIP: // 회원가입
                id = token.nextToken();
                pw = token.nextToken();
                break;
            case User.UPDATE_USERLIST: // 대기실 사용자 목록
                userList(thisUser);
                break;
            case User.UPDATE_ROOM_USERLIST: // 채팅방 사용자 목록
                // 방번호읽기
                rNum = token.nextToken();
                userList(rNum, thisUser);
                break;
            case User.UPDATE_SELECTEDROOM_USERLIST: // 대기실에서 선택한 채팅방의 사용자 목록
                // 방번호읽기
                rNum = token.nextToken();
                selectedRoomUserList(rNum, thisUser);
                break;
            case User.UPDATE_ROOMLIST: // 방 목록
                roomList(thisUser);
                break;
            case User.CREATE_ROOM: // 방만들기
                rNum = token.nextToken();
                rName = token.nextToken();
                createRoom(rNum, rName);
                break;
            case User.CREATE_ROOM_PRIVATE: // 비공개 방만들기
                rNum = token.nextToken();
                rName = token.nextToken();
                rPw = token.nextToken();
                createRoom(rNum,rName,rPw);
                break;
            case User.TRY_GETIN_ROOM:   // 방 입장 시도
                rNum = token.nextToken();
                tryGetIn(rNum);
                break;
            /*case User.GETIN_ROOM: // 방 들어가기
                rNum = token.nextToken();
                getInRoom(rNum);
                break;*/
            case User.GETIN_ROOM_PRIVATE: // 방 들어가기
                rNum = token.nextToken();
                rPw = token.nextToken();
                getInRoom_Private(rNum,rPw);
                break;
            case User.GETOUT_ROOM: // 방 나오기
                rNum = token.nextToken();
                getOutRoom(rNum);
                break;
            case User.ECHO01: // 대기실 에코
                msg = token.nextToken();
                echoMsg(User.ECHO01 + "/" + user.toString() + msg);
                break;
            case User.ECHO02: // 채팅방 에코
                rNum = token.nextToken();
                msg = token.nextToken();
                echoMsg(rNum, msg);
                break;
            case User.WHISPER: // 귓속말
                id = token.nextToken();
                msg = token.nextToken();
                whisper(id, msg);
                break;
            /*case User.WHISPERROOM: // 채팅방으로 귓속말
                room = token.nextToken();
                id = token.nextToken();
                msg = token.nextToken();
                //whisperRoom(room, id, msg);
                break;*/
            case User.CHANGENICKNAME:
                room = token.nextToken();
                nickname = token.nextToken();
                changenick(nickname);
                for (int i = 0; i < userArray.size(); i++) {
                    userList(userArray.get(i).getDos());
                }
                break;
            case User.MONITOR:
                rNum = token.nextToken();
                monitor(rNum);
                break;

        }
    }

    private void changenick(String nick){
        if(user.getAdmin() == true){
            try{
                thisUser.writeUTF(User.WHISPER + "/admin/관리자/관리자는 닉네임을 변경할 수 없습니다.");
            }catch(IOException e){
                e.printStackTrace();
            }
        } else{

            if(nick.equals("관리자")||nick.equals("admin")){
                try{
                    thisUser.writeUTF(User.WHISPER + "/admin/관리자/닉네임을 관리자 닉네임으로 변경할 수 없습니다.");
                }catch(IOException e){
                    e.printStackTrace();
                }
            } else{
                try{
                    thisUser.writeUTF(User.CHANGENICKNAME + "/" + nick);
                    this.user.setNickName(nick);
                    //whisper(user.id,"요청한 닉네임으로 변경되었습니다.");
                    //echoMsg(User.ECHO01 + "/" + user.getId() + "님의 닉네임이 " + nick + "으로 변경되었습니다.");
                    thisUser.writeUTF(User.WHISPER + "/admin/관리자/요청한 닉네임으로 변경되었습니다.");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void monitor(String rNum){
        boolean flag = false;

        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                flag = true;
                try{
                    // 방 객체가 있는 경우, 방에 사용자추가
                    roomArray.get(i).getUserArray().add(user);
                    roomArray.get(i).setMonitoring(true);
                    // 사용자 객체에 방 추가
                    user.getRoomArray().add(roomArray.get(i));

                    thisUser.writeUTF(User.MONITOR+ "/" + roomArray.get(i).getRoomNum() +"/"+ roomArray.get(i).getRoomName());
//                    userList(rNum, thisUser);   // 만들어진 채팅방 유저리스트 업데이트
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        if(flag == false){
            try{
                thisUser.writeUTF(User.ANNOUNCE + "/" +"모니터 하려는 방이 존재하지 않습니다.");
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void getOutRoom(String rNum) {
        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                // 방에서 나가기
                // 채팅방의 유저리스트에서 사용자 삭제
                for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                    if (user.getId().equals(roomArray.get(i).getUserArray().get(j).getId())) {
                        roomArray.get(i).getUserArray().remove(j);
                    }
                }

                // 사용자의 방리스트에서 방을 제거
                for (int j = 0; j < user.getRoomArray().size(); j++) {
                    if (Integer.parseInt(rNum) == user.getRoomArray().get(j).getRoomNum()) {
                        user.getRoomArray().remove(j);
                    }
                }

                this.waitArray.add(user); // 대기방 배열에 사용자 추가

                echoMsg(roomArray.get(i), user.toString() + "님이 퇴장하셨습니다.");
                userList(rNum);

                if (roomArray.get(i).getUserArray().size() <= 0) {
                    roomArray.remove(i);
                    roomList();
                }
            }
        }

        for (int j = 0; j < userArray.size(); j++) {
            userList(userArray.get(j).getDos());
        }   // 대기실 유저리스트 업데이트
    }

    private void tryGetIn(String rNum){

        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                if(!(roomArray.get(i).getPriv())){
                    getInRoom(rNum);
                }
                else{
                    TryIn_Private(rNum);
                }
            }
        }
    }

    private void TryIn_Private(String rNum){
        try{
            thisUser.writeUTF(User.TRY_GETIN_PRIVATE + "/" + rNum);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getInRoom(String rNum) {
        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {

                for (int j = 0; j < waitArray.size(); j++) {
                    if (user.getId().equals(waitArray.get(j).getId())) {
                        System.out.println(waitArray.get(j).getId() + ": 방으로 이동.");
                        waitArray.remove(j);
                    }
                } // 방 객객체가있는경우 대기 array에서 유저 지움

                // 방 객체가 있는 경우, 방에 사용자추가
                roomArray.get(i).getUserArray().add(user);
                // 사용자 객체에 방 추가
                user.getRoomArray().add(roomArray.get(i));
                echoMsg(roomArray.get(i), user.toString() + "님이 입장하셨습니다.");
                userList(rNum);
                try{
                    thisUser.writeUTF(User.GETIN_ROOM + "/OK/" + roomArray.get(i).getRoomNum() +"/"+ roomArray.get(i).getRoomName());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        for (int j = 0; j < userArray.size(); j++) {
            userList(userArray.get(j).getDos());
        }   // 대기실 유저리스트 업데이트
    }

    private void getInRoom_Private(String rNum, String rPw) {
        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                // 방 객체가 있는 경우,
                if(roomArray.get(i).getPassword().equals(rPw)){

                    for (int j = 0; j < waitArray.size(); j++) {
                        if (user.getId().equals(waitArray.get(j).getId())) {
                            System.out.println(waitArray.get(j).getId() + ": 방으로 이동.");
                            waitArray.remove(j);
                        }
                    }   // pw까지 맞췃을경우 대기array에서 지움

                    roomArray.get(i).getUserArray().add(user);
                    // 사용자 객체에 방 추가
                    user.getRoomArray().add(roomArray.get(i));
                    echoMsg(roomArray.get(i), user.toString() + "님이 입장하셨습니다.");
                    userList(rNum);

                    try{
                        thisUser.writeUTF(User.GETIN_ROOM + "/OK/" + roomArray.get(i).getRoomNum() +"/"+ roomArray.get(i).getRoomName());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else{
                    try{
                        thisUser.writeUTF(User.GETIN_ROOM_FAIL + "/비밀번호가 잘못되었습니다.");
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

            }
        }

        for (int j = 0; j < userArray.size(); j++) {
            userList(userArray.get(j).getDos());
        }   // 대기실 유저리스트 업데이트
    }

    private void createRoom(String rNum, String rName) {
        Room rm = new Room(rName); // 지정한 제목으로 채팅방 생성
        rm.setMaker(user); // 방장 설정
        rm.setRoomNum(Integer.parseInt(rNum)); // 방번호 설정

        rm.getUserArray().add(user); // 채팅방에 유저(본인) 추가
        roomArray.add(rm); // 룸리스트에 현재 채팅방 추가
        user.getRoomArray().add(rm); // 사용자 객체에 접속한 채팅방을 저장
        user.setInRoom(true);   // 방안에 들어있는것으로 변경
        echoMsg(User.ECHO01 + "/" + user.toString() + "님이 " + rm.getRoomNum() + "번 채팅방을 개설하셨습니다.");
        echoMsg(rm, user.toString() + "님이 입장하셨습니다.");
        roomList(); // 방리스트 업데이트

        for (int i = 0; i < waitArray.size(); i++) {
            if (user.getId().equals(waitArray.get(i).getId())) {
                System.out.println(waitArray.get(i).getId() + ": 방으로 이동.");
                waitArray.remove(i);
            }
        }

        for (int i = 0; i < userArray.size(); i++) {
            userList(userArray.get(i).getDos());
        }   // 대기실 유저리스트 업데이트

        userList(rNum, thisUser);   // 접속된 채팅방 유저 리스트 업데이트
        jta.append("성공 : " + userArray.toString() + "가 채팅방생성\n");
    }

    private void createRoom(String rNum, String rName, String rPw) {
        Room rm = new Room(rName, rPw); // 지정한 제목으로 채팅방 생성
        rm.setMaker(user); // 방장 설정
        rm.setRoomNum(Integer.parseInt(rNum)); // 방번호 설정
        rm.setPriv(true);   // 비공개방 설정 on

        rm.getUserArray().add(user); // 채팅방에 유저(본인) 추가
        roomArray.add(rm); // 룸리스트에 현재 채팅방 추가
        user.getRoomArray().add(rm); // 사용자 객체에 접속한 채팅방을 저장
        user.setInRoom(true);   // 방안에 들어있는것으로 변경

        echoMsg(User.ECHO01 + "/" + user.toString() + "님이 " + rm.getRoomNum() + "번 채팅방을 개설하셨습니다.");
        echoMsg(rm, user.toString() + "님이 입장하셨습니다.");
        roomList(); // 방리스트 업데이트

        for (int i = 0; i < waitArray.size(); i++) {
            if (user.getId().equals(waitArray.get(i).getId())) {
                System.out.println(waitArray.get(i).getId() + ": 방으로 이동.");
                waitArray.remove(i);
            }
        }

        for (int i = 0; i < userArray.size(); i++) {
            userList(userArray.get(i).getDos());
        }   // 대기실 유저 리스트 업데이트

        userList(rNum, thisUser);   // 만들어진 채팅방 유저리스트 업데이트
        jta.append("성공 : " + userArray.toString() + "가 채팅방생성\n");
    }

    private void whisper(String id, String msg) {
        for (int i = 0; i < userArray.size(); i++) {
            if (id.equals(userArray.get(i).getId())) {
                // 귓속말 상대를 찾으면
                try {
                    userArray.get(i).getDos().writeUTF(User.WHISPER + "/" + user.toProtocol() + "/" + msg);
                    jta.append("성공 : 귓속말보냄 : " + user.toString() + "가 " + userArray.get(i).toString() + "에게" + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 대기실 에코
    private void echoMsg(String msg) {
        for (int i = 0; i < userArray.size(); i++) {
            try {
                userArray.get(i).getDos().writeUTF(msg);
                jta.append(user.toString() + " - " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                jta.append("에러 : 에코 실패\n");
            }
        }
    }

    // 방 에코 (방 번호만 아는 경우)
    private void echoMsg(String rNum, String msg) {
        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                echoMsg(roomArray.get(i), msg);
            }
        }
    }

    // 방 에코 (방객체가 있는 경우)
    private void echoMsg(Room room, String msg) {
        for (int i = 0; i < room.getUserArray().size(); i++) {
            try {
                // 방에 참가한 유저들에게 에코 메시지 전송
                room.getUserArray().get(i).getDos().writeUTF(User.ECHO02 + "/" + room.getRoomNum() + "/" + msg);
                jta.append("성공 : 메시지전송 : " + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                jta.append("에러 : 에코 실패\n");
            }
        }
    }

    private void login(String id, String pw) {
        StringBuffer str = new StringBuffer();
        try {

            DBLogin logdb = new DBLogin();
            int result = logdb.checkIDPW(id, pw);

            if (result == 0 || result == 5) { // result가 0이면 일반유저 result가 5면 관리자 로그인
                for (int i = 0; i < userArray.size(); i++) {
                    if (id.equals(userArray.get(i).getId())) {
                        try {
                            System.out.println("접속중");
                            thisUser.writeUTF(User.LOGIN + "/fail/이미 접속 중입니다.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                if(result == 5){
                    // 관리자 로그인 ok
                    user.setId(id);
                    user.setPw(pw);
                    user.setNickName("관리자");
                    user.setAdmin(true);
                    thisUser.writeUTF(User.LOGIN + "/OK/" + user.getNickName());
                    this.user.setOnline(true);
                } else{
                    // 일반유저 로그인 OK
                    user.setId(id);
                    user.setPw(pw);
                    user.setNickName(id);
                    thisUser.writeUTF(User.LOGIN + "/OK/" + user.getNickName());
                    this.user.setOnline(true);
                }


                // 대기실에 에코
                echoMsg(User.ECHO01 + "/" + user.toString() + "님이 입장하셨습니다.");
                jta.append(id + " : 님이 입장하셨습니다.\n");

                roomList(thisUser);
                for (int i = 0; i < waitArray.size(); i++) {
                    userList(waitArray.get(i).getDos());
                }

                jta.append("성공 : DB 읽기 : " + id);
            } else if(result == 1){ // result가 1이면 실패
                thisUser.writeUTF(User.LOGIN + "/fail/아이디와 비밀번호를 확인해 주세요!");
            } else if(result == 2){ // result가 2이면 Banned
                thisUser.writeUTF(User.LOGIN + "/fail/Block된 계정입니다. 관리자에게 문의해주세요!");
            }

        } catch (Exception e) {
            try {
                thisUser.writeUTF(User.LOGIN + "/fail/아이디가 존재하지 않습니다!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            jta.append("실패 : DB 읽기\n");
            return;
        }

    }

    private void logout() {
        System.out.println("로그아웃 했음!");

        // 오프라인으로 바꿈
        user.setOnline(false);
        // 사용자배열에서 삭제
        for (int i = 0; i < userArray.size(); i++) {
            if (user.getId().equals(userArray.get(i).getId())) {
                System.out.println(userArray.get(i).getId() + "지웠다.");
                userArray.remove(i);
            }
        }
        // room 클래스의 멤버변수인 사용자배열에서 삭제
        for (int i = 0; i < roomArray.size(); i++) {
            for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                if (user.getId().equals(roomArray.get(i).getUserArray().get(j).getId())) {
                    roomArray.get(i).getUserArray().remove(j);
                }
            }
        }
        echoMsg(User.ECHO01 + "/" + user.toString() + "님이 퇴장하셨습니다.");

        for (int i = 0; i < userArray.size(); i++) {
            userList(userArray.get(i).getDos());
        }

        jta.append(user.getId() + " : 님이 퇴장하셨습니다.\n");

        try {
            user.getDos().writeUTF(User.LOGOUT);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            user.getDis().close();
            user.getDos().close();
            user = null;
            jta.append("성공 : 스트림 닫기\n");
        } catch (IOException e) {
            e.printStackTrace();
            jta.append("실패 : 스트림 닫기\n");
        }
    }

    // 사용자 리스트 (선택한 채팅방)
    public void selectedRoomUserList(String rNum, DataOutputStream target) {
        String ul = "";
        boolean flag = false;

        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                if(roomArray.get(i).getMonitoring() == true){
                    flag = true;
                }
                for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                    // 채팅방에 접속되어 있는 유저들의 아이디+닉네임
                    ul += "/" + roomArray.get(i).getUserArray().get(j).toProtocol();
                }
            }
        }
        try {
            // 데이터 전송
            target.writeUTF(User.UPDATE_SELECTEDROOM_USERLIST + "/" + flag  + ul);
            jta.append("성공 : 목록(사용자)-" + ul + "\n");
        } catch (IOException e) {
            jta.append("에러 : 목록(사용자) 전송 실패\n");
        }
    }

    // 사용자 리스트 (대기실)
    public String userList(DataOutputStream target) {
        String ul = "";

        for (int i = 0; i < waitArray.size(); i++) {
            // 접속되어 있는 유저들의 아이디+닉네임
            ul += "/" + waitArray.get(i).toProtocol();
        }

        try {
            // 데이터 전송
            target.writeUTF(User.UPDATE_USERLIST + ul);
            jta.append("성공 : 목록(사용자)-" + ul + "\n");
        } catch (IOException e) {
            jta.append("에러 : 목록(사용자) 전송 실패\n");
        }
        return ul;
    }

    // 사용자 리스트 (채팅방 내부)
    public void userList(String rNum, DataOutputStream target) {
        String ul = "/" + rNum;
        boolean flag = false;

        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {  // 방 찾음

                if(roomArray.get(i).getMonitoring() ==  true){              // 관리자가 모니터링 중인 방이면
                    flag = true;
                    ul += "/" + flag;
                    for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                        flag = true; // 관리자가 모니터링중
                        // 채팅방에 접속되어 있는 관리자가 아닌 유저들의 아이디+닉네임
                        ul += "/" + roomArray.get(i).getUserArray().get(j).toProtocol();

                    }
                } else{ // 관리자가 모니터링중이 아니면
                    ul += "/" + flag;
                    for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {  // 전부다 추가
                        // 채팅방에 접속되어 있는 유저들의 아이디+닉네임
                        ul += "/" + roomArray.get(i).getUserArray().get(j).toProtocol();
                    }
                }
            }
        }

        try {
            // 데이터 전송
            target.writeUTF(User.UPDATE_ROOM_USERLIST + ul);
            jta.append("성공 : 목록(사용자)-" + ul + "\n");
        } catch (IOException e) {
            jta.append("에러 : 목록(사용자) 전송 실패\n");
        }

    }

    // 사용자 리스트 (채팅방 내부 모든 사용자들에게 전달)
    public void userList(String rNum) {
        String ul = "/" + rNum;
        boolean flag = false;
        Room temp = null;

        for (int i = 0; i < roomArray.size(); i++) {
            if (Integer.parseInt(rNum) == roomArray.get(i).getRoomNum()) {
                temp = roomArray.get(i);

                if(roomArray.get(i).getMonitoring() ==  true){
                    flag = true;
                    ul += "/" + flag;
                    for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                        // 채팅방에 접속되어 있는 관리자가 아닌 유저들의 아이디+닉네임
                        ul += "/" + roomArray.get(i).getUserArray().get(j).toProtocol();

                    }
                } else{
                    for (int j = 0; j < roomArray.get(i).getUserArray().size(); j++) {
                        // 채팅방에 접속되어 있는 유저들의 아이디+닉네임
                        ul += "/" + roomArray.get(i).getUserArray().get(j).toProtocol();
                    }
                }

            }
        }
        for (int i = 0; i < temp.getUserArray().size(); i++) {

            try {
                // 데이터 전송
                temp.getUserArray().get(i).getDos().writeUTF(User.UPDATE_ROOM_USERLIST  + ul);
                jta.append("성공 : 목록(사용자)-" + ul + "\n");
            } catch (IOException e) {
                jta.append("에러 : 목록(사용자) 전송 실패\n");
            }
        }
    }

    // 채팅 방리스트
    public void roomList(DataOutputStream target) {
        String rl = "";

        for (int i = 0; i < roomArray.size(); i++) {
            // 만들어진 채팅방들의 제목
            rl += "/" + roomArray.get(i).toProtocol();
        }

        jta.append("test\n");

        try {
            // 데이터 전송
            target.writeUTF(User.UPDATE_ROOMLIST + rl);
            jta.append("성공 : 목록(방)-" + rl + "\n");
        } catch (IOException e) {
            jta.append("에러 : 목록(방) 전송 실패\n");
        }
    }

    // 채팅 방리스트
    public void roomList() {
        String rl = "";

        for (int i = 0; i < roomArray.size(); i++) {
            // 만들어진 채팅방들의 제목
            rl += "/" + roomArray.get(i).toProtocol();
        }

        jta.append("test\n");

        for (int i = 0; i < userArray.size(); i++) {

            try {
                // 데이터 전송
                userArray.get(i).getDos().writeUTF(User.UPDATE_ROOMLIST + rl);
                jta.append("성공 : 목록(방)-" + rl + "\n");
            } catch (IOException e) {
                jta.append("에러 : 목록(방) 전송 실패\n");
            }
        }
    }
}
