package jiraPlusPlus.physicalBoard.mapping;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import jiraPlusPlus.ColumnHeader;
import jiraPlusPlus.Ticket;
import org.json.JSONArray;

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
            ResultPoint qrCodePosition = result.getResultPoints()[0];
            if (isValidTicket(qrCodeTitle)) {
                ticket = new Ticket(qrCodeTitle, qrCodePosition.getX(), qrCodePosition.getY());
                tickets.add(ticket);
            } else if (isStatus(qrCodeTitle)) {
                columnHeader = new ColumnHeader(qrCodeTitle, qrCodePosition.getX(), qrCodePosition.getY());
                columnHeaders.add(columnHeader);
            }
        }

        JSONArray jsonArray = new JSONArray(Arrays.asList(tickets));
        System.out.println("TICKETS");
        System.out.println(jsonArray.toString());

        JSONArray columnsJson = new JSONArray(Arrays.asList(columnHeaders));
        System.out.println("COLUMN HEADERS");
        System.out.println(columnsJson.toString());

        if (columnHeaders.size() > 1) {
            float xDiff = columnHeaders.get(0).getX() - columnHeaders.get(1).getX();
            float yDiff = columnHeaders.get(0).getY() - columnHeaders.get(1).getY();
            boolean rotated = false;
            if (yDiff > xDiff) {
                rotated = true;
                System.out.println("ROTATED!");
            }

            tickets = updateTicketPositions(columnHeaders, tickets, rotated);
        }
        else {
            System.err.println("Image quality not good enough to read 2 or more headers.");
            tickets = new ArrayList<>();
        }

        JSONArray ticketsJson = new JSONArray(Arrays.asList(tickets));
        System.out.println("MAPPED TICKETS");
        System.out.println(ticketsJson.toString());

        return tickets;
    }

    private boolean isValidTicket(String qrCodeTitle) {
        return (!isStatus(qrCodeTitle)) && (qrCodeTitle.trim().length() > 0);
    }


    private List<Ticket> updateTicketPositions(List<ColumnHeader> columnHeaders, List<Ticket> tickets, boolean rotated) {
        for (ColumnHeader columnHeader : columnHeaders) {
            for (Ticket ticket : tickets) {
                float ticketPosition = ticket.getX();
                float columnHeaderPosition = columnHeader.getX();
                if (rotated) {
                    ticketPosition = ticket.getY();
                    columnHeaderPosition = columnHeader.getY();
                }

                if (isInRangePositionOfColumn(ticketPosition, columnHeaderPosition)) {
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
        return !qrCodeTitle.matches(".*\\d.*") && (qrCodeTitle.trim().length() > 0);
    }

}
