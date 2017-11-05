import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;
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




public class QRHelper {

	private Webcam webcam = null;
	
	public QRHelper(){
		super();/*
		Dimension size = WebcamResolution.QVGA.getSize();

		webcam = Webcam.getWebcams().get(2);
		webcam.setViewSize(size);
		webcam.open();*/

	}
	
	public String scanCode(){
		Result result = null;
		BufferedImage image = null;
		
		//if () {
		try { Thread.sleep(2000); } catch (Exception e) {}
			if ((image = webcam.getImage()) == null) {
				
			}
			
			
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Map<DecodeHintType,Object> pureHints = new EnumMap<>(DecodeHintType.class);
		      //pureHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		      pureHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			try {
				result = new MultiFormatReader().decode(bitmap, pureHints);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			
			System.out.println(result.getText());
			if (result != null) {
				return result.getText();
			}
			
		//}
		return result.getText();
	}
	
	public void generateQR(String content){
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
		    
		    BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
		    JFrame frame = new JFrame();
		    frame.getContentPane().setLayout(new FlowLayout());
		    frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		    frame.setPreferredSize(new java.awt.Dimension(325,325));
		    frame.pack();
		    frame.setVisible(true);
		    
	}
}
