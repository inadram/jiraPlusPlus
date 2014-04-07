package jiraPlusPlus.physicalBoard.mapping;

import com.google.zxing.Result;
import jiraPlusPlus.ColumnHeader;
import jiraPlusPlus.Ticket;
import org.json.JSONArray;

import javax.sql.rowset.spi.SyncProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QrCodeToTicket implements iQrCodeToTicket {


	public List<Ticket> mapQrCodeToTicket(Result[] results) throws Exception {
		Ticket ticket;
		List<Ticket> tickets = new ArrayList<>();
		ColumnHeader columnHeader;
		List<ColumnHeader> columnHeaders = new ArrayList<>();

		for (Result result : results) {
			String qrCodeTitle = result.getText();
			if (isValidTicket(qrCodeTitle)) {
				ticket = new Ticket(qrCodeTitle, result.getResultPoints()[0].getX());
				tickets.add(ticket);
			}
			else if(isStatus(qrCodeTitle)){
				columnHeader = new ColumnHeader(qrCodeTitle, result.getResultPoints()[0].getX());
				columnHeaders.add(columnHeader);
			}
		}
		tickets= updateTicketPositions(columnHeaders,tickets);

		return tickets;
	}

	private boolean isValidTicket(String qrCodeTitle) {
		return (!isStatus(qrCodeTitle)) && (qrCodeTitle.trim().length() > 0);
	}


	private List<Ticket> updateTicketPositions(List<ColumnHeader> columnHeaders, List<Ticket> tickets) {
		for (ColumnHeader columnHeader : columnHeaders) {
			for (Ticket ticket : tickets) {
				if (isInRangePositionOfColumn(ticket.getPosition(), columnHeader.getPosition())){
					ticket.setStatus(columnHeader.getId());
				}
			}
		}
		return tickets;

	}

	private boolean isInRangePositionOfColumn(float ticketPosition, float headerPosition) {
		return (ticketPosition < headerPosition + 200 && ticketPosition > headerPosition - 200);
	}

	private boolean isStatus(String qrCodeTitle) {
		return !qrCodeTitle.matches(".*\\d.*");
	}

}
