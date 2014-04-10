package jiraPlusPlus;

public class ColumnHeader {
	private String id;
	private float x;
	private float y;

	public ColumnHeader(String id, float x, float y) throws Exception {
		if (id.trim().length() < 1) {
			throw new Exception("Header Id cannot be empty");
		}
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public String getId() {
		return this.id;
	}

	public float getX() {
		return this.x;
	}

    public float getY() {
        return this.y;
    }
}
