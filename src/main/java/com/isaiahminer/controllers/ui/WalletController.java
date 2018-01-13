import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;


public class WalletController {

    QRScanner scanner = new QRScanner();


	private String pubKey;
	private String privKey;


	public WalletController(String privKey, String pubKey) {
		this.privKey = privKey;
		this.pubKey = pubKey;
	}

	public WalletController(){}


	public String getPubKey() {
		return pubKey;
	}

	public String getPrivKey() {
		return privKey;
	}


	public String scanCode(){
		//return scanner.scanCode();

        return scanner.scanCodeContinuously();
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