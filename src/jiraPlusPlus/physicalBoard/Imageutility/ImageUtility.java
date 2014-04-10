package jiraPlusPlus.physicalBoard.Imageutility;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtility implements iImageUtility {

	@Override
	public BinaryBitmap convertToBinaryBitmap(File image) throws IOException {
		BufferedImage bufferedImage= getBufferedImage(image);
        BinaryBitmap binaryBitmap = convertBufferedImageToBinaryBitMap(bufferedImage);
        return binaryBitmap;
	}


	private BufferedImage getBufferedImage(File image) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(image);
		return ImageIO.read(fileInputStream);
	}


	private BinaryBitmap convertBufferedImageToBinaryBitMap(BufferedImage bufferedImage) {
		BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		HybridBinarizer binarizer = new HybridBinarizer(bufferedImageLuminanceSource);
		return new BinaryBitmap(binarizer);
	}
}