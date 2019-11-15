// 마이크로소프트사 : 윈도우 10에서 사용될 프린터는 다음의 규격을 준수해야 합니다.
//                   Printable 을 구현해 주세요.
// 아래아한글, MS Word : 인쇄는 print만 호출하면 되는군요. 알겠습니다.
// 삼성, 앱손 : 프린터는 Printable 인터페이스 규격에 맞춰서 만들겠습니다.


import java.awt.print.Printable;

interface Printable02{
    void Print(String doc);
}

interface ColorPrintable02 extends Printable02{
    void PrintCMYK(String doc);
}

class PrnColorDrvSamsung implements ColorPrintable02{
    public void Print(String doc){
        System.out.println(doc + "\nFrom Samsung(2) : Black-White Ver");
    }

    public void PrintCMYK(String doc){
        System.out.println(doc + "\nFrom Samsung(2) : Color Ver");
    }
}

class PrnColorDrvEpson implements ColorPrintable02{

    public void Print(String doc){
        System.out.println(doc + "\nFrom Epson(2) : Black-White Ver");
    }

    public void PrintCMYK(String doc){
        System.out.println(doc + "\nFrom Epson(2) : Color Ver");
    }
}

public class G03_Polymorphism {

    public static void main(String[] args) {
        String doc = "프린터로 출력을 합니다.";

        Printable01 prn1 = new PrnDrvSamsung();
        prn1.Print(doc);

        ColorPrintable02 prn2 = new PrnColorDrvSamsung();
        prn2.Print(doc);
        prn2.PrintCMYK(doc);

        ColorPrintable02 prn3 = new PrnColorDrvEpson();
        prn3.Print(doc);
        prn3.PrintCMYK(doc);
    }
}