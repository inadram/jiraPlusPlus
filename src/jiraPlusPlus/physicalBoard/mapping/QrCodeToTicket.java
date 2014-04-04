package jiraPlusPlus.physicalBoard.mapping;

import com.google.zxing.Result;
import jiraPlusPlus.Ticket;

import java.util.ArrayList;
import java.util.List;

public class QrCodeToTicket implements iQrCodeToTicket {


	public List<Ticket> mapQrCodeToTicket(Result[] results) throws Exception {
		String status = "ToDo";
		Ticket ticket;
		List<Ticket> tickets = new ArrayList<>();
		for (Result result : results) {
			String qrCodeTitle = result.getText();
			if (isStatus(qrCodeTitle)) {
				status = qrCodeTitle;
			} else if ((qrCodeTitle.trim().length() > 0)) {
				ticket = new Ticket(qrCodeTitle, status);
				tickets.add(ticket);
			}
		}
		return tickets;
	}

	private boolean isStatus(String qrCodeTitle) {
		return !qrCodeTitle.matches(".*\\d.*");
	}

}
