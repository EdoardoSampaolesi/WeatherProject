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

    @Before
    public void setUp()
    {
    	 city = new City("Chicago");     
    }

    @Test
    public void testHourly()
    {
    	ForecastHourly hourly = new ForecastHourly(city);
    	assertNotNull(hourly.toJSON());
    }
    
    @Test
    public void testDaily()
    {
    	ForecastDaily day = new ForecastDaily(city);
    	assertNotNull(day.toJSON());
    }
    
    @Test
    public void testCurrent()
    {
    	ForecastCurrent current = new ForecastCurrent(city);
    	assertNotNull(current.toJSON());
    }
    
    @Test
    public void testDaySlot()
    {
    	ForecastMorDayEveNight dayslot = new ForecastMorDayEveNight(city);
    	assertNotNull(dayslot.toJSON());
    }
}
