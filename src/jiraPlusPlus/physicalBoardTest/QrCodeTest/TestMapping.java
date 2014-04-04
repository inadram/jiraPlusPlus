package jiraPlusPlus.physicalBoardTest.QrCodeTest;

import jiraPlusPlus.Ticket;
import jiraPlusPlus.physicalBoard.QrCode.QrCode;
import jiraPlusPlus.physicalBoard.mapping.QrCodeToTicket;
import jiraPlusPlus.physicalBoardTest.QrCodeTest.fakes.FakeQRCodeMultiReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class TestMapping {

	@Test
	public void testGivingEmptyBinaryImageShouldReturnEmptyTicketList() throws Exception {
		QrCode qrCode=new QrCode(new FakeQRCodeMultiReader());
		List<Ticket> actualTicketList = qrCode.getTickets(null, new QrCodeToTicket());
		List<Ticket> expectedTicketList = new ArrayList<>();
		Assert.assertEquals(actualTicketList, expectedTicketList);
	}

	@Test
	public void testGivingABinaryImageWithOneTicketItShouldReturnTicketListWithOneImage() throws Exception{
		QrCode qrCode=new QrCode(new FakeQRCodeMultiReader());
		FakeQRCodeMultiReader fakeQRCodeMultiReader =mock(FakeQRCodeMultiReader.class);
		List<Ticket> actualTicketList = qrCode.getTickets(null, new QrCodeToTicket());
		List<Ticket> expectedTicketList = new ArrayList<>();
		expectedTicketList.add(new Ticket("someId","ToDo"));
		Assert.assertEquals(actualTicketList, expectedTicketList);
	}
}
