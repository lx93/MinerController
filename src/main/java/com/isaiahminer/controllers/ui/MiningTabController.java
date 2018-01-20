package com.isaiahminer.controllers.ui;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import com.isaiahminer.controllers.crypto.MiningController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MiningTabController {

    @FXML public Button mineButton = new Button();
    @FXML public Text balanceText = new Text();
    @FXML public Text hashrateText = new Text();
    @FXML public Text walletText = new Text();
    @FXML public Text profitText = new Text();
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
	public MiningTabController() {
		super();
	}

    @FXML
    public void initialize() {
		//System.out.println(MiningTabController.class.getName() + " is being initialized");
		updateWalletUI();
    }

    /**
	 * Updates wallet UI
	 */
    public void updateWalletUI() {
    		new Thread() {
    			@Override
    			public void run() {
				while (true) {
					final String wallet = "Current Wallet: " + controller.returnMiningAddress().substring(9);
					final String hashrate = "Current Hashrate: " + miningController.returnHashrate() + " MH/s";
					final String profit = "Daily Profit: $" + miningController.returnProfit();
					final String balanceString = miningController.returnBalance();
					final String usd = miningController.returnPrice();
					String balance;
					try {
						double currentBalance = Double.parseDouble(balanceString);
						double rate = Double.parseDouble(usd);
						balance =  String.format("%s milliether ≈ $%.2f", balanceString, (currentBalance * rate / 1000));
					} catch  (NumberFormatException e) {
						balance = balanceString + " milliether"; 
					}
					String totalBalance = balance;
					Platform.runLater(() -> {
						profitText.setText(profit);
						walletText.setText(wallet);
						balanceText.setText(totalBalance);
						hashrateText.setText(hashrate);
					});
					try { Thread.sleep(3000); } catch (InterruptedException e) {}
				}
			}
		}.start();
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