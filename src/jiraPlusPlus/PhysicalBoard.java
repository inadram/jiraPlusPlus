package jiraPlusPlus;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class PhysicalBoard {

	public void main() throws WriterException, IOException {
		
		try{
		FileInputStream fileInputStream = new FileInputStream("1.png");
		BufferedImage read = ImageIO.read(fileInputStream);
		printQRCode(read);
		
		}catch(Exception ex){
		ex.printStackTrace();
		}

	}

	private static void printQRCode(BufferedImage Image)
			throws NotFoundException {
		Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);
        
		Result[] results;
		BinaryBitmap binaryBitmap;
		BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(Image);
		HybridBinarizer binarizer = new HybridBinarizer(bufferedImageLuminanceSource);
		binaryBitmap = new BinaryBitmap(binarizer);
		binaryBitmap = binaryBitmap.rotateCounterClockwise();
		QRCodeMultiReader multiReader = new QRCodeMultiReader();
		results = multiReader.decodeMultiple(binaryBitmap, hints);
        for (Result result : results) {
            String text = result.getText();

            System.out.println(text);
        }
	}
}

