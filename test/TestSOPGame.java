package saveOurPlanet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSOPGame {
    private ByteArrayOutputStream outContent;
    private final InputStream originalIn = System.in;
    
    
 // Create an instance of SOPGame
    SOPGame game = new SOPGame();

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(System.out);
        System.setIn(originalIn);
    }

// Test for valid number of players
    @Test
    void testEnterNumberPlayersValidInput() throws Exception {
        provideMockUserInput("3\n");
        assertEquals(3, SOPGame.enterNumberPlayers());
    }

// Test for the lowest valid number of players
    @Test
    void testEnterNumberPlayersLowValidInput() throws Exception {
        provideMockUserInput("2\n");
        assertEquals(2, SOPGame.enterNumberPlayers());
    }

// Test for the highest valid number of players
    @Test
    void testEnterNumberPlayersHighValidInput() throws Exception {
        provideMockUserInput("4\n");
        assertEquals(4, SOPGame.enterNumberPlayers());
    }

// Test for valid player names entry
    @Test
    void testEnterPlayerNamesValidInput() throws Exception {
        provideMockUserInput("Joel\nLeonie\nAndrew\n");
        List<String> expectedNames = Arrays.asList("Joel", "Leonie", "Andrew");
        assertEquals(expectedNames, SOPGame.enterPlayerNames(3));
    }

//Test for mixture of Valid & Invalid names

    @Test
    void testEnterPlayerNamesInvalidInput() throws Exception {
        provideMockUserInput("Joel\nA\nNathaniel\n");
        List<String> expectedNames = Arrays.asList("Joel", "Nathaniel");
        assertEquals(expectedNames, SOPGame.enterPlayerNames(2));
    }

// Test for max names

    @Test
    void testEnterMaxNames() throws Exception {
        provideMockUserInput("Joel\nNathaniel\nLeonie\nAndrew\n");
        List<String> expectedNames = Arrays.asList("Joel", "Nathaniel", "Leonie", "Andrew");
        assertEquals(expectedNames, SOPGame.enterPlayerNames(4));
    }



//Test for Player Choice - Y
    @Test
    void testGetPlayerChoiceAffirmitive() {
        provideMockUserInput("Y\n");
        assertEquals("Y", SOPGame.getPlayerChoice());
    }

// Test for Player Choice - N
    @Test
    void testGetPlayerChoiceNegative() {
        provideMockUserInput("n\n");
        assertEquals("N", SOPGame.getPlayerChoice());
    }

// Test for invalid then valid choice 
    @Test
    void testGetPlayerChoice_InvalidThenValid() {
        provideMockUserInput("invalid\nY\n");
        assertEquals("Y", SOPGame.getPlayerChoice());
    }

// Paid of Tests for PrintFieldMap for a single Field owned by a single player - should probably try more complex cases
    @Test
    void testPrintFieldMap_SingleOwnerSingleFieldOwnerTest() {
        HashMap<Field, List<String>> fieldOwners = new HashMap<>();
        Field field = Field.RENEWABLE_ENERGY_INITIATIVES;
        List<String> owners = Collections.singletonList("Owner 1");
        fieldOwners.put(field, owners);
        game.printFieldMap(fieldOwners);
        assertTrue(outContent.toString().contains("Owner 1"));
    }

    @Test
    void testPrintFieldMap_SingleOwnerSingleFieldFieldTest() {
        HashMap<Field, List<String>> fieldOwners = new HashMap<>();
        Field field = Field.RENEWABLE_ENERGY_INITIATIVES;
        List<String> owners = Collections.singletonList("Owner 1");
        fieldOwners.put(field, owners);
        game.printFieldMap(fieldOwners);
        assertTrue(outContent.toString().contains("RENEWABLE_ENERGY_INITIATIVES"));
    }


// CreateFieldMap Test
    @Test
    void testCreateFieldMap() throws IllegalArgumentException, IOException {
        Board board = new Board(12, "squaresConfig.csv");
        HashMap<Field, List<String>> fieldMap = game.createFieldMap(board);
        assertNotNull(fieldMap);
        assertEquals(4, fieldMap.size());
        assertTrue(fieldMap.containsKey(Field.SUSTAINABLE_AGRICULTURE));
        assertEquals(2, fieldMap.get(Field.SUSTAINABLE_AGRICULTURE).size());
        assertTrue(fieldMap.containsKey(Field.WASTE_MANAGMENT));
        assertEquals(3, fieldMap.get(Field.WASTE_MANAGMENT).size());
        assertTrue(fieldMap.containsKey(Field.RENEWABLE_ENERGY_INITIATIVES));
        assertEquals(3, fieldMap.get(Field.RENEWABLE_ENERGY_INITIATIVES).size());
        assertTrue(fieldMap.containsKey(Field.CLIMATE_CHANGE));
        assertEquals(2, fieldMap.get(Field.CLIMATE_CHANGE).size());
    }

//Test for the offer of a minor Dev
    @Test
    void testOfferMinorDevelopment() throws IllegalArgumentException, IOException {
        Board board = new Board(12, "squaresConfig.csv");
        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 200, 100, 200);
        Player player = new Player("Test Player", new Piece(square), new Die(), board, 200);
        provideMockUserInput("YES");
        game.offerMinorDevelopment(square, player);
        assertTrue(outContent.toString().contains("Successfully added Development to Test Square"));
        assertEquals(100, player.getKnowledgePoints());
        outContent.reset();
        provideMockUserInput("NO");
        game.offerMinorDevelopment(square, player);
        assertTrue(outContent.toString().contains("No development carried out.."));
    }

//Pair of Tests for the offer of a major Dev
    @Test
    void testOfferMajorDevelopmentYes() throws IllegalArgumentException, IOException {
        Board board = new Board(12, "squaresConfig.csv");
        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 200, 100, 200);
        Player player = new Player("Test Player", new Piece(square), new Die(), board, 200);
        provideMockUserInput("YES");
        game.offerMajorDevelopment(square, player);
        assertTrue(outContent.toString().contains("Successfully added Major Development to Test Square"));
        assertEquals(0, player.getKnowledgePoints());
    }

    @Test
    void testOfferMajorDevelopmentNo() throws IllegalArgumentException, IOException {
        Board board = new Board(12, "squaresConfig.csv");
        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 200, 100, 200);
        Player player = new Player("Test Player", new Piece(square), new Die(), board, 200);
        provideMockUserInput("NO");
        game.offerMajorDevelopment(square, player);
        assertTrue(outContent.toString().contains("No development"));
    }

Test for offering a square and player accepts
    @Test
    void testOfferSquareAccept() throws IllegalArgumentException, IOException {
        // Create a player
        Player player = new Player("Test Player", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 200);
        
        // Create an action square
        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 200, 100, 200);
        
        // Set up mock user input for the choice
        provideMockUserInput("Y\n");
        
        // Call the offerSquare method
        boolean owned = SOPGame.offerSquare(player, square);
        
        // Assert that the square is now owned by the player
        assertTrue(owned);
        
    
        
    }

 // Test for offering a square and player declines
    @Test
    void testOfferSquareDeclined() throws IllegalArgumentException, IOException {
        // Create a player
        Player player = new Player("Test Player", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 200);
        
        // Create an action square
        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 200, 100, 200);
        
        // Set up mock user input for the choice
        provideMockUserInput("N\nN\n");
        
        // Call the offerSquare method
        boolean owned = SOPGame.offerSquare(player, square);
        
        // Assert that the square is not now owned by the player
        assertFalse(owned);        
    }

 // Test for purchasing a square - need to take the private off in SOPGame to run 
//    @Test
//    void testPurchaseSquare() throws IllegalArgumentException, IOException {
//        // Create a player
//        Player player = new Player("Test Player", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 200);
//        
//        // Create an action square
//        ActionSquare square = new ActionSquare("Test Square", 1, SquareType.FIELD, Field.RENEWABLE_ENERGY_INITIATIVES, 50, 100, 100, 200);
//        
//        // Purchase the square
//        SOPGame.purchaseSquare(player, square);
//        
//        // Assert that the square is owned by the player
//        assertEquals(player.getName(), square.getOwner());
//        
//        // Assert that the player's knowledge points have been decreased
//        assertEquals(100, player.getKnowledgePoints());
//    }

 // Couple basic tests for DeclareWinner function - some functions need to be made public for these too run
//    @Test
//    void testDeclareWinner() throws IllegalArgumentException, IOException {
//        // Create mock players
//        Player player1 = new Player("Player 1", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 500);
//        Player player2 = new Player("Player 2", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 700);
//        Player player3 = new Player("Player 3", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 300);
//        List<Player> players = new ArrayList<>();
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//
//        // Redirect console output to ByteArrayOutputStream
//        System.setOut(new PrintStream(outContent));
//
//        SOPGame.players = players;
//        
//        // Call declareWinner method
//        SOPGame.declareWinner();
//
//        // Get the printed output
//        String printedOutput = getConsoleOutput();
//
//        // Assert that the output contains expected messages
//        assertTrue(printedOutput.contains("1st Place: Player 2 with 700.00 Knowledge Points"));
//        assertTrue(printedOutput.contains("2nd Place: Player 1 with 500.00 Knowledge Points"));
//        assertTrue(printedOutput.contains("3rd Place: Player 3 with 300.00 Knowledge Points"));
//    }
//
//    @Test
//    void testDeclareWinner1() throws IllegalArgumentException, IOException {
//        // Create mock players
//        Player player1 = new Player("Player 1", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 900);
//        Player player2 = new Player("Player 2", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 700);
//        Player player3 = new Player("Player 3", new Piece(null), new Die(), new Board(12, "squaresConfig.csv"), 300);
//        List<Player> players = new ArrayList<>();
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//
//        // Redirect console output to ByteArrayOutputStream
//        System.setOut(new PrintStream(outContent));
//
//        SOPGame.players = players;
//        
//        // Call declareWinner method
//        SOPGame.declareWinner();
//
//        // Get the printed output
//        String printedOutput = getConsoleOutput();
//
//        // Assert that the output contains expected messages
//        assertTrue(printedOutput.contains("1st Place: Player 1 with 900.00 Knowledge Points"));
//        assertTrue(printedOutput.contains("2nd Place: Player 2 with 700.00 Knowledge Points"));
//        assertTrue(printedOutput.contains("3rd Place: Player 3 with 300.00 Knowledge Points"));
//    }


// Couple of helper functions for the above tests
    private String getConsoleOutput() {
        return outContent.toString().trim();
    }

    private void provideMockUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}