package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Before;
import org.junit.Test;
import it.project.weather.interfaces.Coord;

public class CoordTest {
	
    private Coord coord;
    private String s;
    
    /**
     * setUp method set the coordinates used during the test
     */
    @Before
    public void setUp()
    {    
        coord = new CoordImpl(40.71,-74.00); //new york    
    }
    
    @Test
    public void testCoordinates()
    {
    	s="lat=40.71&lon=-74.00";
    	assertEquals(s,coord.toString());
    }

}
