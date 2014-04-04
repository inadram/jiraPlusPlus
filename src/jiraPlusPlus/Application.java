package jiraPlusPlus;

import jiraPlusPlus.electronicBoard.IElectronicBoard;
import jiraPlusPlus.electronicBoard.jiraService.IJiraService;
import jiraPlusPlus.electronicBoard.JiraElectronicBoard;
import jiraPlusPlus.electronicBoard.jiraService.JiraRESTService;
import jiraPlusPlus.physicalBoard.Imageutility.ImageUtility;
import jiraPlusPlus.physicalBoard.PhysicalBoard;
import jiraPlusPlus.physicalBoard.QrCode.QrCode;
import jiraPlusPlus.physicalBoard.iPhysicalBoard;
import org.json.JSONArray;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Application {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            if (args[0].equalsIgnoreCase("physical")) {
                iPhysicalBoard ph = new PhysicalBoard(new ImageUtility(), new QrCode());
                List<Ticket> tickets = ph.getTicketsOfImage(new File("1.jpg"));
                JSONArray jsonArray = new JSONArray(Arrays.asList(tickets));
                System.out.println(jsonArray.toString());
            } else if (args[0].equalsIgnoreCase("electronic")) {
                String ticketNumber = args[1];
                String status = args[2];

                List<Ticket> tickets = new ArrayList<>();
                Ticket ticket = new Ticket(ticketNumber, status);
                tickets.add(ticket);

                IJiraService jiraService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/home/pi/personal.p12", "/home/pi/jssecacerts", "password");
                IElectronicBoard eBoard = new JiraElectronicBoard(jiraService);
                eBoard.sync(tickets);
            } else if (args[0].equalsIgnoreCase("email")) {
                EmailReader emailReader = new EmailReader("jiraplusplus@gmail.com", "JiraPlus", "imap.gmail.com", "/run/shm/images");
                emailReader.getOldestUnprocessedImage();
            } else if (args[0].equalsIgnoreCase("normal")) {
                System.out.println("Starting JiraPlusPlus...");
                long setupStartTime = System.currentTimeMillis();
                IJiraService jiraService = new JiraRESTService("https://jira.dev.bbc.co.uk/rest/api/2/", "/home/pi/personal.p12", "/home/pi/jssecacerts", "password");
                IElectronicBoard eBoard = new JiraElectronicBoard(jiraService);
                EmailReader emailReader = new EmailReader("jiraplusplus@gmail.com", "JiraPlus", "imap.gmail.com", "/run/shm/images");
                iPhysicalBoard physicalBoard = new PhysicalBoard(new ImageUtility(), new QrCode());
                long setupEndTime = System.currentTimeMillis();
                System.out.println("Setup time: " + (setupEndTime - setupStartTime) + "ms");

                while (true) {
                    long emailStartTime = System.currentTimeMillis();
                    File imageForProcessing = emailReader.getOldestUnprocessedImage();
                    long emailEndTime = System.currentTimeMillis();
                    System.out.println("Email retrieval: " + (emailEndTime - emailStartTime) + "ms");
                    if (imageForProcessing != null) {
                        List<Ticket> tickets = physicalBoard.getTicketsOfImage(imageForProcessing);
                        long physicalEndTime = System.currentTimeMillis();
                        System.out.println("Physical processing: " + (physicalEndTime - emailEndTime) + "ms");

                        eBoard.sync(tickets);
                        long electronicEndTime = System.currentTimeMillis();
                        System.out.println("Electronic syncing: " + (electronicEndTime - physicalEndTime) + "ms");

                        imageForProcessing.delete();
                        long deletingEndTime = System.currentTimeMillis();
                        System.out.println("Image deleting: " + (deletingEndTime - electronicEndTime) + "ms");
                    } else {
                        Thread.sleep(30000);
                    }
                }
            } else {
                System.out.println("Input expected to be electronic, physical, email or normal");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("App total time: " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.println(sw.toString());
        }
    }
}
