package saveOurPlanet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SOPGame {

	private static Scanner scanner = new Scanner(System.in);
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;
	private static final int NUM_ROUNDS = 15;
	private static final double STARTING_POINTS = 200.00;
	private static final double WINNING_POINTS = 1000.00;
	private static final double LOSING_POINTS = 0.00;
	private static List<Player> players = new ArrayList<Player>();
	
	private static Die dice = new Die();

	private static Board board = null;
	private static Square startSquare = null;

	
	public static void main(String[] args) throws Exception {
		
		try {
			board = new Board(12, "squaresConfig.csv");
			startSquare = board.getGoSquare();
			// board.displayBoard();
	
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MAIN GAME LOGIC
		System.out.println("Welcome to the EcoVenture Board Game.");

		// Start Game - get number of players and their names
		startGame(startSquare);

		// Rounds within the game - get player's choice to play (Y/N) and play turn if Y
		boolean continueGame = true;

		for (int i = 1; i <= NUM_ROUNDS && continueGame; i++) {
			System.out.println("\n\n ROUND " + i + "\n*********");
			try {
			    //  (you can adjust the delay time as needed)
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			String choice = "?";

			if (i == 1) {
				System.out.println("\nAre You Ready to Play?");
				System.out.println("Entering 'N' twice will exit the game.");
				System.out.printf("\nEnter your choice (Y/N): ");
				choice = getPlayerChoice();
			} else {
				System.out.printf("\nWould You Like to Continue Game Play to The Next Round? (Y/N): ");
				choice = getPlayerChoice();
			}

			if (choice.equalsIgnoreCase("Y")) {
				continueGame = playRound();
			} else {
				System.out.println("\nGame Over - Thanks for Playing");
				continueGame = false;
			}
			
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------");

			showGameStats();

		}

		System.out.println();
		declareWinner();

	} // End of MM


	
	/**
	 * Method to start a game of save our planet 
	 * Method calls aquirePlayers() to get numbers and names of players
	 * Method then calls displayWelcomeMessage() for these player names
	 * @param startSquare - the square which all pieces will start on
	 */
	public static void startGame(Square startSquare) {

		System.out.println("Let's get this Game Started and Save Our Planet!");

		List<String> playerNames = aquirePlayers(startSquare);
		displayWelcomeMessage(playerNames);
	}

	/**
	 * Method to aquire number of players and their names to play Save Our Planet
	 * Method calls enterNumberPlayers() which accesses the console to determine the number of players playing 
	 * Method then calls enterPlayerNames() which takes the number of players and returns
	 * an array list of player names 
	 * Method creates new Player objects based on these names and assigns each player 200 knowledge points 
	 * and a player piece (positioned on the start square)
	 * These are added to static players array list 
	 * Players are ordered alphabetically - dictates order of gameplay
	 * @return - array of playerNames as strings 
	 * @param startSquare - the square which all pieces will start on
	 */
	private static List<String> aquirePlayers(Square startSquare) {
		
		int numPlayers = 0;

		try {
			numPlayers = enterNumberPlayers();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> playerNames = enterPlayerNames(numPlayers);
		Collections.sort(playerNames);

		for (String name : playerNames) {
			Piece playerPiece = new Piece(startSquare);
			Player player = new Player(name, playerPiece, dice, board, STARTING_POINTS);
			players.add(player);
		}
		
		return playerNames;
		
	}

	/**
	 * Displays welcome message to names contained within a list
	 * @param playerNames
	 */
	private static void displayWelcomeMessage(List<String> playerNames) {
		
		System.out.printf("\nWelcome ");

		for (int i = 0; i < playerNames.size() - 2; i++) {
			System.out.printf(playerNames.get(i) + ", ");
		}
		
		int penultimateIndex = playerNames.size() - 2;
		System.out.printf(playerNames.get(penultimateIndex) + " ");

		int finalIndex = playerNames.size() - 1;
		System.out.printf("and " + playerNames.get(finalIndex));
	}

	/**
	 * Method to obtain from user the number of players for the game If the number
	 * entered is outside of MIN_PLAYERS - MAX_PLAYERS, the user is prompted to
	 * enter another number
	 * 
	 * @return - the number of players playing the game
	 */
	public static int enterNumberPlayers() throws Exception {

		System.out.printf("How many players are there? Please enter a number between %d and %d: ", MIN_PLAYERS, MAX_PLAYERS);
		
		try {

			int entered = scanner.nextInt();

			if (entered < MIN_PLAYERS || entered > MAX_PLAYERS) {
				System.out.println("\nSorry, the game can only be played by "+MIN_PLAYERS+" - "+MAX_PLAYERS+" players. Please Try Again.");
				return enterNumberPlayers();
			}
			scanner.nextLine();
			return entered;
		} catch (Exception e) {
			System.out.println("Invalid input try again.");
			if (scanner.hasNext()) {
				scanner.next();
			}
			return enterNumberPlayers();
		}
	}

	/**
	 * Method to aquire from console the names of all players. The number of names
	 * acquired determined by the number of players which is passed into the method
	 * 
	 * @param numPlayers
	 * @return - an array list containing names of players entered from console (of
	 *         sufficient length and no duplicates)
	 */
	public static List<String> enterPlayerNames(int numPlayers) {

		System.out.println("Setting up the game for " + numPlayers + " players...");
		System.out.println("\nPlease enter the name of each player, one at a time.");
		

		List<String> playerNames = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {

			int playerNumber = i + 1;
			String entered;

			try {

				do {
					System.out.printf("Enter name for Player [%d]: ",playerNumber);
					entered = scanner.nextLine().toLowerCase();
					String nameOfPlayer = Character.toUpperCase(entered.charAt(0)) + entered.substring(1);

					if (isNameValid(nameOfPlayer, playerNames)) {
						playerNames.add(nameOfPlayer);
						break;
					}
				} while (true);

			} catch (Exception e) {
				System.out.println("Invalid input try again.");
				if (scanner.hasNext()) {
					scanner.next();
				}
			}
		}
		return playerNames;
	}

	/**
	 * Method to capture the player's choice (Y/N) entered into the console
	 * If player types N, system asks them to confirm this
	 * If invalid, player is prompted to try again
	 * 
	 * @return - player choice
	 */
	public static String getPlayerChoice() {

		try {
			String choice = scanner.nextLine();

			if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
				
				if (choice.equalsIgnoreCase("N")) {
	                System.out.printf("Please confirm by typing 'N' again: ");
	                String confirmChoice = scanner.nextLine();
	                if (!confirmChoice.equalsIgnoreCase("N")) {
	                	System.out.printf("Sorry, there seems to be a bit of confusion. Please try again and enter 'Y' or 'N': ");
	                	return getPlayerChoice();
	                }
	            }
				return choice;
			} else {
				System.out.printf("\nSorry, I didn't quite catch that. Enter (Y/N): ");
				return getPlayerChoice();
			}

		} catch (Exception e) {
			System.out.println("Invalid input try again.");
			if (scanner.hasNext()) {
				scanner.next();
			}
			return getPlayerChoice();
		}
	}

	/**
	 * Method to determine if the name entered by a player is valid - ie of valid
	 * length and not taken by any other players
	 * 
	 * @param name        - name entered by player
	 * @param playerNames - array list of names already entered for gameplay
	 * @return - if true, player name is valid
	 */
	private static boolean isNameValid(String name, List<String> playerNames) {

		if (name.length() < Player.MIN_NAME || name.length() > Player.MAX_NAME) {
			System.out.println("Sorry, the names must be between " + Player.MIN_NAME + " and " + Player.MAX_NAME
					+ " characters long.");
			return false;
		} else if (playerNames.contains(name)) {
			System.out.println("Sorry, this name is already taken. Please try again.");
			return false;
		}
		return true;
	}


	/**
	 * Method to play a round of Save Our Planet virtual board game
	 * @throws Exception 
	 */
	public static Boolean playRound() throws Exception {

		Boolean gameContinues = true;

		for (Player player : players) {
			
			System.out.println("\n------------------------------------------------------------------------------");
			System.out.printf("\n\033[0;1m"+player.getName() + "\033[0m would you like to roll the dice?");
			try {
			    //  (you can adjust the delay time as needed)
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			System.out.printf("\nIf you enter 'N' twice, game play will terminate for all players.");
			System.out.println("\n"+player.playerStatsSummary());
			System.out.printf("\nYour response (Y/N): ");

			String choice = "?";
			choice = getPlayerChoice();

			if (choice.equalsIgnoreCase("Y")) {
				
				System.out.println();
				Square newSquare = player.playTurn();
				
				// Information on Square they have landed on
				if (newSquare.getType() == SquareType.NEUTRAL || newSquare.getType() == SquareType.START) {
					gameContinues = checkPoints(player);
					 if (!gameContinues) {
					    return gameContinues;
					 }

				} else {
					
					// Otherwise player has landed on an Action Square
					if (newSquare instanceof ActionSquare) {
		
					    ActionSquare newActionSquare = (ActionSquare) newSquare;
					    String ownerName = newActionSquare.getOwner();
					    
					    if (ownerName.equals("Square Currently Unowned")) {
					    	
					    	// Square is unowned - offer to buy
					    	
					    	gameContinues = checkPoints(player);
							 if (!gameContinues) {
							    return gameContinues;
							 }
				
					    	Boolean offerOutcome = offerSquare(player, newActionSquare);
					
					    	// If true the user owns the square
					    	if (offerOutcome == true) {
					    		purchaseSquare(player, newActionSquare);

					    	} else {
					    		
					    		// If false the square is offered to other players
					    		System.out.println("\n\n"+newActionSquare.getName()+" belonging to "+newActionSquare.getField()+" will now be offered to other players.");
					    		try {
					    		    //  (you can adjust the delay time as needed)
					    			TimeUnit.MILLISECONDS.sleep(300);
					    		} catch (InterruptedException e) {
					    		    // Handle interruption if needed
					    		    e.printStackTrace();
					    		}
					    		List<Player> otherPlayers = createPlayersToOffer(player, players);
					    		offerSquareOtherPlayers(otherPlayers, newActionSquare);
					    		
					    		
					    		if(newActionSquare.getOwner().equals("Square Currently Unowned")) {
					    			System.out.println("\nNo players purchased "+newActionSquare.getName()+" this round. This square remains unowned");
					    			try {
					    			    //  (you can adjust the delay time as needed)
					    				TimeUnit.MILLISECONDS.sleep(300);
					    			} catch (InterruptedException e) {
					    			    // Handle interruption if needed
					    			    e.printStackTrace();
					    			}
					    		}
					    	}
					    } else if (ownerName.equals(player.getName())) {
					    	System.out.println("You already own this square. Hang here until your next roll");
					    	try {
					    	    //  (you can adjust the delay time as needed)
					    		TimeUnit.MILLISECONDS.sleep(300);
					    	} catch (InterruptedException e) {
					    	    // Handle interruption if needed
					    	    e.printStackTrace();
					    	}
					    	gameContinues = checkPoints(player);
							 if (!gameContinues) {
							    return gameContinues;
							 }

					    } else {

							gameContinues = checkPoints(player);
					 		if (!gameContinues) {
					    		return gameContinues;
						 	}
	
					    	Player squareOwner = returnPlayer(players, ownerName);
					    	double rentToPay = newActionSquare.getRent();
					    	double payeeFunds = player.getKnowledgePoints();
					    	
					    	if (payeeFunds >= rentToPay) {
					    		System.out.printf("\nTransferring %.2f from %s to %s.\n", rentToPay, player.getName(), squareOwner.getName()); 
					    		try {
					    		    //  (you can adjust the delay time as needed)
					    			TimeUnit.MILLISECONDS.sleep(300);
					    		} catch (InterruptedException e) {
					    		    // Handle interruption if needed
					    		    e.printStackTrace();
					    		}
					    		player.decreaseKnowledgePoints(rentToPay);
						    	squareOwner.increaseKnowledgePoints(rentToPay);
						    	
						    	// Display stats of the two players in question
						    	System.out.println(player.playerStatsSummary());
						    	try {
						    	    //  (you can adjust the delay time as needed)
						    		TimeUnit.MILLISECONDS.sleep(300);
						    	} catch (InterruptedException e) {
						    	    // Handle interruption if needed
						    	    e.printStackTrace();
						    	}
						    	System.out.println(squareOwner.playerStatsSummary());
						    	try {
						    	    //  (you can adjust the delay time as needed)
						    		TimeUnit.MILLISECONDS.sleep(300);
						    	} catch (InterruptedException e) {
						    	    // Handle interruption if needed
						    	    e.printStackTrace();
						    	}
						
						    	
					    	} else {
					    		System.out.println("Rent required is "+rentToPay+", but you only have "+payeeFunds);
					    		try {
					    		    //  (you can adjust the delay time as needed)
					    			TimeUnit.MILLISECONDS.sleep(300);;
					    		} catch (InterruptedException e) {
					    		    // Handle interruption if needed
					    		    e.printStackTrace();
					    		}
					    		System.out.println("You have insufficient funds to proceed - gameplay terminated.");
					    		try {
					    		    //  (you can adjust the delay time as needed)
					    			TimeUnit.MILLISECONDS.sleep(300);
					    		} catch (InterruptedException e) {
					    		    // Handle interruption if needed
					    		    e.printStackTrace();
					    		}
					    		gameContinues = false;
					    		return gameContinues;
					    	}
					    }
					    
					} else {
					     throw new Exception("Issue with game setup. Unexpected square type encountered.");
					}
				}
				
				// ADDED
	    		gameContinues = checkPoints(player);
				 if (!gameContinues) {
				    return gameContinues;
				 }
				
				
				// Purchase developments - if the player owns all squares in the same field

				HashMap<Field, List<String>> fieldOwners = createFieldMap(board);
				checkFieldStats(fieldOwners, board, player);
				gameContinues = true;
				
			} else {
				// Player has chosen not to play - game ends
				System.out.println(player.getName()+" has chosen not to play and therefore gameplay is terminated.");
				gameContinues = false;
				return gameContinues;
			}
		}
		try {
		    //  (you can adjust the delay time as needed)
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e) {
		    // Handle interruption if needed
		    e.printStackTrace();
		}

		return gameContinues;
	}


	/**
	 * Method to offer square to other players, when the player whose turn it is has declined to purchase
	 * @param otherPlayers
	 * @param newActionSquare
	 */
	private static void offerSquareOtherPlayers(List<Player> otherPlayers, ActionSquare newActionSquare) {
		
		for (int i = 0; i < otherPlayers.size(); i++) {
			
			Player toOffer = otherPlayers.get(i);
			
			if (toOffer.getKnowledgePoints() > newActionSquare.getBuyPrice()) {
				System.out.println("\n\033[0;1m"+toOffer.getName()+"\033[0m would you like to purchase this square?");
				try {
				    //  (you can adjust the delay time as needed)
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				Boolean offerOutcome = offerSquare(toOffer, newActionSquare);
				
				if (offerOutcome == true) {
		    		purchaseSquare(toOffer, newActionSquare);
		    		break;
		    	}
				
			} else {
				System.out.println("\nSorry "+toOffer.getName()+"! You also have insufficient funds to buy this square.");
				try {
				    //  (you can adjust the delay time as needed)
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
			}

		}
	
	}

	/**
	 * Method to remove a player from an array list of players
	 * Used to offer other players a square following decline of purhcase from other player(s)
	 * @param playerToRemoveFromOffer
	 * @param playersInGame
	 * @return
	 */
	private static List<Player> createPlayersToOffer(Player playerToRemoveFromOffer, List<Player> playersInGame) {
		
		List<Player> playersToOffer = new ArrayList<>();
		for (Player gamePlayer : playersInGame) {
			playersToOffer.add(gamePlayer);
		}
		playersToOffer.remove(playerToRemoveFromOffer);
		Collections.shuffle(playersToOffer);
		return playersToOffer;
	}

	/**
	 * Prints to console the fields and all associated owners as stored in the HashMap
	 * @param fieldOwners
	 */
	public static void printFieldMap(HashMap<Field, List<String>> fieldOwners) {
		for (Map.Entry<Field, List<String>> entry : fieldOwners.entrySet()) {
			Field field = entry.getKey();
			List<String> owners = entry.getValue();
			System.out.println();
			System.out.println("\t\tField: " + field.toString());
			System.out.println("\t\tOwners: ");
			try {
			    //  (you can adjust the delay time as needed)
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			for (String owner : owners) {
				System.out.println("\t\t- " + owner);
				try {
				    //  (you can adjust the delay time as needed)
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Generates a hash map that maps each Field to the owners (String - name) of the squares belonging to that field.
	 * @param board
	 * @return
	 */
	public static HashMap<Field, List<String>> createFieldMap(Board board) {
	
		HashMap<Field, List<String>> fieldOwners = new HashMap<Field, List<String>>();
	
		for (Square sq : board.getBoard()) {
			// Check if square type is FIELD, if not continue to next square
			if (!sq.getType().equals(SquareType.FIELD)) {
				continue;
			} else {
				// Cast to child class to access object attributes and methods
				if (sq instanceof ActionSquare) {
					ActionSquare actionSq = (ActionSquare) sq;
					if (fieldOwners.containsKey(actionSq.getField())) {
						List<String> updateArray = fieldOwners.get(actionSq.getField());
						updateArray.add(actionSq.getOwner());
						fieldOwners.put(actionSq.getField(), updateArray);
					} else {
						List<String> owners = new ArrayList<String>();
						owners.add(actionSq.getOwner());
						fieldOwners.put(actionSq.getField(), owners);
					}
				}
			}
		}
		return fieldOwners;
	}

	/**
	 * Method checks the owner of each field 
	 * If all squares in one field is owned by one player, player will be offered to develop property
	 * @param ownerMap
	 * @param board
	 * @param currPlayer
	 */
	public static void checkFieldStats(HashMap<Field, List<String>> ownerMap, Board board, Player currPlayer) {
		for (Field key : ownerMap.keySet()) {
			List<String> ownersList = ownerMap.get(key);
			// System.out.println("in the loop");
			int noDistinctOwners = (int) ownersList.stream().distinct().count();
			// System.out.println(noDistinctOwners);
			// System.out.println(currPlayer.getName());
			if (ownersList.get(0).equals(currPlayer.getName()) && noDistinctOwners == 1) {
	
				boolean valid = false;
				do {
					System.out.printf("\n%s, would you like to develop your owned areas in the field : %s? (Y/N)) ", currPlayer.getName(), key);
					try {
					    //  (you can adjust the delay time as needed)
					    TimeUnit.MILLISECONDS.sleep(300);
					} catch (InterruptedException e) {
					    // Handle interruption if needed
					    e.printStackTrace();
					}
					String input = getUserInput().toUpperCase();
					switch (input) {
					case "Y":
						offerDevelopment(key, board, currPlayer);
						valid = true;
						break;
					case "N":
						System.out.println("Ok Moving on!...");
						valid = true;
						break;
					default:
						System.out.println("Didnt recognise that! lets try again..");
					}
				} while (!valid);
			}
		}
	}

	/**
	 * Method to offer development for particular square - based on number of developments currently on square
	 * @param field
	 * @param board
	 * @param currPlayer
	 */
	public static void offerDevelopment(Field field, Board board, Player currPlayer) {
		List<ActionSquare> fieldSquares = getFieldSquares(field, board, currPlayer);
		for (ActionSquare sq : fieldSquares) {
			if (sq.getNoMinorDevs() < 3 && sq.getNoMajorDevs() < 1) {
				offerMinorDevelopment(sq, currPlayer);
			} else if (sq.getNoMinorDevs() == 3 && sq.getNoMajorDevs() < 1) {
				offerMajorDevelopment(sq, currPlayer);
			} else {
				System.out.println("Maximum amount of Developments reached.");
			}
		}
	}

	/**
	 * Returns squares belonging to a particular field
	 * @param field
	 * @param board
	 * @param currPlayer
	 * @return
	 */
	public static List<ActionSquare> getFieldSquares(Field field, Board board, Player currPlayer) {
	
		List<ActionSquare> fieldSquares = new ArrayList<ActionSquare>();
		for (Square sq : board.getBoard()) {
			if (sq instanceof ActionSquare) {
				ActionSquare actionSq = (ActionSquare) sq;
				if (actionSq.getField().equals(field)) {
					fieldSquares.add(actionSq);
				}
			}
		}
		return fieldSquares;
	}

	/**
	 * Method to enable players to build minor development on square
	 * Checks that players have sufficient funds to do so
	 * @param sq
	 * @param currPlayer
	 */
	public static void offerMinorDevelopment(ActionSquare sq, Player currPlayer) {
		boolean validInput = false;

		if (currPlayer.getKnowledgePoints() >= sq.getMinorDevCost()) {
			while (!validInput) {
				System.out.printf("\nWould you like to purchase a minor development for %s?", sq.getName());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				System.out.printf("\nThis will cost %.2f knowledge points. You currently have %.2f knowledge points.", sq.getMinorDevCost(), currPlayer.getKnowledgePoints());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				System.out.printf("\nEnter 'Y' or 'N': ");
				String playerInput = getUserInput().toUpperCase();
				System.out.println(playerInput);
				switch (playerInput) {
				case ("Y"):
					currPlayer.decreaseKnowledgePoints(sq.getMinorDevCost());
					sq.updateNoMinorDevs();
					System.out.println("Successfully added Development to " + sq.getName());
					
					if (sq.getNoMinorDevs() == 1) {
						System.out.println("There is now "+sq.getNoMinorDevs()+" Minor Development on "+ sq.getName());
						try {
						    //  (you can adjust the delay time as needed)
						    TimeUnit.MILLISECONDS.sleep(300);
						} catch (InterruptedException e) {
						    // Handle interruption if needed
						    e.printStackTrace();
						}
					} else {
						System.out.println("There are now "+sq.getNoMinorDevs()+" Minor Developments on "+ sq.getName());
						try {
						    //  (you can adjust the delay time as needed)
						    TimeUnit.MILLISECONDS.sleep(300);
						} catch (InterruptedException e) {
						    // Handle interruption if needed
						    e.printStackTrace();
						}
					}
				
					validInput = true;
					break;
				case ("N"):
					System.out.println("No development carried out.");
					validInput = true;
					break;
				default:
					System.out.println("Didn't recognise input, please try again.");
				}
			}
		} else {
			System.out.println("You do not have enough knowledge points for a minor development on Square: " +  sq.getName());
		}
	}
	
	/**
	 * Method to enable players to build major development on square
	 * Checks that players have sufficient funds to do so
	 * @param sq
	 * @param currPlayer
	 */
	public static void offerMajorDevelopment(ActionSquare sq, Player currPlayer) {
		boolean validInput = false;
		// Scanner sc = new Scanner(System.in);
		if (currPlayer.getKnowledgePoints() >= sq.getMajorDevCost()) {
			while (!validInput) {
				System.out.printf("\nWould you like to purchase a Major development for %s?", sq.getName());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				System.out.printf("\nThis will cost %.2f knowledge points. You currently have %.2f knowledge points.", sq.getMajorDevCost(), currPlayer.getKnowledgePoints());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				System.out.printf("\nEnter 'Y' or 'N': ");
				String playerInput = getUserInput().toUpperCase();
				switch (playerInput) {
				case ("Y"):
					currPlayer.decreaseKnowledgePoints(sq.getMajorDevCost());
					sq.updateNoMajorDevs();
					System.out.println("Successfully added Major Development to " + sq.getName());
					validInput = true;
					break;
				case ("N"):
					System.out.println("No development carried out on " + sq.getName());
					validInput = true;
					break;
				default:
					System.out.println("Didn't recognise input, please try again.");
				}
			}
		} else {
			System.out.println("You do not have enough knowledge points for a major development on this Square " + sq.getName());
		}
	}

	public static String getUserInput() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine().trim();
	}

	/**
	 * Method to offer the user the opportunity to buy a square
	 * If user wants to purchase the square, the system checks the user has sufficient funds
	 * @param player
	 * @param newActionSquare
	 * @return owned - true = player now owns the square, false = square remains unowned
	 */
	public static boolean offerSquare(Player player, ActionSquare newActionSquare) {

		// Square is unowned - offer to buy
		Boolean owned = false;
		double userHas = player.getKnowledgePoints();
		double squareCosts = newActionSquare.getBuyPrice();

		System.out.printf(player.getName()+", you have \033[0;1m%.2f\033[0m Knowledge Points", userHas);
		
		
		if (userHas >= squareCosts) {
			
			System.out.printf(". Would you like to buy this square for \033[0;1m%.2f\033[0m Knowledge Points? (Y/N): ", squareCosts);
			try {
			    //  (you can adjust the delay time as needed)
			    TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			String choice = getPlayerChoice();

			if (choice.equalsIgnoreCase("Y") && (userHas >= squareCosts)) {
				// The user wants to purchase the square and has sufficient funds
				owned = true;
			} else if (choice.equalsIgnoreCase("Y") && (userHas < squareCosts)) {
				// The user wants to purchase the square and has insufficient funds
				System.out.println("\n\nSorry, you do not have sufficient funds to buy this square.");
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				owned = false;
			} else {
				owned = false;
			}
			
		} else {
			System.out.printf(", unfortunately you do not have sufficient funds to buy this square.");
			try {
			    //  (you can adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			owned = false;
		}
		
		return owned;
	}

	/**
	 * Method enabling transaction 
	 * Correct transfer of ownership of square
	 * Correct transfer of knowledge points (deducted from plater making purchase)
	 * @param player
	 * @param newActionSquare
	 */
	private static void purchaseSquare(Player player, ActionSquare newActionSquare) {
		
		String playerName = player.getName();
		double squareCost = newActionSquare.getBuyPrice();
		
		System.out.println("\nCongratulations "+playerName+" you now own "+newActionSquare.getName());
		try {
		    //  (you can adjust the delay time as needed)
		    TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e) {
		    // Handle interruption if needed
		    e.printStackTrace();
		}
		newActionSquare.setOwner(playerName);

		player.decreaseKnowledgePoints(squareCost);
		System.out.println(player.playerStatsSummary());
	
	}


	/**
	 * Returns the player object corresponding to the name
	 * @return
	 */
	private static Player returnPlayer(List<Player> inputlist, String name) {
	
		for (Player player : inputlist ) {
			if (player.getName().equals(name)) {
				return player;
			}
		} 
		return null;
	}

	/**
	 * To be used at the end of each round
	 */
	public static void showGameStats() {
		
		System.out.println();
		System.out.println("\n    ****************************** CURRENT PLAYER STATS ******************************");
		
		for (Player player : players) {
			System.out.println(player.playerStatsSummary());
			try {
			    //  (you can adjust the delay time as needed)
			    TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
			    // Handle interruption if needed
			    e.printStackTrace();
			}
			
		}
		
		System.out.println();
		System.out.println("\n    ****************************** CURRENT SQUARE STATS ******************************");
		
		HashMap<Field, List<String>> fieldOwners = createFieldMap(board);
		printFieldMap(fieldOwners);

	}
	
	/**
	 * Method which checks if a player has reached the knowledge points required to win the game / losing points
	 * Returns false if the player exceeds the min / maximum value 
	 * @param player
	 * @return
	 */
	private static Boolean checkPoints(Player player) {
		
		Boolean gameContinues = true;
		
		if (player.getKnowledgePoints() >= WINNING_POINTS) {
			System.out.println("Congratulations "+player.getName()+" you have reached "+WINNING_POINTS+" Knowledge Points meaning you have won the game!");
			gameContinues = false;
		} else if (player.getKnowledgePoints() <= LOSING_POINTS) {
			System.out.println("\nSorry "+player.getName()+" you have reached "+LOSING_POINTS+" Knowledge Points meaning you have lost the game :(");
			gameContinues = false;
		}

		return gameContinues;
	}


	/**
	 * Method which prints to console the winner
	 * Winner is declared as the player with the most knowledge points at time of game end
	 */
	static void declareWinner() {
		
		Collections.sort(players, new KnowledgePointsComparator());
		
		System.out.println("   ****************************** FINAL LEADERBOARD ******************************");
		for (int i=1; i <= players.size(); i++) {
			
			Player currPlayer = players.get(i-1);
			
			switch (i) {
			case 1:
				System.out.printf("\n   In 1st Place: %s with %.2f Knowledge Points", currPlayer.getName(), currPlayer.getKnowledgePoints());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				break;
			case 2:
				System.out.printf("\n   In 2nd Place: %s with %.2f Knowledge Points", currPlayer.getName(), currPlayer.getKnowledgePoints());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				break;
			case 3:
				System.out.printf("\n   In 3rd Place: %s with %.2f Knowledge Points", currPlayer.getName(), currPlayer.getKnowledgePoints());
				try {
				    //  (you can adjust the delay time as needed)
				    TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
				    // Handle interruption if needed
				    e.printStackTrace();
				}
				break;
			default:
				System.out.printf("\n   Unfortunately, %s didn't place with %.2f Knowledge Points", currPlayer.getName(), currPlayer.getKnowledgePoints());
			}
		}
		System.out.println();
	}

	
} // End of class
