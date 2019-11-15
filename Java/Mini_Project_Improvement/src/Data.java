public class Data {
    private String Name;
    private String Phone_Num;
    private String EMail_Address;

    public Data(String name, String num) {
        this.Name = name;
        this.Phone_Num = num;
    }

    public Data(String name, String num, String mail) {
        this.Name = name;
        this.Phone_Num = num;
        this.EMail_Address = mail;
    }

    public String getEMail_Address() {
        return EMail_Address;
    }

    public String getPhone_Num() {
        return Phone_Num;
    }

    public String getName() {
        return Name;
    }

    public void setPhone_Num(String phone_Num) {
        Phone_Num = phone_Num;
    }

    public void setEMail_Address(String EMail_Address) {
        this.EMail_Address = EMail_Address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void showInfo(){

        if(this.EMail_Address != null){
            System.out.println("이름 : " + this.Name);
            System.out.println("전화번호 : " + this.Phone_Num);
            System.out.println("E-mail 주소 : " + this.EMail_Address);
        }

        else{
            System.out.println("이름 : " + this.Name);
            System.out.println("전화번호 : " + this.Phone_Num);
        }

    }

}
