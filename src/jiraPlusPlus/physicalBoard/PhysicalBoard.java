package jiraPlusPlus.physicalBoard;

import com.google.zxing.BinaryBitmap;
import jiraPlusPlus.Ticket;
import jiraPlusPlus.physicalBoard.Imageutility.iImageUtility;
import jiraPlusPlus.physicalBoard.QrCode.iQrCode;

import java.io.File;
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

    public List<Ticket> getTicketsOfImage(File image) throws Exception {
        BinaryBitmap binaryBitmap = imageUtility.convertToBinaryBitmap(image);
        List<Ticket> tickets = qrCode.getTickets(binaryBitmap);
        return tickets;
    }
}

