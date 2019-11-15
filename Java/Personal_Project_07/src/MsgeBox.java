import javax.swing.*;
import java.awt.*;

public class MsgeBox {

    public void messageBox(Object obj , String message){
        JOptionPane.showMessageDialog( (Component)obj , message);
    }

}
