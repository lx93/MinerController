

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ExchangeController {


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