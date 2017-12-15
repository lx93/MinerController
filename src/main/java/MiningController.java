import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;


public class MiningController {


	SettingsController controller = new SettingsController();
    public static String claymoreStats;

//The following variables are all configurations for Claymore
    String epool = "eth-us-east1.nanopool.org:9999";
    String ewal = controller.returnMiningAddress();
    String epsw = "x";
    String tt = "75";
    String fanmin = "60";
    String fanmax = "90";
    String dcri = "4";
    String cclock = "1165";
    String mclock = "2150";
    String cvddc = "810";
    String mvddc = "810";




    public MiningController() throws IOException{
	}


	public String getPathToMiningProgram() {
		if (MinerControllerApplication.DEBUG) {
			return "src/main/resources/bash/test-print.sh";
		} else {
			return "src/main/resources/Claymore/ethdcrminer64";
		}
	}


	public String returnBalance(){
		String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/"+ewal;

		if (MinerControllerApplication.DEBUG) {
            return String.valueOf(Double.parseDouble((jsonParse("{\"status\":true,\"data\":{\"hashrate\":8000,\"balance\":50}}","balance")))*1000);
		}
		else{
			return String.valueOf(Double.parseDouble((jsonParse(jsonToString(connectAPI(URLAddress)),"balance")))*1000);
		}
	}

	public String returnHashrate(){
		String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/"+ewal;
		if (MinerControllerApplication.DEBUG) {
			return jsonParse("{\"status\":true,\"data\":{\"hashrate\":8000,\"balance\":50}}","hashrate");
		}
		else{
			return jsonParse(jsonToString(connectAPI(URLAddress)),"hashrate");
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

		if ("balance".equalsIgnoreCase(dataType)) {
			try {
				Object obj = parser.parse(json);
				JSONObject jsonObject = (JSONObject) obj;
				jsonObject = (JSONObject) jsonObject.get("data");
				String balance = String.valueOf(jsonObject.get(dataType));
				return balance;
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
				String hashrate = String.valueOf(jsonObject.get(dataType));
				return hashrate;
			} catch (ParseException e) {
				System.out.println("JSON File invalid");
				return "0";
			}
		}

		else {return "you are a prick";}
	}



// this function creates a BufferedReader that reads what Claymore prints, and returns it as a string



    private String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            //System.out.println(inputStream.available());
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
        } finally {
            if (br!=null){br.close();}
        }
        final String result = sb.toString();
        System.out.println(result);
        return result;
    }



		public void claymoreStarter() throws IOException{
		try {
			ProcessBuilder pb = new ProcessBuilder(getPathToMiningProgram(),
                    "-epool",epool,"-ewal",ewal,"-epsw",epsw,"-tt",tt,"-fanmin",fanmin,"-fanmax",fanmax,"-dcri",dcri,"-cclock",cclock,"-mclock",mclock,"-cvddc",cvddc,"-mvddc",mvddc);

			Process p = pb.start();


            new Thread(new Runnable() {
                public void run(){
                    claymoreStats = "be back in 4s";
                    while (true) {
                        try { Thread.sleep(4000); } catch (Exception e) {}

                        // we are not in the event thread currently so we should not update the UI here
                        // this is a good place to do some slow, background loading, e.g. load from a server or from a file system
                        if (p.isAlive()){
                            try { claymoreStats = output(p.getInputStream()); } catch (IOException e) { e.printStackTrace(); }
                        }
                    }
                }
            }).start();

		} catch (IOException f) {
			f.printStackTrace();
			System.out.println("Claymore is not executing");
		}
	}


}
