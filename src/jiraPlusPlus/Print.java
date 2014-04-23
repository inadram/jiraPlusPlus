package jiraPlusPlus;

import java.util.ArrayList;
import java.util.List;

public class Print {
	List<Ticket> currentToDoTickets =new ArrayList<>();
	List<Ticket> currentInProgressTickets =new ArrayList<>();
	List<Ticket> currentDoneTickets =new ArrayList<>();
	List<Ticket> currentUnknownTickets =new ArrayList<>();
	List<Ticket> updatedToDoTickets =new ArrayList<>();
	List<Ticket> updatedInProgressTickets =new ArrayList<>();
	List<Ticket> updatedDoneTickets =new ArrayList<>();
	List<Ticket> updatedUnknownTickets =new ArrayList<>();
	
	

	public void printCurrentTicketBoardToConsole() {
		int maxLength = (currentToDoTickets.size() > currentInProgressTickets.size()) ? currentToDoTickets.size() : currentInProgressTickets.size();
		maxLength = (maxLength > currentDoneTickets.size()) ? maxLength : currentDoneTickets.size();
		maxLength = (maxLength > currentUnknownTickets.size()) ? maxLength : currentUnknownTickets.size();
		System.out.println("\n\r");
		System.out.print("|| To Do       |");
		System.out.print("| In Progress |");
		System.out.print("| Done        |");
		System.out.print("| Unknown     ||");
		for (int i = 0; i < maxLength; i++) {
			getTicketId(i, currentToDoTickets);
			getTicketId(i, currentInProgressTickets);
			getTicketId(i, currentDoneTickets);
			getTicketId(i, currentUnknownTickets);
			System.out.println();
			System.out.println("\n\r");
		}
	}

	public void printUpdatedTicketBoardToConsole() {
		int maxLength = (updatedToDoTickets.size() > updatedInProgressTickets.size()) ? updatedToDoTickets.size() : currentInProgressTickets.size();
		maxLength = (maxLength > updatedDoneTickets.size()) ? maxLength : updatedDoneTickets.size();
		maxLength = (maxLength > updatedUnknownTickets.size()) ? maxLength : updatedUnknownTickets.size();
		System.out.println("\n\r");
		System.out.print("|| To Do       |");
		System.out.print("| In Progress |");
		System.out.print("| Done        |");
		System.out.print("| Unknown     ||");
		for (int i = 0; i < maxLength; i++) {
			getTicketId(i, updatedToDoTickets);
			getTicketId(i, updatedInProgressTickets);
			getTicketId(i, updatedDoneTickets);
			getTicketId(i, updatedUnknownTickets);
			System.out.println("\n\r");
		}
	}

	public String getTicketId(int i, List<Ticket> ticketList) {
		String ticketId = "||           ||";
		if (ticketList.size() > i) {
			ticketId = "||     " + ticketList.get(i).getId() + "     ||";
		}
		return ticketId;
	}

	public void storeCurrentStatusPositions(String ticketId, String status) {
		Ticket ticket;
		try {
			ticket = new Ticket(ticketId, 0, 0);
			if (status.equalsIgnoreCase("todo")) {
				currentToDoTickets.add(ticket);
			} else if (status.equalsIgnoreCase("InProgress")) {
				currentInProgressTickets.add(ticket);
			} else if (status.equalsIgnoreCase("Done")) {
				currentDoneTickets.add(ticket);
			} else {
				currentUnknownTickets.add(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeUpdatedStatusPositions(String ticketId, String status) {
		Ticket ticket;
		try {
			ticket = new Ticket(ticketId, 0, 0);
			if (status.equalsIgnoreCase("todo")) {
				updatedToDoTickets.add(ticket);
			} else if (status.equalsIgnoreCase("InProgress")) {
				updatedInProgressTickets.add(ticket);
			} else if (status.equalsIgnoreCase("Done")) {
				updatedDoneTickets.add(ticket);
			} else {
				updatedUnknownTickets.add(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}