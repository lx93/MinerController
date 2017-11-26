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
import java.util.EnumMap;
import java.util.Map;

public class SettingsController {
    QRScanner scanner = new QRScanner();
    String address = "0x36f536f54ccec727f861d6622e465003a731fe41";

//This section is for the change address section
    public SettingsController(){
    }

    public void scanCode(){
        address = scanner.scanCode();
    }

    public String returnMiningAddress(){
        return address;
    }
//--------------------------------------------------------------------------------------
}
