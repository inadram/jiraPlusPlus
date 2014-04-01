package jiraPlusPlus;

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
    public void testSync() throws Exception {
        JiraElectronicBoard jeb = new JiraElectronicBoard(mockJiraService);

        List<List<Ticket>> tickets = new ArrayList<>();
        List<Ticket> column1 = new ArrayList<>();
        column1.add(new Ticket("Hello"));
        tickets.add(column1);

        jeb.populate(tickets);
        jeb.sync();

        Mockito.verify(mockJiraService, Mockito.times(1)).updateStatus("Hello", "Hello");
    }
}
