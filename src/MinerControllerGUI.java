import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.EventListener;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.*;

public class MinerControllerGUI{
	final static boolean DEBUG = true;

	public Button mineButton = new Button();
	public Text balanceText = new Text();
	public boolean buttonStatus = true;


	public String directory = "/home/isaiahminer0/Desktop/Claymore/start.bash";
	Process p;


	//class constructor
	public MinerControllerGUI() throws IOException, ParserConfigurationException {
		updateBalance();
	}

	private String getPathToMiningProgram() {
		if (DEBUG) {
			return "bash/test-print.sh";
		} else {
			return directory;
		}
	}

	private String testJson(){
		if (DEBUG) {
			return (String.valueOf(nanoParse("{\"status\":true,\"data\":0.0500000000}")));
		}
		else{
			return String.valueOf((nanoParse(jsonToString())));
		}
	}


	// This function updates balance section on the screen
	public void updateBalance(){
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 2000; i++) {

					//this is just for demo purposes
					//try { Thread.sleep(2000); } catch (Exception e) {}

					// we are not in the event thread currently so we should not update the UI here
					// this is a good place to do some slow, background loading, e.g. load from a server or from a file system

					Platform.runLater(new Runnable() {
						public void run() {
							// we are now back in the EventThread and can update the GUI
							balanceText.setText(testJson() + " ETH");
						}
					});
				}
			}
		}).start();
	}

	//Controls the state of the
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


	//this function grabs JSON file from Nanopool API and converts the balance entry into a string
	public String jsonToString() {
		URLConnection connection = null;
		BufferedReader bufferedReader = null;

		try{
			connection = new URL("https://api.nanopool.org/v1/eth/balance/0x36f536f54ccec727f861d6622e465003a731fe41").openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("URL invalid");}

		try {
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Check internet connectivity"); }

		try {
			bufferedReader  = new BufferedReader(new InputStreamReader(connection.getInputStream(),
					Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Check internet connectivity"); }

		String balance;

		try {
			balance = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			balance = "Cannot retrieve your nanopool balance";
		}

		return balance;

	}

	// This function parses the string of nanopool balance JSON
	public double nanoParse(String json) {

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(json);
			JSONObject jsonObject = (JSONObject) obj;
			Double balance = (Double) jsonObject.get("data");
			return (balance);
		} catch (ParseException e) {
			System.out.println("JSON File invalid");
			return 0;
			}
		}
}


