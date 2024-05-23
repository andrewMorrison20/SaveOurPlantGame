package saveOurPlanet;

public class Square {
	
	// Constants for min & max name lengths
	private static final int MIN_NAME_LENGTH = 1;
	private static final int MAX_NAME_LENGTH = 30;
	
	// constants for min & max indexes
	
	private static final int MIN_INDEX = 0;
	private static final int MAX_INDEX = 11;
	
	// Instance variables
	private String name;
	private int index;
	private SquareType type;
	
	
	//Constructor
	Square (String name, int index, SquareType type){
		// set name and index
		setName(name);
		setIndex(index);
		setType(type);
	}

	//default Constructor

	Square(){
		
	}
	
	// Getter for name
	public String getName() {
		return name;
	}
	
	// Setter for name
	public void setName(String name) {
		if(name != null && name.length()>= MIN_NAME_LENGTH && name.length() <= MAX_NAME_LENGTH) {
			this.name=name;
		} else {
			throw new IllegalArgumentException("Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters long.");
		}
	}
	
	// Getter for index
	public int getIndex() {
		return index;
	}
	
	
	// Setter for index
	public void setIndex(int index) {
		if(index >= MIN_INDEX && index <= MAX_INDEX) {
			this.index = index;
		} else {
			throw new IllegalArgumentException("Index value must be between " + MIN_INDEX + " and " + MAX_INDEX);
		}
		
	}
	
	// Getter for type
	
	public SquareType getType() {
		return type;
	}
	
	// Setter for type
	
	public void setType(SquareType type) {
		this.type = type;
	}
	
	
	// Simple function to get all info for the square
	public String getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("You have landed on the square "+this.getName());
		sb.append(" of type "+this.getType()+".");
		sb.append(" at position "+this.getIndex());
		sb.append("\n");
		sb.append("\t> This square is not available for purchase.");
		sb.append("\n");
		sb.append("\t> No Action Required.");
		String toReturn = sb.toString();
		return toReturn;
	}

}
