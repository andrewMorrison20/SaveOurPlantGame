package saveOurPlanet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrew Morrison 40108063
 */

public class Board {
	/**
	 * various static variables to hold business rules
	 */
	public static final int MAX_SQUARES = 12;
	public static final int MIN_SQUARES = 2;
	public static final int MIN_FILENAME_LENGTH = 7;
	public static final int MAX_FILENAME_LENGTH = 30;

	/**
	 * List of squares that make up the game board
	 */
	private ArrayList<Square> squares = new ArrayList<Square>();
	/**
	 * integer number of squares that should be on the board
	 */
	private int noSquares;
	/**
	 * config file that stores the attributes for each square and is used during
	 * board creation
	 */
	private File config;

	/**
	 * constructor calls relevant setters, note order of setters, takes a number of
	 * Squares to generate and a filename of a config csv to read square attributes
	 * from
	 * 
	 * @param noSquares
	 * @param Filename
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public Board(int noSquares, String Filename) throws IllegalArgumentException, IOException {
		this.setFile(Filename);
		this.setNoSquares(noSquares);
		this.createSquares();
	}

	/**
	 * create file based on string of file name passed as arg
	 * 
	 * @param filename
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void setFile(String filename) throws IllegalArgumentException, IOException {
		// Check that file has correct suffix
		this.checkCSV(filename);
		// check if file name length satisfies business rules
		if (filename.length() < MIN_FILENAME_LENGTH || filename.length() > MAX_FILENAME_LENGTH) {
			throw new IllegalArgumentException("File name length outside allowed range");
		} else {
			// create the file based on string of filename (validation handled in reading)
			this.config = new File(filename);
		}
	}

	/**
	 * Create the game board by creating squares detailed in config csv
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void createSquares() throws IllegalArgumentException, IOException {
		// TODO Auto-generated method stub

		if (noSquares < 1 || noSquares > MAX_SQUARES) {
			throw new IllegalArgumentException("Number of board squares outside allowed range");
		} else {
			// first square index set to zero
			int index = 0;
			// read the config file and save lines as an arraylist
			List<String> allLines = readFile();
			// System.out.println(startSquare.getName());
		
			// loop through lines and create a square object for each
			for (String line : allLines) {
				// create array list of each word of the current line splitting on comma
				String[] words = line.split(",");
				// get the various attributes for the constructor to create square object (casting to correct type where neccessary)
				String name = words[0];
				SquareType type = SquareType.valueOf(words[1].toUpperCase());
				// all squares in csv relate to action squares and go and neutral will remain
				// constant even if board size changes - Could add class type in csv and have
				// all squares in it
				Square sq;
				if(type.equals(SquareType.FIELD)){
					Field field = Field.valueOf(words[2].toUpperCase());
					double rent = Double.parseDouble(words[3]);
					double price = Double.parseDouble(words[4]);
					double minDevCost = Double.parseDouble(words[5]);
					double majDevCost = Double.parseDouble(words[6]);
				sq = new ActionSquare(name, index,type, field, rent, price, minDevCost, majDevCost);}
				else {
					sq = new Square(name,index,type);
				}				// add current square to board
				squares.add(sq);
				// increment index
				index++;
				// System.out.printf("Square %s successfully created\n", sq.getName());
			}
		}
		// Ensure enough squares have been created for the current board size
		if (squares.size() != this.getNoSquares()) {
			System.out.printf("Not Enough Squares for board size %d, check config", this.getNoSquares());
		}
	}

	/**
	 * get the current target board size (number of squares) note not length of
	 * array list of squares
	 * 
	 * @return noSquares
	 */
	public int getNoSquares() {

		return this.noSquares;
	}

	/**
	*return the current file assigned to config
	*/
	public File getConfigFile() {
	
	return this.config;
	}
	/**
	*get the full list of squares on the board
	*/
	public ArrayList<Square> getBoard(){
		return this.squares;
	}
	
	/**
	 * Extract the next square from the array list of squares using the current
	 * square index and the player roll result
	 * 
	 * @return Square - the next square piece will move to
	 */
	public Square getSquare(Square currSquare, int total) {
		int oldIndex = currSquare.getIndex();
		int nextIndex =(oldIndex + total)%this.getNoSquares();
		Square nextSquare = squares.get(nextIndex);
		return nextSquare;
	}

	/**
	 * Use the index of 0 to extract the square object,and associated attributes,
	 * for the 'Go' square that players start on
	 * 
	 * @return Square
	 */
	public Square getGoSquare(){
		return this.squares.get(0);
	}
	
	/**
	*set the number of squares on the board if in legal range else throw exception
	*/
	public void setNoSquares(int noSquares)throws IllegalArgumentException{
		if(noSquares < MIN_SQUARES || noSquares > MAX_SQUARES){
			throw new IllegalArgumentException("Number of squares outside legal range");
		}else{
			this.noSquares = noSquares;
		}
	}

	/**
	 * read the file related to the config file name variable and create and array
	 * list of all lines (of square attributes)within the file
	 * 
	 * @return allLines - array list of lines in config file
	 * @throws IOException
	 */
	private List<String> readFile() throws IOException {
		//Check file with filename exists - Further validation to check if file non empty etc added later
		if (!this.config.exists()) {
			throw new FileNotFoundException("Cannot find file");
		}
		List<String> allLines = new ArrayList<String>();
		//create file reader and read contents of config file
		FileReader fr = new FileReader(this.config);
		BufferedReader br = new BufferedReader(fr);
		//remove any whitespace for each line
		String line = br.readLine().trim();
		//discard first line as header data
		line = br.readLine();
		//loop through remaining non null lines and add them to the array list
		while (line != null) {
			allLines.add(line.trim());
			line = br.readLine();
		}
		//close resources
		br.close();
		return allLines;
	}

	/**
	 * Check the file name passed is correct type, either csv or txt
	 * 
	 * @param fileName
	 * @return boolean result of check
	 * @throws IOException
	 */
	private boolean checkCSV(String fileName) throws IOException {
		//cast to uppercase 
		fileName = fileName.toUpperCase();
		//create bool to store outcome of suffix check
		boolean extension = fileName.endsWith(".CSV") || fileName.endsWith(".TXT");
		return extension;
	}

	/**
	 * Basic visual representation of the board
	 */

	// Room for improvement here
	public void displayBoard() {
		// set the length of the sides of square based on total board size
		int sideLength = this.getNoSquares() / 4;
		// get start index of top left hand corner of board as this will print first
		int topVerticeStartIndex = (int) (sideLength * 2);
		// get end index of side
		int endIndex = topVerticeStartIndex + sideLength;
		// System.out.println(topVerticeStartIndex);
		// System.out.println(endIndex);
		// loop across range of top side of square and print out square
		for (int i = topVerticeStartIndex; i <= endIndex; i++) {
			System.out.printf("|%s|\t\t", squares.get(i).getName());
		}
		// move down the board and print adjacent squares that make of the sides
		for (int i = 1; i <= sideLength - 1; i++) {
			System.out.printf("\n|%s|\t\t\t\t\t\t\t\t\t|%s|", squares.get(topVerticeStartIndex - i).getName(),
					squares.get(endIndex + i).getName());
		}
		// print the bottom side of the board
		System.out.println();
		for (int i = (topVerticeStartIndex - sideLength); i >= 0; i--) {
			System.out.printf("|%s| \t\t", squares.get(i).getName());
		}
	}
}
