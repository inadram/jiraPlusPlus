package jiraPlusPlus.physicalBoard.Imageutility;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtility implements iImageUtility {

	@Override
	public BinaryBitmap convertToBinaryBitmap(String imageName) throws IOException {
		BufferedImage bufferedImage= getBufferedImage(imageName);
		return convertBufferedImageToBinaryBitMap(bufferedImage);
//		binaryBitmap = RotateImage90DegreeCounterClockwise(binaryBitmap);
//		return  binaryBitmap;
	}

	private BufferedImage getBufferedImage(String imageName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(imageName);
		return ImageIO.read(fileInputStream);
	}

	private BinaryBitmap RotateImage90DegreeCounterClockwise(BinaryBitmap binaryBitmap) {
		binaryBitmap = binaryBitmap.rotateCounterClockwise();
		return binaryBitmap;
	}

	private BinaryBitmap convertBufferedImageToBinaryBitMap(BufferedImage bufferedImage) {
		BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		HybridBinarizer binarizer = new HybridBinarizer(bufferedImageLuminanceSource);
		return new BinaryBitmap(binarizer);
	}
}