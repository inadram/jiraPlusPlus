package jiraPlusPlus;

public class Ticket {
    private String id;
    private String status;

    public Ticket(String id, String status) throws Exception {
        if (id.trim().length() < 1) {
            throw new Exception("Ticket Id cannot be empty");
        }
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
