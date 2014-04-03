package jiraPlusPlus.physicalBoard.Imageutility;

import com.google.zxing.BinaryBitmap;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mardaa01
 * Date: 03/04/2014
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
public interface iImageUtility {
	BinaryBitmap convertToBinaryBitmap(String imageName) throws IOException;
}
