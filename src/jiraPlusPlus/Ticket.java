package jiraPlusPlus;

public class Ticket {
    private String id;
    private String status;

    public Ticket(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }
}
