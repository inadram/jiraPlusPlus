package jiraPlusPlus.electronicBoard;

import jiraPlusPlus.Ticket;
import jiraPlusPlus.electronicBoard.jiraService.IJiraService;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
            String currentStatus = this.jiraService.getCurrentStatus(ticket.getId());
            Queue<String> transitions = this.getTransitions(currentStatus, ticket.getStatus());

            while (transitions.size() > 0) {
                String transition = transitions.remove();
                this.jiraService.transition(ticket.getId(), transition);
            }
        }
    }

    public Queue<String> getTransitions(String startStatus, String endStatus) throws Exception {
        Queue<String> transitions = new LinkedList<>();

        String currentStatus = startStatus;
        int whileCounter = 0;
        while (!currentStatus.equalsIgnoreCase(endStatus)) {
            whileCounter++;
            if (whileCounter > 10) {
                throw new Exception("Stuck in transitioning between " + startStatus + " and " + endStatus);
            }

            if (currentStatus.equalsIgnoreCase("ToDo")) {
                transitions.offer("4");
                currentStatus = "InProgress";
            } else if (currentStatus.equalsIgnoreCase("InProgress")) {
                if (endStatus.equalsIgnoreCase("ToDo")) {
                    transitions.offer("301");
                    currentStatus = "ToDo";
                } else if (endStatus.equalsIgnoreCase("Done")) {
                    transitions.offer("5");
                    currentStatus = "Done";
                }
            } else if (currentStatus.equalsIgnoreCase("Done")) {
                transitions.offer("3");
                currentStatus = "ToDo";
            }
        }

        return transitions;
    }
}
