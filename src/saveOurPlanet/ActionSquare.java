package saveOurPlanet;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ActionSquare extends Square {

// Constants for Business Rules
	private static final double PRICE_MIN = 0.00;
	private static final int MIN_MINOR_DEV = 0;
	private static final int MAX_MINOR_DEV = 3;
	private static final int MIN_MAJOR_DEV = 0;
	private static final int MAX_MAJOR_DEV = 1;
	private static final int MIN_OWNER_NAME_LENGTH = 2;
	private static final int MAX_OWNER_NAME_LENGTH = 40;
	
	
	// Instance vars
	private double buyPrice;
	private double minorDevCost;
	private double majorDevCost;
	private int noMinorDevs;
	private int noMajorDevs;
	private double rent;
	private String owner;
	private Field field;
	
	// Constructors
	/**
	 * Default Constructor 
	 */
	public ActionSquare() {
		
	}

	/**
	 * Main constructor for Action Square
	 * @param name
	 * @param index
	 * @param type
	 * @param buyPrice
	 * @param rent
	 * @param minorDevCost
	 * @param majorDevCost
	 */
	public ActionSquare(String name, int index, SquareType type, Field field, double rent, double buyPrice, double minorDevCost, double majorDevCost) {
		super(name, index, type);
		setBuyPrice(buyPrice);
		setMinorDevCost(minorDevCost);
		setMajorDevCost(majorDevCost);
		setRent(rent);
		setField(field);
	}
		
	// Getters and Setters
	/**
	 * Gets the price of buying the square
	 * @return the buyPrice
	 */
	public double getBuyPrice() {
		return buyPrice;
	}

	/**
	 * Sets the price of buying the square - if invalid, exception is thrown
	 * @param buyPrice - the buyPrice to set
	 */
	public void setBuyPrice(double buyPrice) throws IllegalArgumentException {
		
		if (buyPrice >= PRICE_MIN) {
			this.buyPrice = buyPrice;
		} else {
			throw new IllegalArgumentException("Price Must Be Non-Negative");
			// System.out.println("Price Invalid, Setting to 100");
			// this.buyPrice = 100.00;
		}
	}

	/**
	 * Gets the cost of developing a minor development on a square
	 * @return the minorDevCost
	 */
	public double getMinorDevCost() {
		return minorDevCost;
	}

	/**
	 * Sets the cost of developing a minor development on a square - if invalid, exception is thrown
	 * @param minorDevCost the minororDevCost to set
	 */
	public void setMinorDevCost(double minorDevCost) throws IllegalArgumentException {
		if (minorDevCost >= PRICE_MIN) {
			this.minorDevCost = minorDevCost;
		} else {
			throw new IllegalArgumentException("Price Must Be Non-Negative");
			// System.out.println("Price Invalid, Setting to 20");
			// this.minorDevCost = 20.00;
		}
	}

	/**
	 * Gets the cost of developing a major development on a square
	 * @return the majorDevCost
	 */
	public double getMajorDevCost() {
		return majorDevCost;
	}

	/**
	 * Sets the cost of developing a major development on a square - if invalid, exception is thrown
	 * @param majorDevCost the majorDevCost to set
	 */
	public void setMajorDevCost(double majorDevCost) throws IllegalArgumentException {
		if (majorDevCost >= PRICE_MIN) {
			this.majorDevCost = majorDevCost;
		} else {
			throw new IllegalArgumentException("Price Must Be Non-Negative");
			// System.out.println("Price Invalid, Setting to 70");
			// this.majorDevCost = 70.00;
		}
	}

	/**
	 * Gets the number of minor developments currently on a square
	 * @return the noMinorDevs
	 */
	public int getNoMinorDevs() {
		return noMinorDevs;
	}

	/**
	 * Sets the number of minor developments on a square - if invalid, exception is thrown
	 * @param noMinorDevs - the number of minor developments to set
	 */
	public void setNoMinorDevs(int noMinorDevs) throws IllegalArgumentException {
		
		if (noMinorDevs >= MIN_MINOR_DEV && noMinorDevs <= MAX_MINOR_DEV) {
			this.noMinorDevs = noMinorDevs;
			increaseRentMinor();
		} else {
			throw new IllegalArgumentException("Number of minor developments must be between "+MIN_MINOR_DEV+" and "+MAX_MINOR_DEV);
			// System.out.println("Invalid number of minor developments, reverting to 0.");
			// this.noMinorDevs = 0;
		}
	}

	/**
	 * Gets the number of major developments currently on a square
	 * @return the noMajorDevs
	 */
	public int getNoMajorDevs() {
		return noMajorDevs;
	}

	/**
	 * Sets the number of major developments on a square - if invalid, exception is thrown
	 * @param noMajorDevs - the number of major developments to set
	 */
	public void setNoMajorDevs(int noMajorDevs) throws IllegalArgumentException {
		if (noMajorDevs >= MIN_MAJOR_DEV && noMajorDevs <= MAX_MAJOR_DEV) {
			this.noMajorDevs = noMajorDevs;
			increaseRentMajor();
		} else {
			throw new IllegalArgumentException("Number of major developments must be between "+MIN_MAJOR_DEV+" and "+MAX_MAJOR_DEV);
			// System.out.println("Invalid number of major developments, reverting to 0.");
			// this.noMajorDevs = 0;
		}
	}

	/**
	 * Gets the cost of renting required to be paid when a player lands on a square they do not own
	 * @return the rent
	 */
	public double getRent() {
		return rent;
	}

	/**
	 * Sets the cost of renting required to be paid when a player lands on a square they do not own - if invalid, exception is thrown
	 * @param rent the rent to set
	 */
	public void setRent(double rent) throws IllegalArgumentException {
		
		if (rent >= PRICE_MIN) {
			this.rent = rent;
		} else {
			throw new IllegalArgumentException("Price Must Be Non-Negative");
			// System.out.println("Rent Price Invalid, Setting to 30");
			// this.rent = 30.00;
		}
	}

	/**
	 * Gets the owner of the square
	 * If null - the square is currently unowned and the string "Square Currently Unowned" is returned rather than null
	 * @return the owner (or "Square Currently Unowned" if owner is null)
	 */
	public String getOwner() {
	
		if (this.owner == null) {
			return "Square Currently Unowned";
		} else {
			return owner;
		}	
		
	}

	/**
	 * Sets the owner of the square
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {

		if (owner == null) {
			this.owner = owner;
		} else if  (!(owner==null) && (owner.length() >= MIN_OWNER_NAME_LENGTH) && (owner.length() <= MAX_OWNER_NAME_LENGTH)) {
			this.owner = owner;
		} else {
			throw new IllegalArgumentException("Owner name must be between " + MIN_OWNER_NAME_LENGTH + " and " + MAX_OWNER_NAME_LENGTH + " characters long.");
		}
	}
	
	/**
	 * Gets the field of the square (RENEWABLE_ENERGY_INITIATIVES, SUSTAINABLE_AGRICULTURE, WASTE_MANAGMENT or CLIMATE_CHANGE)
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Sets the field of the square (RENEWABLE_ENERGY_INITIATIVES, SUSTAINABLE_AGRICULTURE, WASTE_MANAGMENT or CLIMATE_CHANGE)
	 * @param field - the field to set
	 */
	public void setField(Field field) {
		
		if (field==null) {
			System.err.println("Cannot set field to Null. Assuming Default - Climate Change");
			this.field = Field.CLIMATE_CHANGE;
		} else {
			this.field = field;
		}
	}

	// Methods

		/**
		 * Method to increment the current number of minor developments by one
		 * Sets NoMinorDevs as this value if below the maximum possible number of minor developments 
		 * @throws IllegalArgumentException
		 */
		public void updateNoMinorDevs() throws IllegalArgumentException {
			
			int currentNoMinorDevs = this.getNoMinorDevs();
			int updatedNoMinorDevs = ++currentNoMinorDevs;
			
			
			if (updatedNoMinorDevs <= MAX_MINOR_DEV) {
				setNoMinorDevs(updatedNoMinorDevs);
			} else {
				System.out.println("Maximum number ("+MAX_MINOR_DEV+") of minor developments already exist.");
			}
		}
		
		/**
		 * Method to increment the current number of major developments by one
		 * Sets NoMajorDevs as this value if below the maximum possible number of major developments 
		 * Sets NoMinorDevs to 0 
		 * @throws IllegalArgumentException
		 */
		public void updateNoMajorDevs() throws IllegalArgumentException {
			
			int currentNoMajorDevs = this.getNoMajorDevs();
			int updatedNoMajorDevs = ++currentNoMajorDevs;
			
			
			if (updatedNoMajorDevs <= MAX_MAJOR_DEV) {
				setNoMajorDevs(updatedNoMajorDevs);
				setNoMinorDevs(0);
			} else {
				System.out.println("Maximum number ("+MAX_MAJOR_DEV+") of major developments already exist.");
			}
		}
	
	/**
	 * Method to increase the rent of a square following developing minor development
	 *Price increases by 5% for each minor development added
	 * NB: price increases incrementally (assumption of game logic: can only build one minor dev at a time)
	 * 
	 */
	public void increaseRentMinor () {	
		
		double currentRent = getRent();
		int numMinDev = this.getNoMinorDevs();
		
		if (numMinDev > 0) {
				double updatedRent = (currentRent)*(1.05);
				setRent(updatedRent);
		}
		
	}
	
	/**
	 * Method to increase the rent of a square following developing major development
	 *  Price increases by 15% for major development added
	 * NB: price increases incrementally following on from minor developments 
	 * (Assumption of game logic: can only build one minor dev at a time and must achieve maximum before building major dev)
	 * 
	 */
	public void increaseRentMajor () {	
		
		double currentRent = getRent();
		int numMajDev = this.getNoMajorDevs();

		if (numMajDev > 0) {
			double updatedRent = (currentRent)*(1.15);
			setRent(updatedRent);
		}
	}
	
	/**
	 * Formats the price to two decimal places
	 * @return
	 */
	public String getFormatPrice (double price) {
		String formattedPrice = String.format("%.2f", price);
		return formattedPrice;
	}

	/**
	 * Prints the relevant details of a square object - depending on whether player lands on either an owned or unowned square
	 */
	@Override
	public String getAll() {
		
		String rentFormatPrice = getFormatPrice(this.getRent());
		String buyFormatPrice = getFormatPrice(this.getBuyPrice());
		String minorDevFormatPrice = getFormatPrice(this.getMinorDevCost());
		String majorDevFormatPrice = getFormatPrice(this.getMajorDevCost());
		
		if (this.getOwner().equals("Square Currently Unowned")) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("You have landed on the square "+this.getName());
			sb.append(" at position "+this.getIndex());
			sb.append(" which belongs to the field of "+this.getField().toString()+".");
			try {
			    // (you can adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			sb.append("\n");
			sb.append("\n");
			sb.append("This square is currently unowned.");
			try {
			    // (adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			sb.append("\n");
			sb.append("\t> The cost to buy this square is "+buyFormatPrice+" Knowledge Points and initial rental charge will be "+rentFormatPrice+" Knowledge Points.");
			sb.append("\n");
			sb.append("\t> The cost to build a minor development will be "+minorDevFormatPrice+" Knowledge Points.");
			sb.append("\n");
			sb.append("\t> The cost to build a major development will be "+majorDevFormatPrice+" Knowledge Points.");
			String toReturn = sb.toString();
			return toReturn;
			
		} else {

			StringBuilder sb = new StringBuilder();
			sb.append("You have landed on the square "+this.getName());
			sb.append(" at position "+this.getIndex());
			sb.append(" which belongs to the field of "+this.getField()+".");
			try {
			    //(you can adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			sb.append("\n");
			sb.append("\t> This square is owned by "+this.getOwner()+".");
			sb.append("\n");
			sb.append("\t> There are currently "+this.getNoMinorDevs()+" minor developments and "+this.getNoMajorDevs()+" major developments on this square.");
			sb.append("\n");
			sb.append("\t> The rent to stay on this square is "+rentFormatPrice+" Knowledge Points.");
			try {
			    //  (you can adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			String toReturn = sb.toString();
			return toReturn;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(buyPrice, field, majorDevCost, minorDevCost, noMajorDevs, noMinorDevs, owner, rent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionSquare other = (ActionSquare) obj;
		return Double.doubleToLongBits(buyPrice) == Double.doubleToLongBits(other.buyPrice) && field == other.field
				&& Double.doubleToLongBits(majorDevCost) == Double.doubleToLongBits(other.majorDevCost)
				&& Double.doubleToLongBits(minorDevCost) == Double.doubleToLongBits(other.minorDevCost)
				&& noMajorDevs == other.noMajorDevs && noMinorDevs == other.noMinorDevs
				&& Objects.equals(owner, other.owner)
				&& Double.doubleToLongBits(rent) == Double.doubleToLongBits(other.rent);
	}


}
