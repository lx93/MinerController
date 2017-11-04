import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;



public class MinerControllerApplication extends Application{


	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MinerControllerFXML.fxml"));
		primaryStage.setTitle("Isaiah Miner");
		primaryStage.setScene(new Scene(root, 800, 450));
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException {

		launch(args);

	}

}
