package saveOurPlanet;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPlayer {
	
// Test data

	// player objects
	Player p1, p2, p3;

	// name 
	String nameMin, nameMid, nameMax, nameInvalidLow, nameInvalidMax;
	
	
	// Not in use as piece is only assigned in player object, it has no methods or attributes to test
	//piece
	Piece validPiece, invalidPiece;
	
	//board
	Board boardValid, boardNull, boardInvalid;
	
	//knowledgePoints
	//no current business rules for this so values set in test
	
	//Die	
	Die diceValid;
	int maxValue, minValue;
	//invalid is only testing for null, will set in test
	
	@BeforeEach
	void setUp() throws Exception {
		
		// name valid
		nameMin = "NN";
		nameMid = "N".repeat(20);
		nameMax = "N".repeat(40);

		// name invalid
		nameInvalidLow = "N";
		nameInvalidMax = "N".repeat(41);
		
		//boardValid
		boardValid = new Board(12, "squaresConfig.csv");
		
		diceValid = new Die();

	}
	
//constructor
	
	/*
	 * Testing valid low mid and high values for name 
	 */
	@Test
	void testConstructorValid() {
		 Piece piece = new Piece(null);
    	Player p1 = new Player(nameMin, piece, diceValid,boardValid,0.0);
	
    	assertEquals(nameMin, p1.getName());
    	assertNotNull("Object should not be null", piece);
    	assertNotNull("Object should not be null", diceValid);
    	assertNotNull("Object should not be null", boardValid);
    	assertEquals(0.0, p1.getKnowledgePoints());
    
	}
	
	/*
	 * Testing valid low mid and high values for name 
	 */
	@Test
	void testConstructorInvalid() {
		//name
		assertThrows(IllegalArgumentException.class, () -> {
			p1 = new Player(nameInvalidLow, null, diceValid, boardValid, 0.0);
		});
		//piece - only assinged in class - no business rules

		//Die
		assertThrows(IllegalArgumentException.class, () -> {
			p3 = new Player(nameMin, null, null, boardValid, 0.0);
		});
		//Board
		assertThrows(IllegalArgumentException.class, () -> {
			p3 = new Player(nameMin, null, diceValid, null, 0.0);
		});
		//knowledgePoints getter but no setter so no invalid test
	}
	
	

//Name
	
	/*
	 * Testing valid low mid and high values for name 
	 */
	@Test
	void testSetNameValid() {
    	Player p1 = new Player(nameMin, null, diceValid,boardValid,0.0);
    	Player p2 = new Player(nameMid, null, diceValid,boardValid,0.0);
    	Player p3 = new Player(nameMax, null, diceValid,boardValid,0.0);
    	
    	assertEquals(nameMin, p1.getName());
    	assertEquals(nameMid, p2.getName());
    	assertEquals(nameMax, p3.getName());
	}

	/*
	 * Testing to see if exception is thrown when invalid name is passed(too long or short)
	 * @throws IllegalArgumentException
	 */
	@Test
	void testSetNameInvalid()throws IllegalArgumentException{
    	assertThrows(IllegalArgumentException.class, ()-> {
			p1 = new Player (nameInvalidLow,  null, diceValid, boardValid,0.0);
		}); 

		assertThrows(IllegalArgumentException.class, ()-> {
			p2 = new Player (nameInvalidMax,  null, diceValid,boardValid,0.0);
		}); 
	}
	
//Piece  - only assinged in class - no business rules or methods to test
	
//Dice
	
	/*
	 * Testing to see dice object is passed to player object
     */
	@Test
	void testSetDiceValid() {    
		Player p1 = new Player(nameMin, null, diceValid, boardValid, 0);
		assertNotNull("Object should not be null", diceValid);
	}
	
	/*
	 * Testing to see if exception is thrown when dice object is null
	 * @throws IllegalArgumentException
	 */
	@Test
	void testSetDiceInvalid() throws IllegalArgumentException {
	   	assertThrows(IllegalArgumentException.class, ()-> {
			p1 = new Player (nameMid,  null, null, boardValid,0);
		}); 
	}
	
	
//Board
	
	/*
	 * Testing to see board object is passed to player object
	 * @throws IOException when file is not found
	 * @throws IllegalArgumentException when board object is null
	 */
	@Test
	void testSetBoardValid() throws IllegalArgumentException, IOException {
		p1 = new Player(nameMid, null, diceValid, boardValid, 500);	
        assertNotNull("Object should not be null", boardValid);
	}
	
	/*
	 * Testing to see if exception is thrown when board object is null
	 * @throws IOException when file is not found
	 * @throws IllegalArgumentException when board object is null
	 */
	@Test
	void testSetBoardInvalid() throws IllegalArgumentException, IOException {
	   	assertThrows(IllegalArgumentException.class, ()-> {
				p1 = new Player (nameMid,  null, null, null,0);
			}); 
	}
		
//knowledgePoints
	
	/*
	 * Testing to see if knowledge points are passed to player object
     */
    @Test
    public void testGetKnowledgePoints() {
        p1 = new Player("John", null, diceValid, boardValid, 500);
        assertEquals(500, p1.getKnowledgePoints());
    }
    
        
	/*
	 * Testing to see if knowledge points are increased
	 */
    @Test
    public void testIncreaseKnowledgePointsValid() {
    	p3 = new Player("Bob", null, diceValid, boardValid, 0);
        p3.increaseKnowledgePoints(50.0);
        assertEquals(50, p3.getKnowledgePoints());
        
    	p3 = new Player("Bob", null, diceValid, boardValid, 27.0);
        p3.increaseKnowledgePoints(5000.0);
        assertEquals(5027.0, p3.getKnowledgePoints());
    }
    

	/*
	 * Testing to see if error is thrown if knowledge points go below 0
	 * @throws IllegalArgumentException
	 */
    @Test
    public void testIncreaseKnowledgePointsInvalid() throws IllegalArgumentException {
        Player player = new Player("Bob", null, diceValid, boardValid, 27.0);
        assertThrows(IllegalArgumentException.class, () -> player.increaseKnowledgePoints(-28.0));
    }
    
	/*
	 * Testing to see if  error is thrown when null value is passed
	 * @throws IllegalArgumentException
	 */
    @Test
    public void testIncreaseKnowledgePointsNull() throws IllegalArgumentException {
        Player player = new Player("Bob", null, diceValid, boardValid, 27.0);
        assertThrows(IllegalArgumentException.class, () -> player.increaseKnowledgePoints(null));
    }
    


    /*
     * Testing to see if knowledge points are decreased
     */
    @Test
    public void testDecreaseKnowledgePoints() {
    	p3 = new Player("Bobert", null, diceValid, boardValid, 100.0);
        p3.decreaseKnowledgePoints(30.0);
        assertEquals(70.0, p3.getKnowledgePoints());
        
    	p3 = new Player("Bobert", null, diceValid, boardValid, 1000.0);
        p3.decreaseKnowledgePoints(364.0);
        assertEquals(636.0, p3.getKnowledgePoints());       
    }
    
    
    /*
     * Testing to see if error is thrown if knowledge points go below 0
     * @throws IllegalArgumentException
     */
    @Test
    public void testDecreaseKnowledgePointsInvalid() {
        Player player = new Player("Bob", null, diceValid, boardValid, 27.0);
        assertThrows(IllegalArgumentException.class, () -> player.decreaseKnowledgePoints(-28.0));
    }
    
    /*
	 * Testing to see if  error is thrown when null value is passed
	 * @throws IllegalArgumentException
     */
    @Test
    public void testDecreaseKnowledgePointsNull() {
    	 Player player = new Player("Bob", null, diceValid, boardValid, 27);
         assertThrows(IllegalArgumentException.class, () -> player.decreaseKnowledgePoints(null));
     }
    
    
        
//Methods

    /*
     * Testing to see if player can play their turn
     * Note this test will fail the dire roll exceeds square limit at this currently does not loop back to start
     */
    @Test
    public void testPlayTurn() throws IllegalArgumentException, IOException {
        // Set up the initial conditions
        //Board board = new Board(12, "squaresConfig.csv");
        Square goSquare = boardValid.getGoSquare();
        //not sure at all how set this argument
        Piece piece = new Piece(goSquare);
        Player p1 = new Player(nameMin, piece, diceValid,boardValid,0);
        Square initialSquare = piece.getPosition();
        // Call the method under test
        p1.playTurn();
        // Assert the expected behaviour
        Square finalSquare = piece.getPosition();
        assertNotEquals(initialSquare, finalSquare);
    }
	/*
	 * Testing to see if multiple players can play their turn
	 * Note this test will fail the dire roll exceeds square limit at this currently does not loop back to start
	 */
    @Test
    public void testMultiplePlayersPlayTurn() throws IllegalArgumentException, IOException {
        // Set up the initial conditions
        //Board board = new Board(12, "squaresConfig.csv");
        Square goSquare = boardValid.getGoSquare();
        //not sure at all how set this argument
        Piece piece1 = new Piece(goSquare);
        Piece piece2 = new Piece(goSquare);
        Piece piece3 = new Piece(goSquare);
        Piece piece4 = new Piece(goSquare);
   
        Player p1 = new Player(nameMin, piece1, diceValid,boardValid,0);
        Player p2 = new Player(nameMid, piece2, diceValid,boardValid,0);
        Player p3 = new Player(nameMax, piece3, diceValid,boardValid,0);
        Player p4 = new Player(nameMin, piece4, diceValid,boardValid,0);
        
        Square initialSquare1 = piece1.getPosition();
        Square initialSquare2 = piece2.getPosition();
        Square initialSquare3 = piece3.getPosition();
        Square initialSquare4 = piece4.getPosition();
        
        // Call the method under test
        p1.playTurn();
        p2.playTurn();
        p3.playTurn();
    	p4.playTurn();
   
        // Assert the expected behavior
        Square finalSquare1 = piece1.getPosition();
        Square finalSquare2 = piece2.getPosition();
        Square finalSquare3 = piece3.getPosition();
        Square finalSquare4 = piece4.getPosition();
        assertNotEquals(initialSquare1, finalSquare1);
        assertNotEquals(initialSquare2, finalSquare2);
        assertNotEquals(initialSquare3, finalSquare3);
        assertNotEquals(initialSquare4, finalSquare4);
    }
    
    //not sure what unhappy path tests to do here as all expected errors are already tested for in other tests
	
    @Test
    public void testPlayerStatsSummary() {
    	p1 = new Player("John", null, diceValid, boardValid, 500.0);
        String expectedSummary = "**================**\n" +
                                 "Player : John\n" +
                                 "Knowledge Points : 500.0\n" +
                                 "**================**";
        assertEquals(expectedSummary, p1.playerStatsSummary());
    }

}
