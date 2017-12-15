import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView {

    QRScanner scanner = new QRScanner();
    SettingsController controller = new SettingsController();
    JButton shutterBtn = new JButton("Capture!");


    public void QRScanFrame() {
        JFrame QRFrame = new JFrame();
        shutterBtn.setVisible(true);
        QRFrame.add(shutterBtn, BorderLayout.WEST);
        QRFrame.setSize (800,450);
        scanner.showScanner(QRFrame);
        QRFrame.setVisible(true);
        shutterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String address = controller.scanCode();
                controller.addressWrite(address);
            }
        });
    }
}