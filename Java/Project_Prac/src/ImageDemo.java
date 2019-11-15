import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.MatOfByte;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageDemo {

    static BufferedImage img;
    static BufferedImage GrayImg;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {

        //Mat image;
        String loc = "D:\\Taejun\\Java\\Project_Prac\\Lenna.png";
        String output = "D:\\Taejun\\Java\\Project_Prac\\output.png";

        Mat source = Imgcodecs.imread(loc);

        //BufferedImage img = new BufferedImage(source);

        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".png", source, byteMat);

        //Image img = new Image(new ByteArrayInputStream(byteMat.toArray()));
        img = Mat2BufferedImage(source);

        displayImage(img);

        Mat dest = new Mat();

        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);

        //Imgcodecs.imwrite(output, dest);

        GrayImg = Mat2BufferedImage(dest);

        displayImage(GrayImg);

    }

    static BufferedImage Mat2BufferedImage(Mat matrix)throws Exception {
        MatOfByte mob=new MatOfByte();
        Imgcodecs.imencode(".png", matrix, mob);
        byte ba[]=mob.toArray();

        BufferedImage bi=ImageIO.read(new ByteArrayInputStream(ba));
        return bi;
    }

    static public void displayImage(Image disImg) {

        ImageIcon icon=new ImageIcon(disImg);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(disImg.getWidth(null)+50, disImg.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}