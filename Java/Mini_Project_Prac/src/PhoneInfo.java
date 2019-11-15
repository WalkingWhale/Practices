public class PhoneInfo implements java.io.Serializable {
    private String Name;
    private String PhoneNum;
    private String EMail;

    public PhoneInfo(String name, String phoneNum) {
        this.Name = name;
        this.PhoneNum = phoneNum;
    }

    public PhoneInfo(String name, String phoneNum, String Email) {
        this.Name = name;
        this.PhoneNum = phoneNum;
        this.EMail = Email;
    }
    public void ShowInfo(){
        System.out.println("이름 : " + this.Name);
        System.out.println("전화번호 : " + this.PhoneNum);
        if(this.EMail != null){
            System.out.println("E-mail 주소 : " + this.EMail);
        }
    }

    public String getName() {
        return this.Name;
    }
}
