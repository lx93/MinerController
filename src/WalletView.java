import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class WalletView{

	QRHelper helper;
	JButton shutterBtn = new JButton("Capture!");



	public WalletView (){
		helper = new QRHelper();
	}


	// Creates a JFrame which houses QRHelper Panel;
	public void QRScanFrame(){

		JFrame QRFrame = new JFrame();

		shutterBtn.setVisible(true);
		QRFrame.add(shutterBtn,BorderLayout.WEST);

		QRFrame.setSize (800,450);
		helper.showScanner(QRFrame);
		QRFrame.setVisible(true);
		System.out.println("1");
		shutterBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("2");
				shutter();
			}
		});
	}




	public void shutter(){
		System.out.println("hi");
		System.out.println(helper.scanCode());
	}


	public void QRShowFrame(){
		JFrame QRFrame = new JFrame();
		QRFrame.setSize (800,450);
		helper.showScanner(QRFrame);
		QRFrame.setVisible(true);


	}




}
