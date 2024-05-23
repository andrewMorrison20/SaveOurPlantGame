package saveOurPlanetTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import saveOurPlanet.SquareType;
import saveOurPlanet.ActionSquare;
import saveOurPlanet.Board;
import saveOurPlanet.Field;
import saveOurPlanet.Square;

class BoardTest {
	/**
	 * declaring test data
	 * note test data for squares - not testing the square class so only including valid data needed for board methods.
	 * @throws Exception
	 */
		int noSquaresValidMin,noSquaresValidMid, noSquaresValidMax, noSquaresInvalidMin,noSquaresInvalidMax;
		String fileNameSuffixValid,fileNameSuffixInvalid,fileNameValidMin,fileNameValidMid,fileNameValidMax,fileNameInvalidMin,fileNameInvalidMax;
		String squareNameValid;
		SquareType type;
		Field field;
		int validIndex;
		double validPrice;
		String validSquareName1,validSquareName2;
		Board board;
		ActionSquare square1,square2;
		ArrayList<ActionSquare> squares;
	
		String testFile;
	@BeforeEach
	void setUp() throws Exception {
		//test data for number of squares on board
		noSquaresValidMin =2;
		noSquaresValidMid=6;
		noSquaresValidMax=12;
		noSquaresInvalidMin=1;
		noSquaresInvalidMax=13;
		//Invalid file name based on suffix
		fileNameSuffixInvalid = "invalidFileSuffix.xml";
		fileNameValidMin = "a".repeat(3) + ".csv";
		fileNameValidMid = "a".repeat(11)+".csv";
		fileNameValidMax = "a".repeat(26)+".csv";
		testFile ="testBoard.csv";
		validPrice = 100;
		//Invalid file name length test data
		fileNameInvalidMin = "a".repeat(2)+".csv";
		fileNameInvalidMax = "a".repeat(27)+".csv";
		//Field type for squares 
		field = Field.RENEWABLE_ENERGY_INITIATIVES;
		//type
		type = SquareType.FIELD;
		//Valid squareName for squares
		validSquareName1 = "validsquare1";
		validSquareName2 = "validsquare2";
		square1 = new ActionSquare(validSquareName1, 0,type, field, validPrice, validPrice, validPrice, validPrice);
		square2 = new ActionSquare(validSquareName2,1,type, field, validPrice, validPrice, validPrice, validPrice);
		squares = new ArrayList<ActionSquare>();
		System.out.println(square1.getAll());
		squares.add(square1);
		squares.add(square2);
		board = new Board(noSquaresValidMin,testFile);
		}

	
	@Test
	 void testConstructorValidArgs() throws IllegalArgumentException, IOException {
		board = new Board(noSquaresValidMin,testFile);
		assertEquals(noSquaresValidMin,board.getNoSquares());
		//check file methods have successfully created file
		assertNotNull(board.getConfigFile());
		//using predefined toString method of File class - no need to test
		assertEquals(testFile,board.getConfigFile().toString());
	 }
	
	@Test
    void setFileValidFileNameWithinLengthRange(){
		
        assertDoesNotThrow(() -> board.setFile(testFile));
        assertEquals(testFile,board.getConfigFile().toString());
    }

    @Test
    void setFileInvalidFileNameSuffix() {
    	
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setFile(fileNameSuffixInvalid);
        });
        assertEquals("Invalid file type, must be csv or txt file", exception.getMessage());
    }

    @Test
    void setFileNameLengthBelowMinimum() {
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setFile(fileNameInvalidMin);
        });
        assertEquals("File name length outside allowed range", exception.getMessage());
    }

    @Test
    void setFileNameLengthAboveMaximum() {
       
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
          board.setFile(fileNameInvalidMax);
        });
        assertEquals("File name length outside allowed range", exception.getMessage());
    }


	@Test
	void testCreateGetSquares() throws IllegalArgumentException, IOException {
		ArrayList<ActionSquare> expected = squares;
		boolean match = false;
		board.setFile(testFile);
		board.createSquares();
		ArrayList<Square> actual = board.getBoard();
		ActionSquare sq1 = (ActionSquare)actual.get(0);
		ActionSquare sq2 = (ActionSquare)actual.get(1);
		if(sq1.equals(expected.get(0)) && sq2.equals(expected.get(0))) {
			match = true;
		}
	assertTrue(match);
	}

	@Test
	void testGetSetNoSquaresValid() {
		int expected = noSquaresValidMin;
		board.setNoSquares(expected);
		int actual = board.getNoSquares();
		assertEquals(expected, actual);
		
		expected = noSquaresValidMid;
		board.setNoSquares(expected);
		actual = board.getNoSquares();
		assertEquals(expected, actual);
		
		expected = noSquaresValidMax;
		board.setNoSquares(expected);
		actual = board.getNoSquares();
		assertEquals(expected, actual);
	}

	@Test
	void testGetSquare() {
		
	}

	@Test
	void testGetGoSquare() throws IllegalArgumentException, IOException {
		board.createSquares();
		Square expected = board.getBoard().get(0);
		assertEquals(expected, board.getGoSquare());
	}

	
	
}

