import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class WalletView{

	QRHelper helper;
	//public WalletController controller;
	JButton shutterBtn = new JButton("Capture!");
	JButton submitBtn = new JButton("Submit!");
	TextField address = new TextField("Address");
	TextField amount = new TextField("Amount");



	public WalletView (){
		helper = new QRHelper();
		//controller = new WalletController();
	}


	// Creates a JFrame which houses QRHelper Panel;
	public void QRScanFrame(Text notification){

		JFrame QRFrame = new JFrame();

		shutterBtn.setVisible(true);
		QRFrame.add(shutterBtn,BorderLayout.WEST);
		QRFrame.add(submitBtn,BorderLayout.EAST);

		address = new TextField("Address");
		amount = new TextField("Payment");

		QRFrame.add(address,BorderLayout.NORTH);
		QRFrame.add(amount,BorderLayout.SOUTH);
		QRFrame.setSize (800,450);
		helper.showScanner(QRFrame);
		QRFrame.setVisible(true);

		shutterBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				shutter();
			}
		});

		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				QRFrame.dispose();
				notification.setText("HII");
				notification.setVisible(true);
//				System.out.println(notification);
				//controller.updateNotification();


			}
		});

	}




	public void shutter(){
		address.setText(helper.scanCode().substring(8));
	}


	public void displayQRAddr(){
		helper.generateQR(MinerControllerApplication.key.getPubKey());


	}




}
