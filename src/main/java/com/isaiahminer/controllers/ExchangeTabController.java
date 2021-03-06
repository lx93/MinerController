package com.isaiahminer.controllers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import com.isaiahminer.models.ExchangeController;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ExchangeTabController {

    public Text eth_btcText;
    public TextField input_ethTF;
    public TextField output_btcTF;

    private ExchangeController exchangeController = new ExchangeController();

    //class constructor
    public ExchangeTabController() throws IOException, ParserConfigurationException {
        update_eth_btcRate();
    }


    // This function updates eth_btc rate section on the screen
    public void update_eth_btcRate() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try { Thread.sleep(1000);} catch (Exception e) {}
                    //initial run before constant update every minute
                    String rate = exchangeController.eth_btcRate();
                    eth_btcText.setText("Current exchange rate: 1ETH = " + rate + " BTC");
                    output_btcTF.setText(rate);

                    //this is just for demo purposes
                    try { Thread.sleep(60000); } catch (Exception e) {}

                    // we are not in the event thread currently so we should not update the UI here
                    // this is a good place to do some slow, background loading, e.g. load from a server or from a file system

//                    Platform.runLater(new Runnable() {
//                        public void run() {
//                            // we are now back in the EventThread and can update the GUI
//                            String rate = controller.eth_btcRate();
//                            System.out.println(rate);
//                            eth_btcText.setText("Current exchange rate: 1ETH = " + rate + " BTC");
//                            output_btcTF.setText(rate);
//                        }
//                    });
                }
            }
        }).start();
    }
}