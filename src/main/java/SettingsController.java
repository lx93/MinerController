import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.Map;

public class SettingsController {
    QRScanner scanner = new QRScanner();
    String address;

//This section is for the change address section
    public SettingsController(){
    }

    public boolean addressWrite(String newAddress){
        try {
            PrintWriter out = new PrintWriter("ETHaddress");
            out.println(newAddress);
            System.out.println("file printed");
        } catch (FileNotFoundException e) {
            System.out.println("ETHaddress file not found");
        }

        return true;
    }

    public String addressRead(){
        try (BufferedReader br = new BufferedReader(new FileReader("ETHaddress"))) {
            address = br.readLine();

    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ETHaddress file not found");
            return null;
        }
        return address;
    }

    public String scanCode(){
        return scanner.scanCode();
    }

    public String returnMiningAddress(){
        if (addressRead()!=null){
//            System.out.println(addressRead());
            return addressRead();
        }
        else{
//            System.out.println("address unavailable");
            return "address unavailable";
        }
    }
//--------------------------------------------------------------------------------------
}
