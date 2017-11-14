import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javafx.scene.text.Text;

public class WalletView{

	JButton shutterBtn = new JButton("Capture!");
	JButton submitBtn = new JButton("Submit!");
	TextField address = new TextField("Address");
	TextField amount = new TextField("Amount");

	Text notification = new Text ();

	public static WalletController controller = CryptoHelper.getKeyPair();



	public WalletView (){

	}


	// Creates a JFrame which houses WalletControl Panel;
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
		controller.showScanner(QRFrame);
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
		address.setText(controller.scanCode().substring(8));
	}


	public void displayQRAddr(String pubkey){
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(controller.generateQR(controller.getPubKey()))));
		frame.getContentPane().add(new JLabel(controller.getPubKey()));
		frame.setPreferredSize(new java.awt.Dimension(325,325));
		frame.pack();
		frame.setVisible(true);


		controller.generateQR(controller.getPubKey());
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
		displayQRAddr(controller.getPubKey());
	}

	public void updateNotification(){
		notification.setText("Payment sent successfully");
	}





}
