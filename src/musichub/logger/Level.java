package musichub.logger;

public enum Level {
	ERROR("ERROR"), WARNING("WARNING"), INFO("INFO"), DEBUG("DEBUG");

	private String levelValue;

	private Level(String value) {
		levelValue = value;
	}

	public String getLevelValue() {
		return levelValue;
	}
}