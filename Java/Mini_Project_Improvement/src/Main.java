import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Data> list = new LinkedList<>();
        boolean flag = true;
        int num;
        String srch;

        while(flag){
            num = menu(sc);

            switch (num){
                case 1:{
                    list.add(adder(sc));
                    break;
                }
                case 2:{
                    Search(list, sc);
                    break;
                }
                case 3:{
                    list = Delete(list, sc);
                    break;

                }
                case 4:{
                    System.out.println("프로그램을 종료합니다");
                    flag = false;
                    break;
                }
                case 10:{
                    System.out.println("전화번호부에 저장되 있는 전원의 정보");
                    for(Data d : list){
                        d.showInfo();
                    }
                    break;
                }
            }

        }

        sc.close();
    }

    public static List<Data> Delete(List<Data> list, Scanner sc) {
        int count = 0;

        System.out.println("   전화번호 삭제  ");
        System.out.print("삭제하고자 하는 사람의 이름이나 전화번호를 입력해 주십시오: ");
        String srch = sc.next();

        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getName().equals(srch) || list.get(i).getPhone_Num().equals(srch)){
                list.remove(i);
                count++;
                System.out.println("성공적으로 삭제되었습니다.");
            }
        }

        if(count == list.size() && list.size() != 1){
            System.out.println("입력된 이름이나 번호를 가진 사람은 등록되어 있지 않습니다.");
        }

        return list;
    }

    public static void Search(List<Data> list, Scanner sc) {
        int count = 0;
        String srch;
        System.out.println("   전화번호 조회  ");
        System.out.print("찾고자 하는 사람의 이름을 입력해 주십시오: ");
        srch = sc.nextLine();

        for(Data s : list){
            if(s.getName().equals(srch)){
                s.showInfo();
                count++;
            }
        }
        if(count == 0){
            System.out.println("해당 이름을 가진 사람의 번호는 등록되어 있지 않습니다.");
        }
    }

    public static Data adder(Scanner sc) {
        int num;
        Data d;
        String name;
        String pNum;
        String eAddress;

        System.out.println("   전화번호 입력  ");
        System.out.println("E-mail 주소를 알고 있다면 1을");
        System.out.println("모른다면 2를 입력해 주세요");

        while(true){
            System.out.println();
            while(true){
                try{
                    System.out.print("입력(1 or 2): ");
                    num = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("잘못된 입력입니다. 다시 입력해 주십시오");
                    sc = new Scanner(System.in);
                }
            }

            if(num == 1){
                System.out.println();
                System.out.print("이름을 입력해 주십시오 : ");
                name = sc.nextLine();
                System.out.print("전화번호를 입력해 주십시오 : ");
                pNum = sc.nextLine();
                System.out.print("Email주소를 입력해 주십시오 : ");
                eAddress = sc.nextLine();

                d = new Data(name,pNum,eAddress);
                break;
            }
            else if(num == 2){
                System.out.println();
                System.out.print("이름을 입력해 주십시오 : ");
                name = sc.nextLine();
                System.out.print("전화번호를 입력해 주십시오 : ");
                pNum = sc.nextLine();

                d = new Data(name,pNum);
                break;
            }
            else{
                System.out.println("잘못된 입력입니다 다시 입력해 주십시오");
            }

        }

        return d;

    }

    public static int menu(Scanner sc){
        int key;

        System.out.println();
        System.out.println("=======전화번호부=======");
        System.out.println("1. 전화번호 입력");
        System.out.println("2. 전화번호 조회");
        System.out.println("3. 전화번호 삭제");
        System.out.println("4. 프로그램 종료");
        System.out.print(" 메뉴 선택: ");
        while(true){

            while(true){
                try {
                    System.out.print("입력(1 ~ 4): ");
                    key = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("잘못된 입력입니다. 다시 입력해 주십시오");
                    sc = new Scanner(System.in);
                }

            }

            if(key == 1){
                break;
            }
            else if(key == 2){
                break;
            }
            else if(key == 3){
                break;
            }
            else if(key == 4){
                break;
            }
            else if(key == 10){
                break;
            }
            else{
                System.out.println("잘못된 입력입니다 다시 입력해 주십시오");
            }
        }

        return key;
    }
}
