
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
    public TextField hashrateTextfield = new TextField();
    public TextField tempTextfield = new TextField();
    public TextField fanTextfield = new TextField();
    public boolean buttonStatus = true;
    public SettingsController controller = new SettingsController();

	//class constructor
	public MiningView() throws IOException, ParserConfigurationException {
		updateBalance();
		updateHashrate();
		updateWallet();
		updateClaymoreStatus();
		updateFanText();
		updateHashrateText();
		updateTempText();
	}


//this function updates wallet address
    public void updateWallet(){
	    new Thread(new Runnable() {
	        public void run(){
	            while (true) {
                    walletText.setText("current wallet: " + controller.returnMiningAddress().substring(9));
                    try {
                        Thread.sleep(2000); } catch (Exception e) {}
                }
            }
        }).start();
    }


	// This function updates balance section on the screen
	public void updateBalance(){
		new Thread(new Runnable() {
			public void run() {
                try { Thread.sleep(1000); } catch (Exception e) {}
                while (true) {
                    String balance = miningController.returnBalance();
					balanceText.setText(balance + " milliether");
                    try { Thread.sleep(60000); } catch (Exception e) {}
				}
			}
		}).start();
	}


    // This function updates hashrate section on the screen
	public void updateHashrate(){
		new Thread(new Runnable() {
			public void run() {
                while (true) {
                    try { Thread.sleep(1000); } catch (Exception e) {}
                    String hashrate = miningController.returnHashrate();
                    hashrateText.setText("current hashrate: " + hashrate + " MH/s");
                    try { Thread.sleep(60000); } catch (Exception e) {}
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
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            }
        }.start();
	}

	public void updateHashrateText() throws IOException {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    hashrateTextfield.setText(MiningController.gpu0speed);
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            }
        }.start();
    }

    public void updateFanText() throws IOException {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    fanTextfield.setText(MiningController.gpu0fan);
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            }
        }.start();
    }

    public void updateTempText() throws IOException {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    tempTextfield.setText(MiningController.gpu0temperature);
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