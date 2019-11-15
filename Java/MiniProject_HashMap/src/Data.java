import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Data {
    private String Name;
    private String Phone_Num;
    private String EMail_Address;

    public Map<String, Data> map = new HashMap<>();

    public Data() {
    }

    public Data(String name, String num) {
        this.Name = name;
        this.Phone_Num = num;
    }

    public Data(String name, String num, String mail) {
        this.Name = name;
        this.Phone_Num = num;
        this.EMail_Address = mail;
    }

    public void AddInfo(Scanner sc) {
        String name;
        String pNum;
        String eAddress;

        System.out.print("이름을 입력해 주십시오 : ");
        name = sc.nextLine();
        System.out.print("전화번호를 입력해 주십시오 : ");
        pNum = sc.nextLine();
        System.out.print("Email주소를 입력해 주십시오 : ");
        eAddress = sc.nextLine();

        if(eAddress.equals("")){
            this.map.put(name, new Data(name, pNum));
        }
        else{
            this.map.put(name, new Data(name,pNum,eAddress));
        }

    }

    public void SearchInfo(Scanner sc){
        String name;

        System.out.print("찾고자 하는 이름을 입력해 주십시오 : ");
        name = sc.nextLine();

        Set<String> key = this.map.keySet();

        for(String s : key){
            if(key.equals(name)){
                this.map.get(key).showInfo();
            }
        }

    }

    public void DeleteInfo(Scanner sc) {

        if(this.map.isEmpty()){
            return;
        }

        int count = 0;

        System.out.println("   전화번호 삭제  ");
        System.out.print("삭제하고자 하는 사람의 이름을 입력해 주십시오 : ");
        String srch = sc.nextLine();

        Set<String> key =  this.map.keySet();

        for(String s : key){
            if(key.equals(srch)){
                this.map.remove(key);
                count++;
            }
        }

        if(count == 0){
            System.out.println("입력된 이름이나 번호를 가진 사람은 등록되어 있지 않습니다.");
        }
    }

    public void showAll(){
        Set<String> key = map.keySet();

        for(String s : key){
            this.map.get(key).showInfo();
        }
    }

    public void showInfo(){
        System.out.println("이름 : " + this.Name);
        System.out.println("전화번호 : " + this.Phone_Num);
        if(this.EMail_Address != null){
            System.out.println("E-mail 주소 : " + this.EMail_Address);
        }

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



}
