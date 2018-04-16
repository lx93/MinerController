package com.isaiahminer.controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRScanner {

    Webcam webcam = null;

    public QRScanner(){
        // Initialize Camera
        super();
        try { webcam = Webcam.getDefault(); }
        catch(NullPointerException e){}
    }

//    public String scanCode() throws NullPointerException {
//        Result result = null;
//        BufferedImage image = null;
//
//        try { Thread.sleep(2000); } catch (Exception e) {}
//        if ((image = webcam.getImage()) == null) {
//        }
//
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Map<DecodeHintType, Object> pureHints = new EnumMap<>(DecodeHintType.class);
//        pureHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//
//        // parsing the QR into result object
//        try {
//            result = new MultiFormatReader().decode(bitmap, pureHints);
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//            System.out.println("didnt find a QR code");
//        }
//
//
//        // if the QR is present
//        if (result != null) {
//            return result.getText();
//        }
//
//        //otherwise if the QR is not picked up
//        else{
//            return ("lalalala the QR code is messed up");
//        }
//    }


    // this creates a Panel to be fitted into the JFrame passed in as an argument
    public void showScanner(final JFrame window){
        webcam.open();
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        window.add(panel);
        window.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){webcam.close();}
        });
    }

    public String scanCodeContinuously() {
        Result result = null;
        BufferedImage image = null;
        boolean keepGoing = true;
        while (keepGoing) {
        		//webcam.setParameters(parameters);
        		/*
        		java.awt.Dimension[] resolutions = this.webcam.getDevice().getResolutions();
        		for (java.awt.Dimension resolution : resolutions) {
        			System.out.println(resolution.toString());
        		}
        		*/
        		image = webcam.getImage();
        		if (null == image) continue;
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType,Object> pureHints = new EnumMap<>(DecodeHintType.class);
            pureHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            // parsing the QR into result object
            try {
                result = new MultiFormatReader().decode(bitmap, pureHints);
            } catch (NotFoundException e) {
            	    //System.out.print('.');
                //System.out.println("didn't find a QR code");
                continue;
            }
            if (null == result) continue;
            keepGoing = false;
        }
        System.out.println("QR code extracted: " + result.getText());
        return result.getText();
    }

}
