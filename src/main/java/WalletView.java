import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javafx.scene.text.Text;

public class WalletView{

	TextField address = new TextField("Address");
	TextField amount = new TextField("Amount");

	Text notification = new Text ();

	public static WalletController walletController = BTCHelper.getKeyPair();
	QRScanner scanner = new QRScanner();


	// Creates a JFrame which houses WalletControl Panel;
	public void QRScanFrame(Text notification){

		JFrame QRFrame = new JFrame();
		JButton shutterBtn = new JButton("Capture!");
		JButton submitBtn = new JButton("Submit!");
		QRFrame.add(shutterBtn,BorderLayout.WEST);
		QRFrame.add(submitBtn,BorderLayout.EAST);
		QRFrame.add(address,BorderLayout.NORTH);
		QRFrame.add(amount,BorderLayout.SOUTH);
		QRFrame.setSize (800,450);
		scanner.showScanner(QRFrame);
		QRFrame.setVisible(true);



		shutterBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				address.setText(walletController.scanCode().substring(8));			}
		});



		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				QRFrame.dispose();
				notification.setText("HII");
				notification.setVisible(true);
			}
		});

	}


	public void displayQRAddr(String pubkey){
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(walletController.generateQR(walletController.getPubKey()))));
		frame.getContentPane().add(new JLabel(walletController.getPubKey()));
		frame.setPreferredSize(new java.awt.Dimension(325,325));
		frame.pack();
		frame.setVisible(true);

		walletController.generateQR(walletController.getPubKey());
	}



	public void sendETH(){
		//view.controller=this;
		//QRScanFrame(notification);
	}


	public void sendBTC(){
		//view.controller=this;
		QRScanFrame(notification);
	}


	public void recETH()
	{
		//displayQRAddr(controller.getPubKey());
	}

	public void recBTC()
	{
		displayQRAddr(walletController.getPubKey());
	}

	public void updateNotification(){
		notification.setText("Payment sent successfully");
	}


}
