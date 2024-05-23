package saveOurPlanet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSquare {
	
	// test data
	
	int lowIndexValid = 1, midIndexValid = 5, highIndexValid = 10, lowIndexInvalid = -1, highIndexInvalid = 20;
	String lowNameValid = "a", midNameValid = "a".repeat(15), highNameValid = "a".repeat(30), lowNameInvalid = "", highNameInvalid = "a".repeat(31);
	SquareType field = SquareType.FIELD;
	SquareType go = SquareType.START;
	SquareType neut = SquareType.NEUTRAL;
	Square square;

	@BeforeEach
	void setUp() throws Exception {
		square = new Square(lowNameValid, lowIndexValid, SquareType.FIELD); // Create a square instance for testing
	}

	@Test
	void testSquare() {
		assertNotNull(square);
	}

	@Test
	void testValidSquare() {
		assertEquals(lowNameValid, square.getName());
		assertEquals(lowIndexValid, square.getIndex());
		assertEquals(field, SquareType.FIELD);
	}

	@Test
	void testNewValidSquare() {
		Square newSquare = new Square(midNameValid, highIndexValid, SquareType.NEUTRAL);
		assertEquals(midNameValid, newSquare.getName());
		assertEquals(highIndexValid, newSquare.getIndex());
		assertEquals(SquareType.NEUTRAL, newSquare.getType());
	}

	@Test
	void testInvalidNameSquare() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			Square invalidSquare = new Square(lowNameInvalid, lowIndexValid, SquareType.FIELD);
		});
	}

	@Test
	void testInvalidIndexSquare() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			Square invalidSquare = new Square(lowNameValid, lowIndexInvalid, SquareType.FIELD);
		});
	}

	@Test
	void testGetName() {
		assertEquals(lowNameValid, square.getName());
	}

	@Test
	void testSetName() {
		square.setName(midNameValid);
		assertEquals(midNameValid, square.getName());
	}

	@Test
	void testSetNameNull(){
		assertThrows(IllegalArgumentException.class, () -> {
			square.setName(null);
		});
	}

	@Test
	void testGetSetName(){
		square.setName(lowNameValid);
		assertEquals(lowNameValid, square.getName());
		square.setName(midNameValid);
		assertEquals(midNameValid, square.getName());
		square.setName(highNameValid);
		assertEquals(highNameValid, square.getName());
	}
	
	void testSetNameLowInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			square.setName(lowNameInvalid); 
		});
	}

	void testSetNameHighInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			square.setName(highNameInvalid); 
		});
	}

	@Test
	void testGetIndex() {
		assertEquals(lowIndexValid, square.getIndex());
	}

	@Test
	void testGetSetIndex(){
		square.setIndex(lowIndexValid);
		assertEquals(lowIndexValid, square.getIndex());
		square.setIndex(midIndexValid);
		assertEquals(midIndexValid, square.getIndex());
		square.setIndex(highIndexValid);
		assertEquals(highIndexValid, square.getIndex());
	}

	@Test
	void testSetIndex() {
		square.setIndex(midIndexValid);
		assertEquals(midIndexValid, square.getIndex());
	}
	
	@Test
	void testSetIndexLowInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			square.setIndex(lowIndexInvalid); 
		});
	}

	@Test
	void testSetIndexHighInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			square.setIndex(highIndexInvalid); 
		});
	}

	@Test
	void testGetType() {
		assertEquals(SquareType.FIELD, square.getType());
	}

	@Test
	void testSetType() {
		square.setType(go); 
		assertEquals(SquareType.START, square.getType());
	}

	@Test
	void testGetAll() {
		String expected = "Name: " + lowNameValid + ", Index: " + lowIndexValid + ", Type: " + SquareType.FIELD;
		assertEquals(expected, square.getAll());
	}

}
