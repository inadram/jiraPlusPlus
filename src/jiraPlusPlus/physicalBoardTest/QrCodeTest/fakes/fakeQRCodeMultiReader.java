package jiraPlusPlus.physicalBoardTest.QrCodeTest.fakes;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.multi.MultipleBarcodeReader;

import java.util.Map;

public class FakeQRCodeMultiReader implements MultipleBarcodeReader {
    @Override
    public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
        return new Result[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
        return new Result[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
