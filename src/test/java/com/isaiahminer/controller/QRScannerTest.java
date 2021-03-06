package com.isaiahminer.controller;

import static org.junit.Assert.assertNotNull;

import java.awt.GridLayout;

import javax.swing.JFrame;

import org.junit.Test;

import com.isaiahminer.controllers.QRScanner;

public class QRScannerTest {

	@Test public void testShowScanner() throws InterruptedException {
		JFrame newFrame = new JFrame();
		newFrame.setSize(600, 400);
		newFrame.setLayout(new GridLayout(1, 1));
		newFrame.setResizable(true);
    		final QRScanner qrScanner = new QRScanner();
    		assertNotNull("QRScanner", qrScanner);
    		qrScanner.showScanner(newFrame);
    		newFrame.setVisible(true);
		final String qrCodeText = qrScanner.scanCodeContinuously();
		assertNotNull("QR Code", qrCodeText);
		//Thread.sleep(60000);
    }
}
