public class WalletController {


	WalletView view = new WalletView();

	public void sendETH(){
		view.QRScanFrame();
	}


	public void sendBTC(){
		view.QRScanFrame();
	}


	public void recETH(){
		view.QRShowFrame();

	}

	public void recBTC(){
		view.QRShowFrame();
	}

}
