
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.io.*;

public class MiningView {

	final static boolean DEBUG = false;


	public static MiningController controller = new MiningController();
	public Button mineButton = new Button();
	public Text balanceText = new Text();
	public Text hashrateText = new Text();
	public TextField claymoreText = new TextField();


	public boolean buttonStatus = true;


	public String directory = "/home/isaiahminer0/Desktop/Claymore/start.bash";
	Process p;


	//class constructor
	public MiningView() throws IOException, ParserConfigurationException {
		updateBalance();
		updateHashrate();
	}

	private String getPathToMiningProgram() {

		if (DEBUG) {
			return "bash/test-print.sh";
		} else {
			return directory;
		}
	}

	private String returnBalance(){
		String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/0x36f536f54ccec727f861d6622e465003a731fe41";

		if (DEBUG) {
			return controller.jsonParse("{\"status\":true,\"data\":0.0500000000}","balance");
		}
		else{
			return controller.jsonParse(controller.jsonToString(controller.connectAPI(URLAddress)),"balance");
		}
	}

	private String returnHashrate(){
		String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/0x36f536f54ccec727f861d6622e465003a731fe41";
		if (DEBUG) {
			return controller.jsonParse("{\"status\":true,\"data\":500}","hashrate");
		}
		else{
			return controller.jsonParse(controller.jsonToString(controller.connectAPI(URLAddress)),"hashrate");
		}

	}



	// This function updates balance section on the screen
	public void updateBalance(){
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 20000; i++) {

					//this is just for demo purposes
					try { Thread.sleep(2000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

					Platform.runLater(new Runnable() {
						public void run() {
							// we are now back in the EventThread and can update the GUI
							balanceText.setText(returnBalance() + " ETH");
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

					//this is just for demo purposes
					try { Thread.sleep(2000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

					Platform.runLater(new Runnable() {
						public void run() {
							// we are now back in the EventThread and can update the GUI
							hashrateText.setText("current hashrate: " + returnHashrate() + " MH/s");
						}
					});
				}
			}
		}).start();
	}


	public void updateClaymoreStatus(){

	}





	//Controls the state of the mineButton
	public void mineButtonController (){
		if (buttonStatus) {
			buttonStatus = false;
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #ef8973;");
			mineButton.setText("Stop Mining");
			try {
				p = new ProcessBuilder("/bin/bash",
						getPathToMiningProgram()).start();
				claymoreReader();
			} catch (IOException f) {f.printStackTrace();}
		}
		else{
			buttonStatus = true;
			mineButton.setText("Start Mining");
			mineButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		}
	}

	


	// this function creates a BufferedReader that reads from Claymore output to print in console
	public void claymoreReader () throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}







}


