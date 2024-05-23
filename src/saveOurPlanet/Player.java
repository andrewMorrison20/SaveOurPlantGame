/**
 * 
 */
package saveOurPlanet;

import java.util.concurrent.TimeUnit;


public class Player {


	// Business Constants

    public static final int MIN_NAME = 2;
    public static final int MAX_NAME = 40;
    private static final double MIN_POINTS_VALUE = 0.0;
    private static final double GO_AMOUNT = 200.00;

//instance variables
    
    private String name;
    private Piece piece;
    private Die dice; 
    private Board board;
    private double knowledgePoints;
    
//constructor
    
    /**
     * Constructor for the player object
     * @param name
     * @param piece
     * @param dice
     * @param board
     * @param knowledgePoints
     */
    public Player(String name, Piece piece, Die dice, Board board, double knowledgePoints) {
        this.setName(name);
        this.piece = piece;
        this.setDice(dice);
        this.setBoard(board);
        this.knowledgePoints = knowledgePoints;
    }
    
//methods
    
    /**
     * Method to move a players piece during their turn 
     * 
     * Dice value calculated and set in die class, then stored in total in this method
     * checks current position of the piece
     * next square is calculated by adding total to the current square index
     * Players pieces is the moved by the number of squares stored in total
     * If the player passes "Go" they are awarded 200 knowledge points
     */
    public Square playTurn() {
        System.out.println(this.getName() + "`s turn");
        System.out.println("Rolling dice...");
        dice.roll();
        int total = dice.getRollValue();
        System.out.printf("Moving Piece %d places...\n", total);
        
        Square currSquare = this.piece.getPosition();
        Square nextSquare = this.board.getSquare(currSquare, total);
        
        if (currSquare.getIndex() >= nextSquare.getIndex() || total > board.getNoSquares()){
            this.increaseKnowledgePoints(GO_AMOUNT);
            System.out.printf("You passed Go and collected %.2f knowledge points\n", GO_AMOUNT);
        }
        
        // Visual representation of movement with dots
        System.out.print("Moving: ");
        for (int i = 0; i < total; i++) {
            System.out.print(". ");
            try {
                // Add a short delay between each dot (adjust sleep time as needed)
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(); // Move to the next line after the dots
        
        this.piece.setPosition(nextSquare);
        
        if (nextSquare instanceof ActionSquare) {
            ActionSquare nextActionSquare = (ActionSquare) nextSquare;
            System.out.println(nextActionSquare.getAll());
        } else {
            System.out.println(nextSquare.getAll());
        }
        
        return nextSquare;
    }
          
    /**
     * Method to get the current knowledge points of the player
     * 
     * @return this.knowledgePoints
     */
    public double getKnowledgePoints() {
        return this.knowledgePoints;
    }
     
    /**
     * Method to increase the players knowledge points
     * 
     * @param knowledgePoints
     */
    public void increaseKnowledgePoints(Double knowledgePoints) {
        if (knowledgePoints == null) {
            throw new IllegalArgumentException("Knowledge Points cannot be null");
        }
        
        if (knowledgePoints < MIN_POINTS_VALUE) {
            throw new IllegalArgumentException("Knowledge Points are below the minimum amount to play, must be at least 0, currently:" + MIN_POINTS_VALUE);
        } else {
            this.knowledgePoints += knowledgePoints;
        }
    }
    
    /**
     * Method to decrease the players knowledge points
     * 
     * @param knowledgePoints
     */
    public void decreaseKnowledgePoints(Double knowledgePoints) { 
        if (knowledgePoints == null) {
            throw new IllegalArgumentException("Knowledge Points cannot be null");
        }
        
        if (knowledgePoints < MIN_POINTS_VALUE) {
            throw new IllegalArgumentException("Knowledge Points are below the minimum amount to play, must be at least 0, currently:" + MIN_POINTS_VALUE);
        } else {
            this.knowledgePoints -= knowledgePoints;
        }
    }
    
//getters & setters
    
    /**
     * Method to get the players name
     * 
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

	/**
	 * Method to set the players name
	 * checks length of name to ensure it is between 2-40 characters
	 * throws exception if not
	 * 
	 * @return this.name
	 */
    public void setName(String name) {
        if(name.length()>=MIN_NAME && name.length()<=MAX_NAME) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Not a valid name - valid = 2-40 characters.\n "
                                                +"This can be your initials i.e. NN or your full name");
        }
    }
    
	/**
	 * Method to set the players board
	 * 
	 * will throw exception if board is null
	 * 
	 * @return this.board
	 */
    public void setBoard(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        } else {
            this.board = board;
        } 
    }
    
	/**
	 * Method to set the players dice
	 * 
	 * @return this.dice
	 */
    public void setDice(Die dice) { 
        if (dice == null) {
            throw new IllegalArgumentException("Dice cannot be null");
        } else {
        	this.dice = dice;
        }
    }

//Print methods 
    
    /**
	 * Formats the price to two decimal places
	 * @return
	 */
	public String getFormatPrice (double price) {
		String formattedPrice = String.format("%.2f", price);
		return formattedPrice;
	}
    
       /**
     * Prints nice message of player name and knowledge points
     * Decoration above and below the message to delineate section
     * 
     * @return String
     */
    public String playerStatsSummary() {
    	
    	StringBuilder ownership = new StringBuilder();
		ownership.append("");
    	for (Square sq : board.getBoard()) {
			if (!sq.getType().equals(SquareType.FIELD)) {
				continue;
			} else {
				if (sq instanceof ActionSquare) {
					ActionSquare actionSq = (ActionSquare) sq;
					if (actionSq.getOwner().equals(this.getName())) {
						ownership.append("\n\t    > "+actionSq.getName()+" with "+actionSq.getNoMinorDevs()+" min & "+actionSq.getNoMajorDevs()+" maj - "+actionSq.getField()+".");
						
					}
				}
			}
		}
    	
    	String youOwn = ownership.toString();
    	
    	String kpFromat = getFormatPrice(this.getKnowledgePoints());
    	
        String newLine = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append(newLine);
        sb.append("\t**===================================================================**");
        sb.append(newLine);
        sb.append("\t  Player : ");
        sb.append(this.getName());
        sb.append(" - Knowledge Points : ");
        sb.append(kpFromat);
        sb.append(youOwn);
        sb.append(newLine);
        sb.append("\t**===================================================================**");
        return sb.toString();	

    }

}