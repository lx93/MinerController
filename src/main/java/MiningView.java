
import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.io.*;

public class MiningView {

    public MiningController controller = new MiningController();
    public Button mineButton = new Button();
    public Text balanceText = new Text();
    public Text hashrateText = new Text();
    public Text walletText = new Text();
    public TextField claymoreText = new TextField();
    public boolean buttonStatus = true;
    SettingsController settingsController = new SettingsController();




	//class constructor
	public MiningView() throws IOException, ParserConfigurationException {
		updateBalance();
		updateHashrate();
		updateWallet();
	}



//this function updates wallet address
    public void updateWallet(){
	    new Thread(new Runnable() {
	        public void run(){
	            for (int i=0; i < 200000; i++){
                    //initial run before constant update every minute
                    String wallet = settingsController.returnMiningAddress();
                    walletText.setText("current wallet: " + settingsController.returnMiningAddress());
                }
            }
        }).start();
    }



	// This function updates balance section on the screen
	public void updateBalance(){
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 200000; i++) {
					//initial run before constant update every minute
					String balance = controller.returnBalance();
					balanceText.setText(balance + " ETH");

					//this is just for demo purposes
					try { Thread.sleep(60000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

					Platform.runLater(new Runnable() {
						public void run() {
							// we are now back in the EventThread and can update the GUI
							balanceText.setText(balance + " ETH");
						}
					});
				}
			}
		}).start();
	}



	public void updateHashrate(){
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 200000; i++) {
					//initial run before constant update every minute
					String hashrate = controller.returnHashrate();
					hashrateText.setText("current hashrate: " + hashrate + " MH/s");

					//this is just for demo purposes
					try { Thread.sleep(60000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

					Platform.runLater(new Runnable() {
						public void run() {
							// we are now back in the EventThread and can update the GUI
							hashrateText.setText("current hashrate: " + hashrate + " MH/s");
						}
					});
				}
			}
		}).start();
	}


	public void updateClaymoreStatus() throws IOException {
	}





	//Controls the state of the mineButton
	public void mineButtonController () throws IOException {
		if (buttonStatus) {
			buttonStatus = false;
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #ef8973;");
			mineButton.setText("Stop Mining");
			controller.claymoreStarter();
		}
		else{
			buttonStatus = true;
			mineButton.setText("Start Mining");
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		}
	}



}


