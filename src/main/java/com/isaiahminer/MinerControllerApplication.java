package com.isaiahminer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


public class MinerControllerApplication extends Application {

    final public static boolean DEBUG = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println(MinerControllerApplication.class.getName() + " started");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/isaiahminer/resources/MinerControllerFXML.fxml"));
        //System.out.println("MinerControllerFXML.fxml found");
        primaryStage.setTitle("Isaiah Miner");
        primaryStage.setScene(new Scene(root, 800, 450));
        //primaryStage.setFullScreen(true);
        primaryStage.show();
        //System.out.println("MinerControllerFXML.fxml shown");
    }

    /**
     * Terminates the program when clicked X on window
     */
    @Override
    public void stop() {
        //System.out.println(MinerControllerApplication.class.getName() + " stopped");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        //System.out.println("Debug mode is " + (MinerControllerApplication.DEBUG ? "on!!!!!!!!!!!!!!!!!" : "off"));
        launch(args);
        //System.out.println(MinerControllerApplication.class.getName() + " launched");
    }
}