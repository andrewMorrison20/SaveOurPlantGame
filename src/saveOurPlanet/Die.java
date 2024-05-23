package saveOurPlanet;

import java.util.Random;

public class Die {

	/**
	 * constants for min and max die values
	 */
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 6;

	private int die1;
	private int die2;
	private int rollValue;


	/**
	 * constructor
	 **/
	public Die() {
		this.roll();
	}

    /** 
	 * added in getDie1 and getDie2 so we can use in roll()
	 */
	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}

	public int getRollValue() {
		return rollValue;
	}

	
    public void setRollValue() {
        if (die1 < MIN_VALUE || die1 > MAX_VALUE) {
            throw new IllegalArgumentException(
                    "Die 1 value must be between " + MIN_VALUE + " and " + MAX_VALUE + "(inclusive)");
        } else if (die2 < MIN_VALUE || die2 > MAX_VALUE) {
            throw new IllegalArgumentException(
                    "Die 1 value must be between " + MIN_VALUE + " and " + MAX_VALUE + "(inclusive)");
        } else {
            this.rollValue = this.getDie1() + this.getDie2();
        }
    }

    public int roll() {
        Random random = new Random();
        this.die1 = random.nextInt(MAX_VALUE) + 1;
        this.die2 = random.nextInt(MAX_VALUE) + 1;
        setRollValue(); // Call setRollValue() after setting dice values to calculate rollValue
		 return rollValue;
    }
    


}
