package jiraPlusPlus.electronicBoard;

import jiraPlusPlus.Print;
import jiraPlusPlus.Ticket;
import jiraPlusPlus.electronicBoard.jiraService.IJiraService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JiraElectronicBoard implements IElectronicBoard {
    private final IJiraService jiraService;
	private final Print print = new Print();

	public JiraElectronicBoard(IJiraService jiraService) {
        this.jiraService = jiraService;
    }

    public void sync(List<Ticket> tickets) throws Exception {
        for (Ticket ticket : tickets) {
            Queue<String> transitions = getTransitions(ticket);
			performTransitions(ticket, transitions);
		}
		print.printCurrentTicketBoardToConsole();
		print.printUpdatedTicketBoardToConsole();
    }

	private void performTransitions(Ticket ticket, Queue<String> transitions) {
        while (transitions.size() > 0) {
            String transition = transitions.remove();
            try {
                this.jiraService.transition(ticket.getId(), transition);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.err.println(sw.toString());
                break;
            }
        }
    }

    private Queue<String> getTransitions(Ticket ticket) throws Exception {
		String ticketId = ticket.getId();
		String currentStatus = this.jiraService.getCurrentStatus(ticketId);
		String updatedStatus = ticket.getStatus();
		print.storeCurrentStatusPositions(ticketId, currentStatus);
		print.storeUpdatedStatusPositions(ticketId, updatedStatus);
		Queue<String> transitions = new LinkedList<>();
		if (currentStatus != null && !currentStatus.equals("Unknown") && updatedStatus != null && !updatedStatus.equals("Unknown")) {
			System.out.println("Getting transitions for ticket:  " + ticketId + " transitioning from: " + currentStatus + " to: " + updatedStatus);
            transitions = this.getTransitions(currentStatus, updatedStatus);
        }
        return transitions;
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
