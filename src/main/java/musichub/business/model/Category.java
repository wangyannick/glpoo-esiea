package main.java.musichub.business.model;

/**
 * enumeration for category
 * @author ali
 *
 */
public enum Category {
	YOUTH ("youth"), NOVEL ("novel"), THEATER ("theater"), DOCUMENTARY ("documentary"), SPEECH("speech");
	private String category;
	private Category (String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
}