

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class ExchangeController {



	public String eth_btcRate(){
		String URLAddress = "https://shapeshift.io/rate/eth_btc";
		if (MinerControllerApplication.DEBUG) {
			return jsonParse("{\"pair\":\"eth_btc\",\"rate\":\"1\"}","rate");
		}
		else{
			return jsonParse(jsonToString(connectAPI(URLAddress)),"rate");
		}
	}


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

		if (dataType == "rate") {
			try {
				Object obj = parser.parse(json);
				JSONObject jsonObject = (JSONObject) obj;
				String balance = String.valueOf(jsonObject.get(dataType));
				return (balance);
			} catch (ParseException e) {
				System.out.println("JSON File invalid");
				return "0";
			}
		}
		else {return "you are a prick";}
	}









































	//Written at Hackathon


	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		ExchangeController http = new ExchangeController();

		System.out.println("\nCurrency Converting - Send Http POST request");
		http.sendPost();

	}






	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://shapeshift.io/shift";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// Get withdrawal and return address
		String withdrawal = getWithdrawal();
		String return_addr = getReturnAddr();


		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


		// ***** Wont read the exchange pair!!
		String urlParameters = "withdrawal="+getWithdrawal()+", pair=btc_ltc, returnAddress="+getReturnAddr();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}

	public String getWithdrawal() {
		return "AAAAAAAAAAAAA";
	}
	public String getReturnAddr() {
		return "BBBBBBBBBBB";
	}

}