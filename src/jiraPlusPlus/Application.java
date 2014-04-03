package jiraPlusPlus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;


public class Application {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            if (args[0].equalsIgnoreCase("physical")) {
            	PhysicalBoard ph = new PhysicalBoard();
            	ph.main();

            } else if (args[0].equalsIgnoreCase("electronic")) {
                String ticketNumber = args[1];
                String status = args[2];

                List<Ticket> tickets = new ArrayList<>();
                Ticket ticket = new Ticket(ticketNumber, status);
                tickets.add(ticket);

                IJiraService jiraService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/home/pi/personal.p12", "/home/pi/jssecacerts", "password");
                IElectronicBoard eBoard = new JiraElectronicBoard(jiraService);
                eBoard.populate(tickets);
                eBoard.sync();
            }
            else  {
                throw new Exception("Input expected to be electronic or physical");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("App total time: " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }
}
