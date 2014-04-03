package jiraPlusPlus.physicalBoard;

import com.google.zxing.BinaryBitmap;
import jiraPlusPlus.Ticket;
import jiraPlusPlus.physicalBoard.Imageutility.iImageUtility;
import jiraPlusPlus.physicalBoard.QrCode.iQrCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhysicalBoard implements iPhysicalBoard {
	private iQrCode qrCode;
	private iImageUtility imageUtility;

	public PhysicalBoard(iImageUtility imageUtility, iQrCode QrCode) {
		this.imageUtility = imageUtility;
		this.qrCode = QrCode;
	}

	public List<Ticket> getTicketsOfImage(String imageName) throws IOException {
		List<Ticket> tickets = new ArrayList<>();
		try {
			BinaryBitmap binaryBitmap = imageUtility.convertToBinaryBitmap(imageName);
			tickets = qrCode.getTickets(binaryBitmap);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tickets;
	}
}

