package com.isaiahminer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;


@SuppressWarnings("restriction")
public class MinerControllerApplication extends Application {

    final public static boolean DEBUG = false;
    final private static String VIEW = "MinerControllerView.fxml";

	@Override
    public void start(Stage primaryStage) {
        System.out.println(MinerControllerApplication.class.getName() + " starting: looking for " + VIEW);
        final URL mainView = getClass().getClassLoader().getResource("com/isaiahminer/resources/" + VIEW);
        System.out.println(mainView);
        Parent root;
		try {
			root = FXMLLoader.load(mainView);
		} catch (IOException e) {
			System.err.println("IOException during loading " + VIEW);
			e.printStackTrace(System.err);
			System.exit(-1);
			return;
		}
        System.out.println(VIEW + " loaded");
        primaryStage.setTitle("Isaiah Miner");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.setFullScreen(true);
        primaryStage.show();
        System.out.println(VIEW + " shown");
    }

    /**
     * Terminates the program when clicked X on window
     */
    @Override
    public void stop() {
        System.out.println(MinerControllerApplication.class.getName() + " stopped");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        System.out.println("Debug mode is " + (MinerControllerApplication.DEBUG ? "on!!!!!!!!!!!!!!!!!" : "off"));
        launch(args);
        System.out.println(MinerControllerApplication.class.getName() + " launched");
    }
}