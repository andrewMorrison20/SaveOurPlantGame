package saveOurPlanet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DieTest {

	IllegalArgumentException exp;
	
	Die d;
	int maxValue, minValue, die1, die2;
	
	@BeforeEach
	void setUp() throws Exception {
		d = new Die();
		maxValue = 6;
		minValue = 1;
	}


	@Test
	void testGetDieValues() {
        d.roll();
        assertNotNull(d);
        assertTrue("Die 1 value should be between 1 and 6", d.getDie1() >= minValue && d.getDie1() <= maxValue);
        assertTrue("Die 2 value should be between 1 and 6", d.getDie2() >= minValue && d.getDie2() <= maxValue);
	}

	@Test
	void testGetRollValue() {
        d.roll();
        assertNotNull(d);
        assertEquals("The sum of die values should be equal to roll value", d.getRollValue(), d.getDie1() + d.getDie2());
	}

	@Test
	void testRoll() {
	        int rollResult = d.roll();
	        
	        assertNotNull(d.getDie1());
	        assertNotNull(d.getDie2());
	        assertNotNull(d.getRollValue());
	        
	        assertTrue("The roll result should be between 2 and 12", rollResult >= (minValue*2) && rollResult <= (maxValue*2));
	}



}


