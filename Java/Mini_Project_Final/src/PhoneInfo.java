public class PhoneInfo {
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

    public String getPhoneNum() {
        return PhoneNum;
    }

    public String getEMail() {
        return EMail;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }
}
