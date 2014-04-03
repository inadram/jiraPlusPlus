package jiraPlusPlus.physicalBoard.QrCode;

import com.google.zxing.*;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import jiraPlusPlus.Ticket;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class QrCode implements iQrCode {

	public List<Ticket> getTickets(BinaryBitmap binaryBitmap) throws NotFoundException {
		return mapQrCodeToTicket(getQRCodeList(binaryBitmap));
	}

	private Result[] getQRCodeList(BinaryBitmap binaryBitmap)
			throws NotFoundException {
		Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
		hints.put(DecodeHintType.TRY_HARDER, BarcodeFormat.QR_CODE);

		QRCodeMultiReader multiReader = new QRCodeMultiReader();
		return multiReader.decodeMultiple(binaryBitmap, hints);
	}

	private List<Ticket> mapQrCodeToTicket(Result[] results) {
		List<Ticket> tickets = new ArrayList<>();
		for (Result result : results) {
			Ticket ticket = new Ticket(result.getText(), "ToDo");
			tickets.add(ticket);
		}
		return tickets;
	}
}