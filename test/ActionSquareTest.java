/**
 * 
 */
package saveOurPlanet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
* @author Aisling Broder-Rodgers - 40388027
*/

/**
 * @author aislingbroder-rodgers
 *
 */
class ActionSquareTest {

	//Constants for business rules
	private static final double PRICE_MIN = 0.00;
	private static final int MIN_MINOR_DEV = 0;
	private static final int MAX_MINOR_DEV = 3;
	private static final int MIN_MAJOR_DEV = 0;
	private static final int MAX_MAJOR_DEV = 1;
	private static final int MIN_NAME_LENGTH = 1;
	private static final int MAX_NAME_LENGTH = 30;
	private static final int MIN_INDEX = 0;
	private static final int MAX_INDEX = 11;
	private static final int MIN_OWNER_NAME_LENGTH = 2;
	private static final int MAX_OWNER_NAME_LENGTH = 40;
	
	// Test data
	double validPriceMin, validPrice;
	double invalidPrice;
	
	int validNoMinDevLower, validNoMinDevMid, validNoMinDevUpper;
	int invalidNoMinDevLower, invalidNoMinDevUpper;
	int validNoMajDevLower, validNoMajDevUpper;
	int invalidNoMajDevLower, invalidNoMajDevUpper;
	
	String validNameLower, validNameMid, validNameUpper;
	String invalidNameLower, invalidNameUpper, nullName;
	
	String validOwnerNameLower, validOwnerNameMid, validOwnerNameUpper;
	String invalidOwnerNameLower, invalidOwnerNameUpper;
	
	int validIndexLower, validIndexMid, validIndexUpper;
	int invalidIndexLower, invalidIndexUpper;
	
	SquareType start, neutral, field;
	Field renewableEn, sustainableAg, wasteMan, climateChange, undefined;
	
	double setPrice;
	
	ActionSquare actionSquare;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		actionSquare = new ActionSquare();
		
		validPriceMin = PRICE_MIN;
		validPrice = 500.0;
		invalidPrice = PRICE_MIN-0.01;
		
		validNoMinDevLower = MIN_MINOR_DEV;
		validNoMinDevMid = (MIN_MINOR_DEV+MAX_MINOR_DEV)/2;
		validNoMinDevUpper = MAX_MINOR_DEV;
		invalidNoMinDevLower = MIN_MINOR_DEV-1;
		invalidNoMinDevUpper = MAX_MINOR_DEV+1;
		
		validNoMajDevLower =  MIN_MAJOR_DEV;
		validNoMajDevUpper = MAX_MAJOR_DEV;
		invalidNoMajDevLower = MIN_MAJOR_DEV-1;
		invalidNoMajDevUpper = MAX_MAJOR_DEV+1;
		
		validNameLower = "A".repeat(MIN_NAME_LENGTH);
		validNameMid = "A".repeat((MIN_NAME_LENGTH+MAX_NAME_LENGTH)/2);
		validNameUpper = "A".repeat(MAX_NAME_LENGTH);
		invalidNameLower  = "";
		invalidNameUpper = "A".repeat(MAX_NAME_LENGTH+1);
		nullName = null;
		
		validOwnerNameLower = "A".repeat(MIN_OWNER_NAME_LENGTH);
		validOwnerNameMid = "A".repeat((MIN_OWNER_NAME_LENGTH+MAX_OWNER_NAME_LENGTH)/2);
		validOwnerNameUpper = "A".repeat(MAX_OWNER_NAME_LENGTH);
		invalidOwnerNameLower = "A".repeat(MIN_OWNER_NAME_LENGTH-1);
		invalidOwnerNameUpper = "A".repeat(MAX_OWNER_NAME_LENGTH+1);
		
		validIndexLower = MIN_INDEX;
		validIndexMid = (MIN_INDEX + MAX_INDEX)/2;
		validIndexUpper = MAX_INDEX;
		invalidIndexLower = MIN_INDEX-1;
		invalidIndexUpper = MAX_INDEX+1;
		
		start = SquareType.START;
		neutral = SquareType.NEUTRAL;
		field = SquareType.FIELD;
		
		renewableEn = Field.RENEWABLE_ENERGY_INITIATIVES;
		sustainableAg = Field.SUSTAINABLE_AGRICULTURE;
		wasteMan = Field.WASTE_MANAGMENT;
		climateChange = Field.CLIMATE_CHANGE;
		
		setPrice = 200;
		
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#ActionSquare()}.
	 */
	@Test
	void testActionSquareDefaultConstructor() {
		assertNotNull(actionSquare);
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#ActionSquare(java.lang.String, int, saveOurPlanet.SquareType, saveOurPlanet.Field, double, double, double, double)}.
	 */
	@Test
	void testActionSquareConstructorWithArgs() {
		
		ActionSquare actSq = new ActionSquare(validNameMid, validIndexMid, field, wasteMan, validPrice, validPrice, validPrice, validPrice);
		assertEquals(validNameMid, actSq.getName());
		assertEquals(validIndexMid, actSq.getIndex());
		assertEquals(field, actSq.getType());
		assertEquals(wasteMan, actSq.getField());
		assertEquals(validPrice, actSq.getRent(), 0.01);
		assertEquals(validPrice, actSq.getBuyPrice(), 0.01);
		assertEquals(validPrice, actSq.getMinorDevCost(), 0.01);
		assertEquals(validPrice, actSq.getMajorDevCost(), 0.01);
	
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setBuyPrice(double)}.
	 */
	@Test
	void testGetSetNameValid() {
		
		actionSquare.setName(validNameLower);
		assertEquals(validNameLower, actionSquare.getName());
		
		actionSquare.setName(validNameMid);
		assertEquals(validNameMid, actionSquare.getName());
		
		actionSquare.setName(validNameUpper);
		assertEquals(validNameUpper, actionSquare.getName());
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setBuyPrice(double)}.
	 */
	@Test
	void testGetSetNameInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setName(invalidNameLower);
		});
		assertEquals("Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters long.", iae.getMessage());
		
		IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setName(invalidNameUpper);
		});
		assertEquals("Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters long.", iae2.getMessage());
		
		IllegalArgumentException iae3 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setName(nullName);
		});
		assertEquals("Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters long.", iae3.getMessage());
	}
	
	/**
	 * Test method for {@link saveOurPlanet.Square#setName(java.lang.String)}.
	 */
	@Test
	void testSetBuyPriceValid() {
		
		actionSquare.setBuyPrice(validPriceMin);
		assertEquals(validPriceMin, actionSquare.getBuyPrice(), 0.01);
		
		actionSquare.setBuyPrice(validPrice);
		assertEquals(validPrice, actionSquare.getBuyPrice(), 0.01);
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.Square#setName(java.lang.String)}.
	 */
	@Test
	void testSetBuyPriceInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setBuyPrice(invalidPrice);
		});
		assertEquals("Price Must Be Non-Negative", iae.getMessage());
		
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setMinorDevCost(double)}.
	 */
	@Test
	void testGetSetMinorDevCostValid() {
		
		actionSquare.setMinorDevCost(validPriceMin);
		assertEquals(validPriceMin, actionSquare.getMinorDevCost(), 0.01);
		
		actionSquare.setMinorDevCost(validPrice);
		assertEquals(validPrice, actionSquare.getMinorDevCost(), 0.01);
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setMinorDevCost(double)}.
	 */
	@Test
	void testGetSetMinorDevCostInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setMinorDevCost(invalidPrice);
		});
		assertEquals("Price Must Be Non-Negative", iae.getMessage());
	
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setMajorDevCost(double)}.
	 */
	@Test
	void testSetMajorDevCostValid() {
		
		actionSquare.setMajorDevCost(validPriceMin);
		assertEquals(validPriceMin, actionSquare.getMajorDevCost(), 0.01);
		
		actionSquare.setMajorDevCost(validPrice);
		assertEquals(validPrice, actionSquare.getMajorDevCost(), 0.01);
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setMinorDevCost(double)}.
	 */
	@Test
	void testGetSetMajorDevCostInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setMajorDevCost(invalidPrice);
		});
		assertEquals("Price Must Be Non-Negative", iae.getMessage());
	
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setNoMinorDevs(int)}.
	 */
	@Test
	void testGetSetNoMinorDevsValid() {
		
		actionSquare.setRent(setPrice);
		actionSquare.setNoMinorDevs(validNoMinDevLower);
		assertEquals(validNoMinDevLower, actionSquare.getNoMinorDevs());
		assertEquals(setPrice, actionSquare.getRent(), 0.01);
		
		actionSquare.setNoMinorDevs(validNoMinDevMid);
		assertEquals(validNoMinDevMid, actionSquare.getNoMinorDevs());
		assertEquals(setPrice*1.05, actionSquare.getRent(), 0.01);

		actionSquare.setNoMinorDevs(validNoMinDevMid+1);
		assertEquals(validNoMinDevMid+1, actionSquare.getNoMinorDevs());
		assertEquals(setPrice*1.05*1.05, actionSquare.getRent(), 0.01);

		actionSquare.setNoMinorDevs(validNoMinDevUpper);
		assertEquals(validNoMinDevUpper, actionSquare.getNoMinorDevs());
		assertEquals((200*1.05*1.05*1.05), actionSquare.getRent(), 0.01);

	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setNoMinorDevs(int)}.
	 */
	@Test
	void testGetSetNoMinorDevsInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setNoMinorDevs(invalidNoMinDevLower);
		});
		assertEquals("Number of minor developments must be between "+MIN_MINOR_DEV+" and "+MAX_MINOR_DEV, iae.getMessage());
		
		IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setNoMinorDevs(invalidNoMinDevUpper);
		});
		assertEquals("Number of minor developments must be between "+MIN_MINOR_DEV+" and "+MAX_MINOR_DEV, iae2.getMessage());
		
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setNoMajorDevs(int)}.
	 */
	@Test
	void testGetSetNoMajorDevsValid() {
		
		actionSquare.setNoMajorDevs(validNoMajDevLower);
		assertEquals(validNoMajDevLower, actionSquare.getNoMajorDevs());
		assertEquals(0, actionSquare.getRent(), 0.01);
		
	
		double initialRent = actionSquare.getRent();
		actionSquare.setNoMajorDevs(validNoMajDevUpper);
		assertEquals(validNoMajDevUpper, actionSquare.getNoMajorDevs());
		assertEquals((initialRent*1.15), actionSquare.getRent(), 0.01);

	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setNoMajorDevs(int)}.
	 */
	@Test
	void testGetSetNoMajorDevsInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setNoMajorDevs(invalidNoMajDevLower);
		});
		assertEquals("Number of major developments must be between "+MIN_MAJOR_DEV+" and "+MAX_MAJOR_DEV, iae.getMessage());
		
		IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setNoMajorDevs(invalidNoMajDevUpper);
		});
		assertEquals("Number of major developments must be between "+MIN_MAJOR_DEV+" and "+MAX_MAJOR_DEV, iae2.getMessage());

	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setRent(double)}.
	 */
	@Test
	void testGetSetRentValid() {
		
		actionSquare.setRent(validPriceMin);
		assertEquals(validPriceMin, actionSquare.getRent(), 0.01);
		
		actionSquare.setRent(validPrice);
		assertEquals(validPrice, actionSquare.getRent(), 0.01);
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setRent(double)}.
	 */
	@Test
	void testGetSetRentInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setRent(invalidPrice);
		});
		assertEquals("Price Must Be Non-Negative", iae.getMessage());
		
	}


	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setOwner(java.lang.String)}.
	 */
	@Test
	void testGetSetOwnerValid() {
		
		actionSquare.setOwner(validOwnerNameLower);
		assertEquals(validOwnerNameLower, actionSquare.getOwner());
		
		actionSquare.setOwner(validOwnerNameMid);
		assertEquals(validOwnerNameMid, actionSquare.getOwner());
		
		actionSquare.setOwner(validOwnerNameUpper);
		assertEquals(validOwnerNameUpper, actionSquare.getOwner());
		
		actionSquare.setOwner(nullName);
		assertEquals("Square Currently Unowned", actionSquare.getOwner());
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setOwner(java.lang.String)}.
	 */
	@Test
	void testGetSetOwnerInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setOwner(invalidOwnerNameLower);
		});
		assertEquals("Owner name must be between " + MIN_OWNER_NAME_LENGTH + " and " + MAX_OWNER_NAME_LENGTH + " characters long.", iae.getMessage());
	
		IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setOwner(invalidOwnerNameUpper);
		});
		assertEquals("Owner name must be between " + MIN_OWNER_NAME_LENGTH + " and " + MAX_OWNER_NAME_LENGTH + " characters long.", iae2.getMessage());
	
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setField(saveOurPlanet.Field)}.
	 */
	@Test
	void testGetSetFieldValid() {
		
		actionSquare.setField(renewableEn);
		assertEquals(renewableEn, actionSquare.getField());
		
		actionSquare.setField(sustainableAg);
		assertEquals(sustainableAg, actionSquare.getField());
		
		actionSquare.setField(wasteMan);
		assertEquals(wasteMan, actionSquare.getField());
		
		actionSquare.setField(climateChange);
		assertEquals(climateChange, actionSquare.getField());
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#setField(saveOurPlanet.Field)}.
	 */
	@Test
	void testGetSetFieldInvalid() {
		
		actionSquare.setField(undefined);
		assertEquals(climateChange, actionSquare.getField());
		
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#increaseRentMinor()}.
	 */
	@Test
	void testIncreaseRentMinor() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMinorDevs(MIN_MINOR_DEV);
		assertEquals(setPrice, actSq1.getRent(), 0.01);
		
		ActionSquare actSq2 = new ActionSquare();
		actSq2.setRent(setPrice);
		actSq2.setNoMinorDevs(MAX_MINOR_DEV);
		assertEquals((setPrice*1.05), actSq2.getRent(), 0.01);
		
	}

	/**
	 * Test method for {@link saveOurPlanet.ActionSquare#increaseRentMajor()}.
	 */
	@Test
	void testIncreaseRentMajor() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMajorDevs(MIN_MAJOR_DEV);
		assertEquals(setPrice, actSq1.getRent(), 0.01);
		
		ActionSquare actSq2 = new ActionSquare();
		actSq2.setRent(setPrice);
		actSq2.setNoMajorDevs(MAX_MAJOR_DEV);
		assertEquals((setPrice*1.15), actSq2.getRent(), 0.01);
		
	}

	/**
	 * Test method for {@link saveOurPlanet.Square#setIndex(int)}.
	 */
	@Test
	void testGetSetIndexValid() {
		
		actionSquare.setIndex(validIndexLower);
		assertEquals(validIndexLower, actionSquare.getIndex());
		
		actionSquare.setIndex(validIndexMid);
		assertEquals(validIndexMid, actionSquare.getIndex());
		
		actionSquare.setIndex(validIndexUpper);
		assertEquals(validIndexUpper, actionSquare.getIndex());
		
	}
	
	/**
	 * Test method for {@link saveOurPlanet.Square#setIndex(int)}.
	 */
	@Test
	void testGetSetIndexInvalid() {
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setIndex(invalidIndexLower);
		});
		assertEquals("Index value must be between " + MIN_INDEX + " and " + MAX_INDEX, iae.getMessage());

		IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class, () -> {
			actionSquare.setIndex(invalidIndexUpper);
		});
		assertEquals("Index value must be between " + MIN_INDEX + " and " + MAX_INDEX, iae2.getMessage());

	}
	
	
	/**
	 * Test method for {@link saveOurPlanet.Square#setType(saveOurPlanet.SquareType)}.
	 */
	@Test
	void testGetSetTypeValid() {
		
		actionSquare.setType(field);
		assertEquals(field, actionSquare.getType());

	}
	
	@Test
	void testUpdateNoMinorDevsValid() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMinorDevs(validNoMinDevLower);
		actSq1.updateNoMinorDevs();
		
		double priceInc1 = setPrice*1.05;
		assertEquals(validNoMinDevLower+1, actSq1.getNoMinorDevs());
		assertEquals(priceInc1, actSq1.getRent(), 0.01);
		
		double priceInc2 = priceInc1*1.05;
		actSq1.updateNoMinorDevs();
		assertEquals(validNoMinDevLower+2, actSq1.getNoMinorDevs());
		assertEquals(priceInc2, actSq1.getRent(), 0.01);
		
		double priceInc3 = priceInc2*1.05;
		actSq1.updateNoMinorDevs();
		assertEquals(validNoMinDevUpper, actSq1.getNoMinorDevs());
		assertEquals(priceInc3, actSq1.getRent(), 0.01);	
	}
	
	@Test
	void testUpdateNoMinorDevsInvalid() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMinorDevs(validNoMinDevUpper);
		actSq1.updateNoMinorDevs();
		assertEquals(validNoMinDevUpper, actSq1.getNoMinorDevs());
		
	}
	
	@Test
	void testUpdateNoMajorDevsValid() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMajorDevs(validNoMajDevLower);
		actSq1.updateNoMajorDevs();
		
		double priceInc4 = setPrice*1.15;
		assertEquals( validNoMajDevLower+1, actSq1.getNoMajorDevs());
		assertEquals(priceInc4, actSq1.getRent(), 0.01);
	}
	
	@Test
	void testUpdateNoMajorDevsInvalid() {
		
		ActionSquare actSq1 = new ActionSquare();
		actSq1.setRent(setPrice);
		actSq1.setNoMajorDevs(validNoMajDevUpper);
		actSq1.updateNoMinorDevs();
		assertEquals(validNoMajDevUpper, actSq1.getNoMajorDevs());
		
	}

}