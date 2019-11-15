import java.util.ArrayList;

public class Room {

    int roomNum;
    String roomName;
    String password;
    ArrayList<User> userArray; // 채팅방에 접속한 사람들
    User maker; // 방장, 방만든사람
    RoomUI rUI; // 방 UI
    Boolean priv = false;
    Boolean monitoring = false;

    public Room() {
        userArray = new ArrayList<User>();
    }

    public Room(String message) {
        userArray = new ArrayList<User>();
        setRoomName(message);
    }

    public Room(String message, String password){
        userArray = new ArrayList<User>();
        setRoomName(message);
        setPassword(password);
    }

    public String toProtocol() {
        return roomNum + "/" + roomName;
    }

    public String toProtocolPassword() { return roomNum + "/" + roomName + "/" + password;}

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<User> getUserArray() {
        return userArray;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User user) {
        this.maker = user;
    }

    public RoomUI getrUI() {
        return rUI;
    }

    public void setrUI(RoomUI rUI) {
        this.rUI = rUI;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPriv() {
        return priv;
    }

    public void setPriv(Boolean priv) {
        this.priv = priv;
    }

    public Boolean getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Boolean monitoring) {
        this.monitoring = monitoring;
    }
}