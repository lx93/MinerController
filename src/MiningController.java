import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;



public class MiningController {
	// this function establish connection to various external APIs
	public URLConnection connectAPI(String URLAddress){

		URLConnection connection = null;

		try{
			connection = new URL(URLAddress).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("URL invalid");}

		try {
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection to external API failed"); }

		return connection;
	}


	//this function grabs JSON file from Nanopool API and converts the balance entry into a string
	public String jsonToString(URLConnection connection) {

		BufferedReader bufferedReader = null;

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
	public String jsonParse(String json, String dataType) {

		JSONParser parser = new JSONParser();

		if (dataType == "balance") {
			try {
				Object obj = parser.parse(json);
				JSONObject jsonObject = (JSONObject) obj;
				jsonObject = (JSONObject) jsonObject.get("data");
				String balance = String.valueOf(jsonObject.get("balance"));
				return (balance);
			} catch (ParseException e) {
				System.out.println("JSON File invalid");
				return "0";
			}
		}
		else if (dataType == "hashrate") {
			try {
				Object obj = parser.parse(json);
				JSONObject jsonObject = (JSONObject) obj;
				jsonObject = (JSONObject) jsonObject.get("data");
				String hashrate = String.valueOf(jsonObject.get("hashrate"));
				return (hashrate);
			} catch (ParseException e) {
				System.out.println("JSON File invalid");
				return "0";
			}
		}

		else {return "you are a prick";}
	}
}
