package com.isaiahminer.controllers.ui;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SettingsView {

    QRScanner scanner = new QRScanner();
    SettingsController controller = new SettingsController();
    JButton shutterBtn = new JButton("Capture!");

    public void QRScanFrame() {
        JFrame QRFrame = new JFrame();
        shutterBtn.setVisible(true);
//        QRFrame.add(shutterBtn, BorderLayout.WEST);
        QRFrame.setSize (800,450);
        scanner.showScanner(QRFrame);
        QRFrame.setVisible(true);
        final String code = controller.scanCode();
        controller.addressWrite(code);
        QRFrame.dispose();

//        shutterBtn.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e)
//            {
//                String address = controller.scanCode();
//                controller.addressWrite(address);
//            }
//        });
    }
}
