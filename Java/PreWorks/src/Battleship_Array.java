import java.util.Random;
import java.util.Scanner;

class BattleShip {
    static final int MAX_ARR_SIZE = 9;
    private int x_pos_1;
    private int x_pos_2;
    private int y_pos_1;
    private int y_pos_2;
    private int direction;
    private int Life_Point = 2;
    private int[][] Arr = new int[MAX_ARR_SIZE][MAX_ARR_SIZE];


    public void setX_pos_1(int x_pos) {
        this.x_pos_1 = x_pos;
    }

    public void setX_pos_2(int x_pos) {
        this.x_pos_2 = x_pos;
    }

    public void setY_pos_1(int y_pos) {
        this.y_pos_1 = y_pos;
    }

    public void setY_pos_2(int y_pos) {
        this.y_pos_2 = y_pos;
    }

    public int getX_pos_1() {
        return this.x_pos_1;
    }

    public int getX_pos_2() {
        return this.x_pos_2;
    }

    public int getY_pos_1() {
        return this.y_pos_1;
    }

    public int getY_pos_2() {
        return this.y_pos_2;
    }

    public int getDirection() {
        return this.direction;
    }

    public int getLife_Point() {
        return this.Life_Point;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    protected void genXpos_1() {
        Random ran = new Random(System.currentTimeMillis());
        int temp = (ran.nextInt(7) + 1);
        //System.out.println(temp);
        this.setX_pos_1(temp);
    }

    protected void genYpos_1() {
        Random ran = new Random(System.currentTimeMillis());
        int temp = (ran.nextInt(7) + 1);
        //System.out.println(temp);
        this.setY_pos_1(temp);
    }

    protected void genDirection() {
        Random ran = new Random(System.currentTimeMillis());
        int temp = ran.nextInt(4);
        setDirection(temp);
    }

    protected void placeValue(){
        this.Arr[this.getX_pos_1()][this.getY_pos_1()] = 1;
        this.Arr[this.getX_pos_2()][this.getY_pos_2()] = 1;
    }

    public void setLife_Point(int life_Point) {
        this.Life_Point = life_Point;
    }

    protected void gen2ndpos() {
        int dir = this.getDirection();

        switch (dir) {
            case 0: {
                this.setX_pos_2(this.getX_pos_1());
                this.setY_pos_2(this.getY_pos_1() + 1);
                break;
            }
            case 1: {
                this.setX_pos_2(this.getX_pos_1() + 1);
                this.setY_pos_2(this.getY_pos_1());
                break;
            }
            case 2: {
                this.setX_pos_2(this.getX_pos_1());
                this.setY_pos_2(this.getY_pos_1() - 1);
                break;
            }
            case 3: {
                this.setX_pos_2(this.getX_pos_1() - 1);
                this.setY_pos_2(this.getY_pos_1());
                break;
            }
            default: {
                System.out.println("Error");
                break;
            }
        }
    }

    public void genBattleShip() {               //generating Computer position
        reset_Life();
        genXpos_1();
        genYpos_1();
        genDirection();
        gen2ndpos();
        placeValue();
    }

    public void genBattleShip(int x, int y){    //generating Player position
        reset_Life();
        this.setX_pos_1(x);
        this.setY_pos_1(y);
        genDirection();
        gen2ndpos();
        placeValue();
    }

    protected void reset_Life(){
        this.setLife_Point(2);
    }

    protected void Hit() {
        this.setLife_Point((this.getLife_Point() - 1));
    }

    protected boolean sink() {
        if (getLife_Point() == 0) {
            return true;
        }

        return false;
    }

    protected boolean isValue(int x, int y){

        if(this.Arr[x][y] == 1){
            return true;
        }

        return false;
    }

    protected void emptyValue(int x,int y){
        this.Arr[x][y] = 0;
    }

    protected boolean fire(int x, int y){
        if(((x == getX_pos_1() && y ==getX_pos_1()) || (x == getX_pos_2()) && (y == getY_pos_2())) && isValue(x,y)){
            this.Hit();
            this.emptyValue(x,y);
            return true;
        }
        return false;
    }

    protected boolean fire(){
        Random ran = new Random(System.currentTimeMillis());
        int x_ran = ran.nextInt(10), y_ran = ran.nextInt(10);
        if(((x_ran == getX_pos_1() && y_ran ==getX_pos_1()) || (x_ran == getX_pos_2()) && (y_ran == getY_pos_2())) && isValue(x_ran,y_ran)){
            this.Hit();
            this.emptyValue(x_ran,y_ran);
            return true;
        }

        return false;
    }
}


public class Battleship_Array {

    public static void main(String[] args) {

        play();

    }

    protected static void play() {

        BattleShip enemy = new BattleShip();
        BattleShip player = new BattleShip();

        boolean flag = true;
        String Input;
        int x;
        int y;

        PrintMain();

        Input = getEnterString();

        if(Input.equals("0")){
            System.out.println("Bye");
            return;
        }

        while(flag){
            System.out.println("Generating enemy");
            enemy.genBattleShip();
            player = genPlayer();

            /*System.out.println(enemy.getX_pos_1());
            System.out.println(enemy.getY_pos_1());
            System.out.println();
            System.out.println(enemy.getX_pos_2());
            System.out.println(enemy.getY_pos_2());
            System.out.println();*/



            while (true){
                printFire();

                System.out.println("Please Enter Coordinates X (0~9)");
                x = getEnterInt();

                System.out.println();

                System.out.println("Please Enter Coordinates Y (0~9)");
                y = getEnterInt();

                if(enemy.fire(x,y)){
                    printHit();
                    //System.out.println(enemy.getLife_Point());

                    if(enemy.sink()){

                        PrintWin();
                        Input = getEnterString();

                        if(Input.equals("0")){
                            flag = changeFlag();
                            break;
                        }

                        else{
                            printRestart();
                            break;
                        }

                    }
                }
                else{
                    printMiss();
                }


               if(player.fire()){
                    printComputerHit();
                   System.out.println("Your life point is now " + player.getLife_Point());

                    if(player.sink()){

                        PrintLoose();
                        Input = getEnterString();

                        if(Input.equals("0")){
                            flag = changeFlag();
                            break;
                        }

                        else{
                            printRestart();
                            break;
                        }

                    }
                }
                else{
                    printComputerMiss();
                }

            }

        }
    }

    protected static void PrintMain(){
        System.out.println("Welcome to Battleship game");
        System.out.println("Your goal is hit enemy's ship twice and make it sink");

        System.out.println("If you want to play please enter anykey");
        System.out.println("To Exit enter key 0");
    }

    protected static void PrintWin(){
        System.out.println("Congratulation!! You win the game!!");
        System.out.println();
        System.out.println("If you want to play again, Please Enter anykey");
        System.out.println("To Exit enter key 0");
    }

    private static void PrintLoose(){
        System.out.println("Too bad, you lose the game.....");
        System.out.println();
        System.out.println("If you want to play again, Please Enter anykey");
        System.out.println("To Exit enter key 0");
    }

    protected static void printMiss(){
        System.out.println();
        System.out.println("You miss your shot");
        System.out.println("Try Harder!!");
        System.out.println();
    }

    protected static void printComputerMiss(){
        System.out.println();
        System.out.println("Computer Missed!!");
        System.out.println();
    }

    protected static void printHit(){
        System.out.println();
        System.out.println("Hit!!");
        System.out.println();
    }

    protected static void printComputerHit(){
        System.out.println();
        System.out.println("Hit!! you lost your life point!!");
        System.out.println();
    }

    protected static void printRestart(){
        System.out.println();
        System.out.println("Restarting Game");
        System.out.println();
    }

    protected static void printFire(){
        System.out.println("It's Time to fire");
        System.out.println();
    }

    protected static String getEnterString(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter : ");
        String Input = sc.nextLine();

        return Input;
    }

    protected static int getEnterInt(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter : ");
        int Input = sc.nextInt();

        return Input;
    }

    protected static boolean changeFlag(){
        System.out.println();
        System.out.println("Thank you for play");
        return false;
    }

    protected static BattleShip genPlayer(){
        int x,y;

        System.out.println("Please enter Coordinates to generate your ship");

        System.out.println("Please Enter Coordinates X (0~9)");
        x = getEnterInt();

        System.out.println();

        System.out.println("Please Enter Coordinates Y (0~9)");
        y = getEnterInt();

        System.out.println("Generating Player's Ship");
        BattleShip player = new BattleShip();
        player.genBattleShip(x,y);

        return player;
    }

}
