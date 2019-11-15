import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class User {

    String IP;
    String nickName; // 사용자 닉네임
    String id; // 사용자 아이디 - IP 주소
    String pw; // password
    boolean online;
    ArrayList<Room> userooms; // 사용자가 입장한 방의 목록
    boolean admin = false;
    boolean inRoom = false;

    DataInputStream dis; // 입력스트림
    DataOutputStream dos; // 출력스트림

    // PROTOCOLs
    public static final String LOGIN = "LI"; // 로그인
    public static final String LOGOUT = "LO"; // 로그아웃
    public static final String MEMBERSHIP = "RG"; // 회원가입
    public static final String MODIFY = "MO";   // 회원정보변경
    public static final String CLOSEACCOUNT = "BYE"; // 회원탈퇴
    public static final String ALARM = "ALARM"; // 시스템알림

    public static final String CHANGENICKNAME = "CN";   //닉네임 변경

    public static final String UPDATE_SELECTEDROOM_USERLIST = "UL"; // 대기실에서 선택한 채팅방의 유저리스트 업데이트
    public static final String UPDATE_ROOM_USERLIST = "RUL"; // 채팅방의 유저리스트 업데이트
    public static final String UPDATE_USERLIST = "AUL"; // 유저리스트 업데이트
    public static final String UPDATE_ROOMLIST = "ARL"; // 채팅방리스트 업데이트

    public static final String TRY_GETIN_ROOM  = "TI";  // 채팅방 입장 시도
    public static final String TRY_GETIN_PRIVATE  = "TIP";  // 채팅방 입장 시도
    public static final String CREATE_ROOM = "RC"; // 공개 채팅방 생성
    public static final String CREATE_ROOM_PRIVATE = "PRC"; // 비공개 채팅방 생성
    public static final String GETIN_ROOM = "RI"; // 공개 채팅방 들어옴
    public static final String GETIN_ROOM_PRIVATE = "PRI";  // 비공개 채팅방 들어옴
    public static final String GETIN_ROOM_FAIL = "JF";  // 채팅방 입장 실패
    public static final String GETOUT_ROOM = "RO"; // 채팅방 나감

    public static final String ECHO01 = "CHW"; // 대기실 채팅
    public static final String ECHO02 = "CHR"; // 채팅방 채팅
    public static final String WHISPER = "WHI"; // 귓속말

    // 관리자 커맨드
    public static final String ANNOUNCE = "AN"; // 전체공지사항
    public static final String ANNOUNCEROOM = "ANR"; // 지정된 방에 공지사항
    public static final String MONITOR = "MON"; // 채팅방 모니터
    public static final String BAN = "BAN";     // 서버 밴




    User(String id, String nick) {
        this.id = id;
        this.nickName = nick;
    }

    User(DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
//		nickCnt++;
//		setNickName(defaultNick + nickCnt);
        userooms = new ArrayList<Room>();
    }

    public String toStringforLogin() {
        return id + "/" + pw;
    }

    public String toProtocol() {
        return id + "/" + nickName;
    }

    public String toString() {
        return nickName + "(" + id + ")";
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public ArrayList<Room> getRoomArray() {
        return userooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.userooms = rooms;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }

    public boolean getInRoom() {
        return inRoom;
    }

    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }
}
