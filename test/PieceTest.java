package saveOurPlanet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceTest {

	String name;
	int index;
	SquareType type;
	Piece p;
	Square s;

	@BeforeEach
	void setUp() throws Exception {
		name = "Solar Power";
		index = 1;
		type = SquareType.FIELD;
		s = new Square(name, index, type);
		p = new Piece(s);
	}

	@Test
	void testPiece() {

		assertNotNull(p);

		assertEquals(s, p.getPosition());
	}

	@Test
	void testGetSetPosition() {
		Square s2 = new Square("Wind Power", 2, SquareType.FIELD);
		p.setPosition(s2);
		assertEquals(s2, p.getPosition(), "getPosition() should return the set Square object");
	}

}
