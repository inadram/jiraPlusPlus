package jiraPlusPlus;

import java.util.ArrayList;
import java.util.List;

public class Print2D {

	List<List<String>> current = new ArrayList<>();
	List<List<String>> updated = new ArrayList<>();


	public void printCurrentTicketBoardToConsole() {
		System.out.println("\n\r");
		System.out.print(createColumn("To Do"));
		System.out.print(createColumn("In Progress"));
		System.out.print(createColumn("Done"));
		System.out.print("\n\r");
		for (int i = 0; i < current.size(); i++) {
			System.out.print(createColumn(current.get(0).get(i)));
			System.out.print(createColumn(current.get(1).get(i)));
			System.out.print(createColumn(current.get(2).get(i)));
			System.out.print("\n\r");
		}
	}

	public void printUpdatedTicketBoardToConsole() {
		System.out.println("\n\r");
		System.out.print(createColumn("To Do"));
		System.out.print(createColumn("In Progress"));
		System.out.print(createColumn("Done"));
		System.out.print("\n\r");
		for (int i = 0; i < updated.size(); i++) {
			System.out.print(createColumn(updated.get(0).get(i)));
			System.out.print(createColumn(updated.get(1).get(i)));
			System.out.print(createColumn(updated.get(2).get(i)));
			System.out.print("\n\r");
		}
	}

	private String createColumn(String columnValue) {
		if(columnValue==null){
			columnValue ="";
		}
		int valueLength = columnValue.length();
		String output = columnValue;
		while (valueLength < 15) {
			output += " ";
			valueLength++;
		}
		return "|" + output + "|";
	}

	public void storeCurrentStatusPositions(String ticketId, String status, int index) {
		List<String> column = new ArrayList<>();
		try {
			if (status.equalsIgnoreCase("todo")) {
				column.add(ticketId);
				current.add(column);
			} else if (status.equalsIgnoreCase("InProgress")) {
				current.get(1).add(ticketId);
			} else if (status.equalsIgnoreCase("Done")) {
				current.get(2).add(ticketId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeUpdatedStatusPositions(String ticketId, String status, int index) {
		try {
			if (status.equalsIgnoreCase("todo")) {
				updated.get(0).add(ticketId);
			} else if (status.equalsIgnoreCase("InProgress")) {
				updated.get(1).add(ticketId);
			} else if (status.equalsIgnoreCase("Done")) {
				updated.get(2).add(ticketId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}