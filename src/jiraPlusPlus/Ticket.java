package jiraPlusPlus;

public class Ticket {
	private String id;
	private String status;
	private float x;
	private float y;

	public Ticket(String id, float x, float y) throws Exception {
		if (id.trim().length() < 1) {
			throw new Exception("Ticket Id cannot be empty");
		}
		this.id = id;
		this.x = x;
        this.y = y;
	}

	public String getId() {
		return this.id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public float getX() {
		return this.x;
	}

    public float getY() {
        return this.y;
    }
}
