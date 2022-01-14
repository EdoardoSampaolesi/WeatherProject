package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class ForecastTest 
{
	private City city;

	/**
     * setUp method set the city name used during the forecast test
     */
    @Before
    public void setUp()
    {
    	 city = new City("Chicago");     
    }

    /**
     * Test method for ForecastHourly, we verify that it returns something different from null 
     */
    @Test
    public void testHourly()
    {
    	ForecastHourly hourly = new ForecastHourly(city);
    	assertNotNull(hourly.toJSON());
    }
    
    /**
     * Test method for ForecastDaily, we verify that it returns something different from null 
     */
    @Test
    public void testDaily()
    {
    	ForecastDaily day = new ForecastDaily(city);
    	assertNotNull(day.toJSON());
    }
    
    /**
     * Test method for ForecastCurrent, we verify that it returns something different from null 
     */
    @Test
    public void testCurrent()
    {
    	ForecastCurrent current = new ForecastCurrent(city);
    	assertNotNull(current.toJSON());
    }
    
    /**
     * Test method for ForecastDaySlot, we verify that it returns something different from null 
     */
    @Test
    public void testDaySlot()
    {
    	ForecastMorDayEveNight dayslot = new ForecastMorDayEveNight(city);
    	assertNotNull(dayslot.toJSON());
    }
}
