package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.services.WeatherServiceImpl;

public class ForecastTest 
{
	private City city;
    private WeatherService wService;

	/**
     * setUp method set the city name used during the forecast test
     */
    @Before
    public void setUp()
    {
        wService = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");
    	 city = new City("Chicago");     
         try {
            city.createFromJSON(wService);
        } catch (Exception e) {}
    }

    /**
     * Test method for ForecastHourly, we verify that it returns something different from null 
     */
    @Test
    public void testHourly()
    {
    	ForecastHourly hourly = new ForecastHourly(city);
        hourly.createFromJSON(wService);
    	assertNotNull(hourly.toJSON());
    }
    
    /**
     * Test method for ForecastDaily, we verify that it returns something different from null 
     */
    @Test
    public void testDaily()
    {
    	ForecastDaily day = new ForecastDaily(city);
        day.createFromJSON(wService);
    	assertNotNull(day.toJSON());
    }
    
    /**
     * Test method for ForecastCurrent, we verify that it returns something different from null 
     */
    @Test
    public void testCurrent()
    {
    	ForecastCurrent current = new ForecastCurrent(city);
        current.createFromJSON(wService);
    	assertNotNull(current.toJSON());
    }
    
    /**
     * Test method for ForecastDaySlot, we verify that it returns something different from null 
     */
    @Test
    public void testDaySlot()
    {
    	ForecastMorDayEveNight dayslot = new ForecastMorDayEveNight(city);
        dayslot.createFromJSON(wService);
    	assertNotNull(dayslot.toJSON());
    }
}
