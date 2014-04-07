package jiraPlusPlus;

public class ColumnHeader {
	private String id;
	private float position;

	public ColumnHeader(String id, float position) throws Exception {
		if (id.trim().length() < 1) {
			throw new Exception("Header Id cannot be empty");
		}
		this.id = id;
		this.position = position;
	}

	public String getId() {
		return this.id;
	}

	public float getPosition() {
		return this.position;
	}
}
