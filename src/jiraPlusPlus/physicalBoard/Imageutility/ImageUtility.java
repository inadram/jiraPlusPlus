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
//        BufferedImage rotatedImage = RotateBufferedImage90(bufferedImage);
//        ImageIO.write(rotatedImage, "jpg", new File("rotated.jpg"));
		BinaryBitmap binaryBitmap = convertBufferedImageToBinaryBitMap(bufferedImage);
        return binaryBitmap;
	}

    private BufferedImage RotateBufferedImage90(BufferedImage originalImage) {
        System.out.println("90 degree rotating");
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, originalImage.getType());
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                newImage.setRGB(y, width - x - 1, originalImage.getRGB(x, y));
            }
        }
        return newImage;
    }

	private BufferedImage getBufferedImage(File image) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(image);
		return ImageIO.read(fileInputStream);
	}

//	private BinaryBitmap RotateImage90DegreeCounterClockwise(BinaryBitmap binaryBitmap) {
//		binaryBitmap = binaryBitmap.rotateCounterClockwise();
//		return binaryBitmap;
//	}

	private BinaryBitmap convertBufferedImageToBinaryBitMap(BufferedImage bufferedImage) {
		BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		HybridBinarizer binarizer = new HybridBinarizer(bufferedImageLuminanceSource);
		return new BinaryBitmap(binarizer);
	}
}