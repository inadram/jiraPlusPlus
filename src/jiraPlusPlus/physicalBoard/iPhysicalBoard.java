package jiraPlusPlus.physicalBoard;

import com.google.zxing.WriterException;
import jiraPlusPlus.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface iPhysicalBoard {
	List<Ticket> getTicketsOfImage(File image) throws Exception;
}
