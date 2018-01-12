
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MiningView {

    public MiningController miningController = new MiningController();
    public Button mineButton = new Button();
    public Text balanceText = new Text();
    public Text hashrateText = new Text();
    public Text walletText = new Text();
    public TextField claymoreText = new TextField();
    public boolean buttonStatus = true;
    public SettingsController controller = new SettingsController();

	//class constructor
	public MiningView() throws IOException, ParserConfigurationException {
		updateBalance();
		updateHashrate();
		updateWallet();
		updateClaymoreStatus();
	}



//this function updates wallet address
    public void updateWallet(){
	    new Thread(new Runnable() {
	        public void run(){
	            while (true) {

                    //initial run before constant update every minute
                    walletText.setText("current wallet: " + controller.returnMiningAddress().substring(9));
//                    System.out.println("init print");

                    //this is just for demo purposes, 60000 = one minute
                    try {
                        Thread.sleep(2000); } catch (Exception e) {}


//                    Platform.runLater(new Runnable() {
//                        public void run() {
//                            // we are now back in the EventThread and can update the GUI
//                            walletText.setText("current wallet: " + controller.returnMiningAddress());
//                            System.out.println("thread print");
//                        }
//                    });
                }
            }
        }).start();
    }


	// This function updates balance section on the screen
	public void updateBalance(){
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					//initial run before constant update every minute
                    String balance = miningController.returnBalance();
					balanceText.setText(balance + " milliether");

					//this is just for demo purposes, 60000 = one minute
					try { Thread.sleep(60000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

//					Platform.runLater(new Runnable() {
//						public void run() {
//							// we are now back in the EventThread and can update the GUI
//							balanceText.setText(balance + " ETH");
//						}
//					});
				}
			}
		}).start();
	}


    // This function updates hashrate section on the screen
	public void updateHashrate(){
		new Thread(new Runnable() {
			public void run() {
                while (true) {
					//initial run before constant update every minute
                    String hashrate = miningController.returnHashrate();
                    hashrateText.setText("current hashrate: " + hashrate + " MH/s");

                    //this is just for demo purposes, 60000 = one minute
					try { Thread.sleep(60000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

//					Platform.runLater(new Runnable() {
//						public void run() {
//							// we are now back in the EventThread and can update the GUI
//							hashrateText.setText("current hashrate: " + hashrate + " MH/s");
//						}
//					});
				}
			}
		}).start();
	}


	public void updateClaymoreStatus() throws IOException {
		new Thread() {
			@Override
			public void run() {
                while (true) {
                    //initial run before constant update every minute
                    claymoreText.setText(MiningController.claymoreStats);
                    //TODO  MiningController.gpu0fan, MiningController.gpu0speed, and MiningController.gpu0temperature
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            }
        }.start();
	}



	//Controls the state of the mineButton
	public void mineButtonController () throws IOException {
		if (buttonStatus) {
			buttonStatus = false;
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #ef8973;");
			mineButton.setText("Stop Mining");
			miningController.claymoreStarter();
		}
		else{
			buttonStatus = true;
			mineButton.setText("Start Mining");
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		}
	}


}