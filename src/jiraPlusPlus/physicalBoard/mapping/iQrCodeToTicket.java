package jiraPlusPlus.physicalBoard.mapping;

import com.google.zxing.Result;
import jiraPlusPlus.Ticket;

import java.util.List;


public interface iQrCodeToTicket {
	List<Ticket> mapQrCodeToTicket(Result[] results) throws Exception;
}
