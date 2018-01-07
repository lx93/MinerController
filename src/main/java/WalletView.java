import javax.swing.*;
import java.awt.*;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bitcoinj.wallet.Wallet;


public class WalletView {

	String address = new String("recipient address");
    public Text addressText = new Text();


    Text notification = new Text ();

	public static WalletController walletController = BTCHelper.getKeyPair();
	QRScanner scanner = new QRScanner();


	// Creates a JFrame which houses WalletControl Panel;
    //
    public void QRScanFrame(Text notification){
		JFrame QRFrame = new JFrame();
		QRFrame.setSize (555,480);
		scanner.showScanner(QRFrame);
		QRFrame.setVisible(true);
        address = walletController.scanCode();
        QRFrame.dispose();
    }

    public void sendETHFrame (){
        try{
            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("sendBTCFrame.fxml"));
            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Send ETH");
            stage.setScene(new Scene(root1));
            stage.show();
            new Thread (new Runnable() {
                @Override
                public void run() {
                    addressText.getScene().setRoot(root1);
                    addressText.setText("hi!");
                    System.out.println("wtf");
                }
            }).start();        }
        catch(Exception e){
            System.out.println("cannot open a new transaction page");
        }
    }


	public void sendBTCFrame (){
        try{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sendBTCFrame.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Send BTC");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.println("cannot open a new transaction page");
        }
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
		QRScanFrame(notification);
		sendETHFrame();
	}


	public void sendBTC(){
		//view.controller=this;
		QRScanFrame(notification);
        sendBTCFrame();
    }


	public void recETH()
	{
		displayQRAddr(walletController.getPubKey());
	}

	public void recBTC()
	{
		displayQRAddr(walletController.getPubKey());
	}

	public void updateNotification(){
		notification.setText("Payment sent successfully");
	}

}
