import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import java.util.EnumMap;
import java.util.Map;


import javax.swing.JFrame;



import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;


public class WalletController {


	public static WalletController key = BTCHelper.getKeyPair();
	QRScanner scanner = new QRScanner();


	private String pubKey;
	private String privKey;


	public WalletController(String privKey, String pubKey) {
		this.privKey = privKey;
		this.pubKey = pubKey;
	}


	public String getPubKey() {
		return pubKey;
	}

	public String getPrivKey() {
		return privKey;
	}


	public String scanCode(){
		return scanner.scanCode();
	}


	public BufferedImage generateQR(String content){
		BitMatrix matrix=null;
		int bigEnough = 256;
		Writer writer = new QRCodeWriter();
		try {
			matrix = writer.encode(content, BarcodeFormat.QR_CODE, bigEnough,
					bigEnough, null);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return MatrixToImageWriter.toBufferedImage(matrix);

	}
}