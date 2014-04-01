package jiraPlusPlus;

import java.util.List;

public class JiraElectronicBoard implements IElectronicBoard {
    private List<List<Ticket>> tickets;
    private final IJiraService jiraService;

    public JiraElectronicBoard(IJiraService jiraService) {
        this.jiraService = jiraService;
    }

    public void populate(List<List<Ticket>> tickets) {
        this.tickets = tickets;
    }

    public void sync() {
        this.jiraService.updateStatus("Hello", "Hello");
    }
}
