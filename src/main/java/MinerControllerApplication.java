import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;



public class MinerControllerApplication extends Application{

    final static boolean DEBUG = false;

	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MinerControllerFXML.fxml"));
		primaryStage.setTitle("Isaiah Miner");
		primaryStage.setScene(new Scene(root, 800, 450));
		//primaryStage.setFullScreen(true);
		primaryStage.show();
	}
// terminates the program when clicked X on window
	@Override
	public void stop(){System.exit(0);}


	public static void main(String[] args) throws IOException, ParserConfigurationException {


	    if (DEBUG){
	        System.out.println("Debug mode is on!!!!!!!!!!!!!!!!!");
        }

		launch(args);

	}

}