package com.isaiahminer.controllers.ui;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import com.isaiahminer.controllers.crypto.MiningController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MiningView {

    @FXML public Button mineButton = new Button();
    @FXML public Text balanceText = new Text();
    @FXML public Text hashrateText = new Text();
    @FXML public Text walletText = new Text();
    @FXML public TextField claymoreText = new TextField();
    @FXML public TextField hashrateTextfield = new TextField();
    @FXML public TextField tempTextfield = new TextField();
    @FXML public TextField fanTextfield = new TextField();
    public boolean buttonStatus = true;
    public SettingsController controller = new SettingsController();
    public MiningController miningController = new MiningController(this);

	/**
	 * Public Constructor
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public MiningView() {
		super();
	}

    @FXML
    public void initialize() {
		System.out.println(MiningView.class.getName() + " is being initialized");
    		//updateBalance();
		//updateHashrate();
		//updateWallet();
		//updateClaymoreStatus();
    }
	/**
	 * Updates wallet address
	 */
    public void updateWallet() {
    		Platform.runLater(new Runnable() {
			@Override
			public void run() {
	            while (true) {
                    walletText.setText("Current Wallet: " + controller.returnMiningAddress().substring(9));
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                }
			}
    		});
    }


	/**
	 * Updates balance section on the screen
	 */
	public void updateBalance() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
                while (true) {
                    String balance = miningController.returnBalance();
					balanceText.setText(balance + " milliether");
                    try { Thread.sleep(60000); } catch (InterruptedException e) {}
				}
			}
		});
	}


    /**
	 * Updates hashrate section on the screen
	 */
	public void updateHashrate() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
                while (true) {
                    String hashrate = miningController.returnHashrate();
                    hashrateText.setText("current hashrate: " + hashrate + " MH/s");
                    try { Thread.sleep(60000); } catch (InterruptedException e) {}
				}
			}
		});
	}

    /** 
     * Controls the state of the mineButton
     * @throws IOException
     */
	public void mineButtonController() throws IOException {
		if (buttonStatus) {
			buttonStatus = false;
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #ef8973;");
			mineButton.setText("Stop Mining");
			miningController.claymoreStarter();
		} else {
			buttonStatus = true;
			mineButton.setText("Start Mining");
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		}
	}
}