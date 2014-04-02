package jiraPlusPlus;

import java.util.List;

public class JiraElectronicBoard implements IElectronicBoard {
    private List<Ticket> tickets;
    private final IJiraService jiraService;

    public JiraElectronicBoard(IJiraService jiraService) {
        this.jiraService = jiraService;
    }

    public void populate(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void sync() throws Exception {
        for (Ticket ticket : this.tickets) {
            this.jiraService.transition(ticket.getId(), ticket.getStatus());
        }
    }
}
