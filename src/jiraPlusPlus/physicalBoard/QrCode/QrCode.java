package jiraPlusPlus.physicalBoard.QrCode;

import com.google.zxing.*;
import com.google.zxing.multi.MultipleBarcodeReader;
import jiraPlusPlus.Ticket;
import jiraPlusPlus.physicalBoard.mapping.iQrCodeToTicket;

import java.util.Hashtable;
import java.util.List;

public class QrCode implements iQrCode {

	private MultipleBarcodeReader multiReader;

	public QrCode(MultipleBarcodeReader multiReader) {

		this.multiReader = multiReader;
	}

	public List<Ticket> getTickets(BinaryBitmap binaryBitmap, iQrCodeToTicket qrCodeToTicket) throws Exception {
		return qrCodeToTicket.mapQrCodeToTicket(getQRCodeList(binaryBitmap));
	}

	private Result[] getQRCodeList(BinaryBitmap binaryBitmap)
			throws NotFoundException {
		Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
		hints.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);
		return multiReader.decodeMultiple(binaryBitmap, hints);
	}

}