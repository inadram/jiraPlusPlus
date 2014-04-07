package jiraPlusPlus;

public class Ticket {
	private String id;
	private String status;
	private float position;

	public Ticket(String id, float position) throws Exception {
		if (id.trim().length() < 1) {
			throw new Exception("Ticket Id cannot be empty");
		}
		this.id = id;
		this.position = position;
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

	public float getPosition() {
		return this.position;
	}
}
