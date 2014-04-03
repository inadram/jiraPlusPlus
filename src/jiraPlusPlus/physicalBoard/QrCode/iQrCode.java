package jiraPlusPlus.physicalBoard.QrCode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import jiraPlusPlus.Ticket;

import java.util.List;

public interface iQrCode {
	List<Ticket> getTickets(BinaryBitmap binaryBitmap) throws NotFoundException;
}
