import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MiningController {

	SettingsController controller = new SettingsController();
    public static String claymoreStats;
    public static String gpu0speed = "0";
    public static String gpu0temperature = "0";
    public static String gpu0fan = "0";

//The following variables are all configurations for Claymore
    String epool = "eth-us-east1.nanopool.org:9999";
    String ewal = controller.returnMiningAddress().substring(9);
    String epsw = "x";
    String tt = "75";
    String fanmin = "60";
    String fanmax = "90";
    String dcri = "4";
    String cclock = "1165";
    String mclock = "2150";
    String cvddc = "810";
    String mvddc = "810";

    public MiningController() {
    		super();
	}

	public String getPathToMiningProgram() {
		return MinerControllerApplication.DEBUG ? "src/main/resources/bash/emulateClaymore.sh"
				: "./Desktop/Claymore/ethdcrminer64";
    }

	public String returnBalance() {
		return String.format("%.2f", Double.parseDouble(balance()) * 1000);
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
		return "you are a prick";
	}

	private String getValueFromJson(String json, String dataType) {
		final JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(json);
			JSONObject innerObject = (JSONObject) jsonObject.get("data");
			if (null == innerObject) return "loading...";
			return String.valueOf(innerObject.get(dataType));
		} catch (ParseException e) {
			System.out.println("JSON File invalid: " + json);
			return "loading...";
		}
	}

    /**
     * creates a BufferedReader that reads what Claymore prints, and returns it as a string
     * @param inputStream
     * @return
     * @throws IOException
    private String output(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        try {
            //System.out.println(inputStream.available());
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("�����")&& isr!=null) continue;
                sb.append(line + System.getProperty("line.separator"));
            }
        } finally {
            if (br!=null){br.close();}
        }
        final String result = sb.toString();
        System.out.println(result);
        return result;
    }
    */

	public void claymoreStarter() {
		final ProcessBuilder pb = new ProcessBuilder(getPathToMiningProgram(),
				"-epool", epool,
				"-ewal", ewal,
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
							try { Thread.sleep(1000); } catch (InterruptedException e) { return; }
							continue;
						}
						if (null == statsLine || 0 == statsLine.length()) {
							try { Thread.sleep(500); } catch (InterruptedException e) { return; }
							continue;
						}
						System.out.println(statsLine);
						parseStatsLine(statsLine);
						continue;
					}
					statsLine = "Claymore had finished already with exit code: " + minerProcess.exitValue();
					MiningController.claymoreStats = statsLine;
					System.err.println(statsLine);
					final BufferedReader errorReader =  new BufferedReader(new InputStreamReader(minerProcess.getErrorStream()));
					String errorLine;
					try {
						while ((errorLine = errorReader.readLine()) != null) System.err.println(errorLine);
					} catch (IOException e) {}
					return;
				}
			}

			private void parseStatsLine(final String statsLine) {
				if (MinerControllerApplication.DEBUG) System.out.println(statsLine);{
				    MiningController.claymoreStats = statsLine;
                }
				if (statsLine.startsWith(ETH_GPU0_PREFIX) && statsLine.endsWith(ETH_GPU0_POSTFIX)) {
					gpu0speed = statsLine.substring(ETH_GPU0_PREFIX.length(), statsLine.length() - ETH_GPU0_POSTFIX.length());
					if (MinerControllerApplication.DEBUG) System.out.println("gpu0speed: " + gpu0speed + " Mh/s");
					return;
				}
				if (statsLine.startsWith(GPU0_PREFIX) && statsLine.endsWith(GPU0_POSTFIX)) {
					gpu0temperature = statsLine.substring(GPU0_PREFIX.length(), statsLine.indexOf(GPU0_MIDDLE));
					gpu0fan = statsLine.substring(statsLine.indexOf(GPU0_MIDDLE) + GPU0_MIDDLE.length());
					if (MinerControllerApplication.DEBUG) System.out.println("gpu0temperature: " + gpu0temperature
							+ "C gpu0fan: " + gpu0fan);
					return;
				}
			}
		};
		outputListener.setName("Claymore listener");
		outputListener.start();
	}
}
