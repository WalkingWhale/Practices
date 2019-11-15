import java.io.*;
import java.util.*;

public class MyPhoneBook {
    static Scanner sc = new Scanner(System.in);
    static Map<String, PhoneInfo> map = new HashMap<>();

    public static void showMenu(){
        System.out.println("   [메뉴 선택]  ");
        System.out.println("1. 전화번호 입력");
        System.out.println("2. 전화번호 조회");
        System.out.println("3. 전화번호 삭제");
        System.out.println("4. 프로그램 종료");
        System.out.println("10. 전화번호 전체 조회");
        System.out.print(" 메뉴 선택: ");
    }

    public static void addNumber(){
        PhoneInfo pInfo;
        String name;
        String pNum;
        String eAddress;

        while(true){
            System.out.print("이름을 입력해 주십시오 : ");
            name = sc.nextLine();
            if(name.length() == 0){
                System.out.println("이름이 입력 되지 않았습니다.");
                continue;
            }
            System.out.print("전화번호를 입력해 주십시오 : ");
            pNum = sc.nextLine();
            if(pNum.length() == 0){
                System.out.println("전화번호가 입력 되지 않았습니다.");
                continue;
            }
            System.out.print("Email주소를 입력해 주십시오 : ");
            eAddress = sc.nextLine();
            break;
        }


        if (eAddress.length() != 0){
            pInfo = new PhoneInfo(name,pNum,eAddress);
        }
        else{
            pInfo = new PhoneInfo(name,pNum);
        }

        map.put(name,pInfo);
    }

    public static void selNumber(){
        // int count = 0;
        String srch;
        System.out.println("   전화번호 조회  ");
        System.out.print("찾고자 하는 사람의 이름을 입력해 주십시오: ");
        srch = sc.nextLine();

        /*Set<String> key = map.keySet();
        for(String k : key){
            if(k.equals(srch)){
                map.get(k).ShowInfo();
                count++;
            }
        }

        if(count == 0){
            System.out.println("해당 이름을 가진 사람의 번호는 등록되어 있지 않습니다.");
        }*/

        if(map.containsKey(srch)){
            PhoneInfo pInfo = map.get(srch);
            pInfo.ShowInfo();
        }
        else{
            System.out.println("해당 이름을 가진 사람의 번호는 등록되어 있지 않습니다.");
        }

    }
    public static void delNumver(){
        // int count = 0;

        System.out.println("   전화번호 삭제  ");
        System.out.print("삭제하고자 하는 사람의 이름을 입력해 주십시오: ");
        String srch = sc.next();
        // Set<String> key = map.keySet();

        if(map.containsKey(srch)){
            map.remove(srch);
        }
        else{
            System.out.println("해당 이름을 가진 사람의 번호는 등록되어 있지 않습니다.");
        }

        /*for(String k : key){
            if(k.equals(srch)){
                map.remove(k);
                count++;
            }
        }

        if(count == 0){
            System.out.println("입력된 이름이나 번호를 가진 사람은 등록되어 있지 않습니다.");
        }*/
    }

    public static void showAll(){

        Map<String, PhoneInfo> Tmap = new TreeMap<>(map);

        Set<String> key = Tmap.keySet();

        for(String k : key){
            Tmap.get(k).ShowInfo();
        }
    }

    public static void saveInfo(){
        String s;

        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Info.csv"), "UTF-8"));
            Set<String> key = map.keySet();

            for(String k : key){
                if(map.get(k).getEMail() != null){
                    s = map.get(k).getName()+","+map.get(k).getPhoneNum()+","+map.get(k).getEMail();
                }
                else{
                    s = map.get(k).getName()+","+map.get(k).getPhoneNum();
                }

                writer.write(s);
                writer.newLine();
                //PhoneInfo pInfo = map.get(k);
                //oo.writeObject(pInfo);
                //oo.writeObject(map.get(k));
            }

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readInfo(){
        PhoneInfo pInfo;
        StringTokenizer token;
        String str;
        String name;
        String pNum;
        String eAddress;
        int count;

        try{
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream("Info.csv")));
            while((str =  reader.readLine())!=null){
                token = new StringTokenizer(str, ",");
                name = null;
                pNum = null;
                eAddress = null;

                int num = token.countTokens();
                count = 0;
                while(count <num){

                    if(count == 0){
                        name = token.nextToken();
                        count++;
                    }

                    else if (count == 1){
                        pNum = token.nextToken();
                        count++;
                    }

                    else if (count == 2){
                        eAddress = token.nextToken();
                        count++;
                    }
                }

                if (eAddress != null){
                    pInfo = new PhoneInfo(name,pNum,eAddress);
                }
                else{
                    pInfo = new PhoneInfo(name,pNum);
                }

                map.put(name,pInfo);
            }

            reader.close();

        }catch (IOException e){
            //e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        int choice;

        readInfo();

        while (true){
            showMenu();
            while(true){
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("잘못된 입력입니다. 다시 입력해 주십시오");
                    sc = new Scanner(System.in);
                }

            }

            switch (choice){
                case 1:{
                    addNumber();
                    break;
                }
                case 2:{
                    selNumber();
                    break;
                }
                case 3:{
                    delNumver();
                    break;
                }
                case 4:{
                    System.out.println("정보를 저장중입니다.");
                    saveInfo();
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                case 10:{
                    showAll();
                    break;
                }
                default:{
                    System.out.println("잘못입력하셨습니다.");
                    break;
                }

            }
        }
    }
}
