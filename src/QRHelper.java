import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;




public class QRHelper {

	private Webcam webcam = null;
	
	public QRHelper(){
		super();
		Dimension size = WebcamResolution.QVGA.getSize();

		webcam = Webcam.getWebcams().get(2);
		webcam.setViewSize(size);
		webcam.open();

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
}
