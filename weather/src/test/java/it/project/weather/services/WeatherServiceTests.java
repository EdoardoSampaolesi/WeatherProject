package it.project.weather.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.Coord;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.CoordImpl;

public class WeatherServiceTests 
{
    private Coord coord;
    private WeatherService service;
    private Vector<String> exclude = new Vector<String>();


    /**
     * setUp method, used to define variables needed for tests
     */
    @Before
    public void setUp()
    {
        service = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");
        coord = new CoordImpl(40.71,-74.00); //new york
        exclude.add("hourly");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }
    
    /**
     * Test method for OneCallApi, we verify that it returns something different from null if we insert correct informations
     */
    @Test
    public void testOneCallAPI()
    {
        try
        {
            assertNotNull(service.oneCallAPI(coord, exclude));
        } 
        catch (IOException | ParseException e) {}
    }

    /**
     * Test method for GeocodingAPI, we verify that it returns something different from null if we insert correct informations
     */
    @Test
    public void testGeocodingAPI()
    {
        try 
        {
            assertNotNull(service.geocodingAPI("Chicago"));
        } 
        catch (CityNotFoundException | IOException | ParseException e) {}
        
        assertThrows(CityNotFoundException.class, () -> {service.geocodingAPI("dfhasduifhsad");});
    }

    /**
     * Test method for HistoricalAPI, we verify that it returns something different from null if we insert correct informations
     */
    @Test
    public void testHistoricalAPI()
    {
        try 
        {
            assertNotNull(service.historicalWeatherAPI(coord,new Date()));
        } 
        catch (IOException | ParseException e) {}
    }
}
