interface Viewable3{
    @Deprecated
    public void showIt(String str);

    public void brShowIt(String str);
}

class Viewer3 implements Viewable3{

    @Override
    @SuppressWarnings("deprecation")
    public void showIt(String str) {
        System.out.println(str);
    }

    @Override
    public void brShowIt(String str) {
        System.out.println('[' + str + ']');
    }
}
public class D3_AtSuppressWarnings {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        Viewable2 view = new Viewer2();
        view.showIt("Hello Annotations");
        view.brShowIt("Hello Annotations");
    }
}
