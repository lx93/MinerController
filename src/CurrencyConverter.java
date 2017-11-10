
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

public class CurrencyConverter {
	

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		CurrencyConverter http = new CurrencyConverter();

		System.out.println("\nCurrencyExchanging - Send Http POST request");
		http.sendPost();

	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://cors.shapeshift.io/sendamount";
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
		String urlParameters = "amount=1&withdrawal="+getWithdrawal()+"&pair="+getPair();
		// Including a return address gives 500 Error Code: Internal Server Error
		// +"&returnAddress="+getReturnAddr();


		
		
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
		return "AAAAAAAAAAAA";
	}
	public String getReturnAddr() {
		return "BBBBBBBBBBB";
	}
	
	public String getPair() {
		return "btc_eth";
	}

}
