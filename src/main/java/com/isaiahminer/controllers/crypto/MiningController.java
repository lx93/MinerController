package com.isaiahminer.controllers.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.isaiahminer.MinerControllerApplication;
import com.isaiahminer.controllers.ui.MiningTabController;
import com.isaiahminer.controllers.ui.SettingsController;

public class MiningController {

	private final MiningTabController miningTab;
	private SettingsController controller = new SettingsController();

//The following variables are all configurations for Claymore
    private String epool = "eth-us-east1.nanopool.org:9999";
    private String ewal = controller.returnMiningAddress().substring(9);//"ethereum:0x54d0b70baa6577b1118dde00de22bdbcafb1e8aa"
    private String eworker = getHostName(); //Worker name
    private String epsw = "x";
    private String tt = "75";
    private String fanmin = "60";
    private String fanmax = "90";
    private String dcri = "4";
    private String cclock = "1165";
    private String mclock = "2150";
    private String cvddc = "810";
    private String mvddc = "810";

    public MiningController(final MiningTabController miningTab) {
    		this.miningTab = miningTab;
	}

	public String getPathToMiningProgram() {
		return MinerControllerApplication.DEBUG ? "src/main/resources/bash/emulateClaymore.sh"
				: "./Desktop/Claymore/ethdcrminer64";
    }

	public String returnBalance() {
		try {
			return String.format("%.2f", Double.parseDouble(balance()) * 1000);
		} catch (NumberFormatException e) {
			return "8.88";
		}
	}

	private String balance() {
		if (MinerControllerApplication.DEBUG) {
            return jsonParse("{\"status\":true,\"data\":{\"hashrate\":8000,\"balance\":50}}","balance");
		}
		final String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/" + ewal;
		return jsonParse(jsonToString(connectAPI(URLAddress)), "balance");
	}

	public String returnHashrate() {
		if (MinerControllerApplication.DEBUG) {
			return jsonParse("{\"status\":true,\"data\":{\"hashrate\":8000,\"balance\":50}}","hashrate");
		}
		final String URLAddress = "https://api.nanopool.org/v1/eth/balance_hashrate/" + ewal;
		return jsonParse(jsonToString(connectAPI(URLAddress)), "hashrate");
	}

	public String returnPrice() {
        if (MinerControllerApplication.DEBUG) {
            return jsonParse("{\"status\":true,\"data\":{\"price_btc\":0.09078,\"price_usd\":8888.88,\"price_eur\":868.86,\"price_rur\":70353.59,\"price_cny\":7859.19}}", "price_usd");
        }
        final String URLAddress = "https://api.nanopool.org/v1/eth/prices";
        return jsonParse(jsonToString(connectAPI(URLAddress)), "price_usd");
    }

    public String returnProfit() {
        if (MinerControllerApplication.DEBUG) {
            return ("$1000/day");
        }
        final String URLAddress = "https://api.nanopool.org/v1/eth/approximated_earnings/28" ;
//        System.out.println(jsonParse(jsonToString(connectAPI(URLAddress)), "day","dollars"));
        return jsonParse(jsonToString(connectAPI(URLAddress)), "day","dollars");
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

		String string;

		try {
			string = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			string = "Cannot retrieve your nanopool balance";
		}

		return string;

	}

	/**
	 * parses the string of nanopool balance JSON
	 * @param json - original JSON
	 * @param dataType has to be either "balance" or "hashrate"
	 * @return either the balance or the hashrate
	 */
	public String jsonParse(String json, String dataType) {
		if ("balance".equalsIgnoreCase(dataType)) {
			return getValueFromJson(json, dataType);
		}
		if ("hashrate".equalsIgnoreCase(dataType)) {
			return getValueFromJson(json, dataType);
		}
        if ("price_usd".equalsIgnoreCase(dataType)) {
            return getValueFromJson(json, dataType);
        }
		return "you are a prick";
	}

    public String jsonParse(String json, String dataType, String dataType2) {
        if ("day".equalsIgnoreCase(dataType)) {
            return getValueFromJson(json, dataType, dataType2);
        }
        return "you are a prick";
    }

	private String getValueFromJson(String json, String dataType) {
		final JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(json);
			JSONObject innerObject = (JSONObject) jsonObject.get("data");
			if (null == innerObject) return "getValueFromJson2: JSON innerObject is null";
			return String.valueOf(innerObject.get(dataType));
		} catch (ParseException e) {
			System.out.println("JSON File invalid: " + json);
			return "failed to parse JSON...";
		}
	}

    private String getValueFromJson(String json, String dataType, String dataType2) {
        final JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            JSONObject innerObject = (JSONObject) jsonObject.get("data");
            if (null == innerObject) return "getValueFromJson3:JSON innerObject is null";
            JSONObject innerinnerObject = (JSONObject) innerObject.get(dataType);
            return String.valueOf(innerinnerObject.get(dataType2));
        } catch (ParseException e) {
            System.out.println("JSON File invalid: " + json);
            return "failed to parse JSON...";
        }
    }

	public void claymoreStarter() {
		final ProcessBuilder pb = new ProcessBuilder(getPathToMiningProgram(),
				"-epool", epool,
				"-ewal", ewal,
				"-eworker", eworker,
				"-epsw", epsw,
				"-tt", tt,
				"-fanmin", fanmin,
				"-fanmax", fanmax,
				"-dcri", dcri,
				"-cclock", cclock,
				"-mclock", mclock,
				"-cvddc", cvddc,
				"-mvddc", mvddc);

		Process minerProcess;
		try {
			minerProcess = pb.start();
		} catch (IOException e) {
			System.err.println("Failed to start miner process");
			e.printStackTrace();
			return;
		}
		//p.getOutputStream();
		final Thread outputListener = new Thread() {
			private final static String ETH_GPU0_PREFIX  = "ETH: GPU0 ";
			private final static String ETH_GPU0_POSTFIX = " Mh/s";
			private final static String GPU0_PREFIX  = "GPU0 t=";
			private final static String GPU0_POSTFIX = "%";
			private final static String GPU0_MIDDLE = "C fan=";

			@Override
			public void run() {
				String statsLine;
				final BufferedReader reader =  new BufferedReader(new InputStreamReader(minerProcess.getInputStream()));
				while (true) {
					if (minerProcess.isAlive()) {
					    //System.out.println("minerProcess is alive!");
						try {
							statsLine = reader.readLine();
						} catch (IOException e1) {
							e1.printStackTrace();
							System.out.println("sleep 1000ms");
							try { Thread.sleep(1000); } catch (InterruptedException e) {}
							continue;
						}
						if (null == statsLine || 0 == statsLine.length()) {
							try { Thread.sleep(500); } catch (InterruptedException e) {}
							continue;
						}
						parseStatsLine(statsLine);
						try { Thread.sleep(100); } catch (InterruptedException e) {}
						continue;
					}
					parseStatsLine("Claymore had finished already with exit code: " + minerProcess.exitValue());
					final BufferedReader errorReader =  new BufferedReader(new InputStreamReader(minerProcess.getErrorStream()));
					String errorLine;
					try {
						while ((errorLine = errorReader.readLine()) != null) System.err.println(errorLine);
					} catch (IOException e) {}
					return;
				}
			}

			/**
			 * 27(\e) 91([) 48(0)                   109(m) <br/>
			 * 27(\e) 91([) 48(0) 59(;) 51(3) 54(6) 109(m) <br/>
			 * 27(\e) 91([) 48(0) 59(;) 51(3) 53(5) 109(m) <br/>
			 * 27(\e) 91([) 48(0) 59(;) 51(3) 50(2) 109(m) <br/>
			 * 27(\e) 91([) 49(1) 59(;) 51(3) 50(2) 109(m) <br/>
			 * @see https://misc.flogisoft.com/bash/tip_colors_and_formatting
			 */
			private void parseStatsLine(String statsLine) {
				statsLine = statsLine.replaceAll("\\e\\[\\d(;\\d{2,3})?m", "");
				System.out.println(statsLine);

				if (statsLine.startsWith(ETH_GPU0_PREFIX) && statsLine.endsWith(ETH_GPU0_POSTFIX)) {
					miningTab.hashrateTextfield.setText(statsLine.substring(ETH_GPU0_PREFIX.length(), statsLine.length() - ETH_GPU0_POSTFIX.length()));
					return;
				}
				if (statsLine.startsWith(GPU0_PREFIX) && statsLine.endsWith(GPU0_POSTFIX)) {
					miningTab.tempTextfield.setText(statsLine.substring(GPU0_PREFIX.length(), statsLine.indexOf(GPU0_MIDDLE)));
					miningTab.fanTextfield.setText(statsLine.substring(statsLine.indexOf(GPU0_MIDDLE) + GPU0_MIDDLE.length(), statsLine.length() - 1));
					return;
				}
				miningTab.claymoreText.setText(statsLine);
			}
		};
		outputListener.setName("Claymore listener");
		outputListener.start();
	}



	private String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException | NullPointerException e) {}
		final String hostname = System.getenv("HOSTNAME");
		return null == hostname ? "IsaiahMiner" : hostname;
	}
}
