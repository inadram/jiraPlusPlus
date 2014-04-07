package jiraPlusPlus.electronicBoard;

import jiraPlusPlus.Ticket;
import jiraPlusPlus.electronicBoard.jiraService.IJiraService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class JiraElectronicBoardTest {
    @Mock
    private IJiraService mockJiraService;

    @Test
    public void testSyncUpdatesTheFirstItem() throws Exception {
        JiraElectronicBoard jeb = new JiraElectronicBoard(mockJiraService);

        List<Ticket> tickets = this.createListOfTickets(1);

        jeb.sync(tickets);

        Mockito.verify(mockJiraService, Mockito.times(1)).transition("Key0", "Status0");
    }

    @Test
    public void testSynUpdatesAllTheTickets() throws Exception {
        JiraElectronicBoard jeb = new JiraElectronicBoard(mockJiraService);

        List<Ticket> tickets = this.createListOfTickets(20);

        jeb.sync(tickets);

        Mockito.verify(mockJiraService, Mockito.times(20)).transition(Mockito.anyString(), Mockito.anyString());
    }

    private List<Ticket> createListOfTickets(int count) throws Exception {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < count; i++) {
//            tickets.add(new Ticket("Key" + i, "Status" + i));
        }

        return tickets;
    }
}
