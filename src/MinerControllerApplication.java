import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

public class MinerControllerApplication {

	public MinerControllerApplication() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException {
		//Setting up the JFrame for GUI
		JFrame frame = new JFrame ("Isaiah Miner");
		frame.setSize (800,450);
		frame.add(new MinerControllerGUI());
		frame.setVisible(true);
	}

}
