package saveOurPlanet;

public class Piece {
	
	private Square position;

    public Piece(Square startSquare) {
        this.setPosition(startSquare);
    }

   
    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    } 
    
}


