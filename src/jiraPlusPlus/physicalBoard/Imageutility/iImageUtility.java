package jiraPlusPlus.physicalBoard.Imageutility;

import com.google.zxing.BinaryBitmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface iImageUtility {
	BinaryBitmap convertToBinaryBitmap(File image) throws IOException;
}
