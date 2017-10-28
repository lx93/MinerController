import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

public class MinerControllerGUI extends JPanel implements ActionListener{
	final static boolean DEBUG_BASH_PROGRAM = false;
	
	public JButton mineButton;
	public String directory = "/home/isaiahminer0/Desktop/Claymore/start.bash";
	Process p;
	
//class constructor
	public MinerControllerGUI() throws IOException, ParserConfigurationException {
		super(new BorderLayout(2, 2));
		JLabel intro = new JLabel("<html>Isaiah Miner</html>");
		add(intro, BorderLayout.NORTH);

		menu();
		nanoParse();
	}
	
	
//implementing ActionListener
	public void actionPerformed (ActionEvent e) {
		Object source = e.getSource();
		if (source == mineButton) {
			if (mineButton.getText().equals("Start Mining")) {
				mineButton.setBackground(Color.red);
				mineButton.setText("Stop Mining");
				try{
					p = new ProcessBuilder ("/bin/bash", 
							getPathToMiningProgram()).start();
					claymoreReader();
				}
				catch(IOException f){f.printStackTrace();}
				}
			else {
				mineButton.setText("Start Mining");
				mineButton.setBackground(Color.green);
			}
		}
	}
	
	private String getPathToMiningProgram() {
		if (DEBUG_BASH_PROGRAM) {
			return "bash/test-print.sh";
		} else {
			return directory;
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
	
	
	
//this function creates a JPanel menu
	public void menu() {
		JPanel menu = new JPanel (new GridLayout(3,3));
		menu.add(mineButton = mine());
		add(menu,"Center");
	}
	
//this function creates a JPanel for parsed wallet info
	public void walletView() {
		JPanel wallet = new JPanel (new GridLayout(3,3));
		wallet.add();shit
	}

	
	// This function parses the nanopool JSON API
	public double nanoParse() throws IOException, ParserConfigurationException {
		JSONParser parser = new JSONParser();
		URLConnection connection = new URL("https://api.nanopool.org/v1/eth/balance/0x36f536f54ccec727f861d6622e465003a731fe41").openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		BufferedReader bufferedReader = null;
		try {
			connection.connect();
			bufferedReader  = new BufferedReader(new InputStreamReader(connection.getInputStream(),
					Charset.forName("UTF-8")));
		}
		catch (java.net.UnknownHostException e){
			System.out.println("No Internet.");
			return 0;
		}

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
		    sb.append(line);
		}

			try {
				Object obj = parser.parse(sb.toString());
				JSONObject jsonObject = (JSONObject) obj;
				Double balance = (Double) jsonObject.get("data");
				System.out.println(balance);
				return (balance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("JSON File invalid");
				return 0;
			}
		}


//this function use ActionListener to listen for user input
	public JButton mine() {
		JButton button = new JButton("Start Mining");
		button.setBackground(Color.green);
		button.addActionListener(this);
		return button;
	}
}


