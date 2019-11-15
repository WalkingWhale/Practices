public class G04_Polymorphism {

    public static void main(String[] args) {
        String doc = "프린터로 출력을 합니다";

        ColorPrintable02 prn1 = new PrnColorDrvSamsung();
        prn1.Print(doc);
        prn1.PrintCMYK(doc);

        ColorPrintable02 prn2 = new PrnColorDrvEpson();
        prn2.Print(doc);
        prn2.PrintCMYK(doc);

        // 자식을 부모에게 넣을 수는 있으나, 같은 레벨의 클래스에 넣을 수는 없다.
        //PrnColorDrvSamsung prn3 = new PrnColorDrvEpson();
    }
}
