import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class WalletController {


	WalletView view = new WalletView();
	Text notification = new Text ();

	public void sendETH(){
		//view.controller=this;
		view.QRScanFrame(notification);
	}


	public void sendBTC(){
		//view.controller=this;
		view.QRScanFrame(notification);
	}


	public void recETH(){
		view.displayQRAddr();
	}

	public void recBTC(){
		view.displayQRAddr();
	}

	public void updateNotification(){
		notification.setText("Payment sent successfully");
	}


}
