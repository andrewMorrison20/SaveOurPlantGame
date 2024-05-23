package saveOurPlanet;

public enum SquareType {
	START("Start"), NEUTRAL("Neutral"), FIELD("Field");

	private final String displayName;

	private SquareType(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

}

