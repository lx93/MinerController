import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class QRScanner {

    Webcam webcam = null;

    public QRScanner(){
        // Initialize Camera
        super();
        try { webcam = Webcam.getDefault(); }
        catch(NullPointerException e){}
    }

    public String scanCode() throws NullPointerException{
        Result result = null;
        BufferedImage image = null;

        try { Thread.sleep(2000); } catch (Exception e) {}
        if ((image = webcam.getImage()) == null) {
        }

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Map<DecodeHintType,Object> pureHints = new EnumMap<>(DecodeHintType.class);
        pureHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        try {
            result = new MultiFormatReader().decode(bitmap, pureHints);
        } catch (NotFoundException e) {
            e.printStackTrace();
            System.out.println("didnt find a QR code");
        }

        if (result != null) {
            return result.getText();
        }
        else{
            return ("lalalalal the QR code and your face is messed up");
        }
    }


    // this creates a Panel to be fitted into the JFrame passed in as an argument
    public void showScanner(JFrame window){
        webcam.open();
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        window.add(panel);
        window.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){webcam.close();}
        });
    }

}
