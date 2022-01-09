package it.project.weather.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import it.project.weather.exeptions.DateOutOfRangeException;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.services.WeatherServiceImpl;

public class DatesManagerTest 
{
    private WeatherService service;
    private City city;
    private Calendar startDate,endDate;


    /**
     * setUp method, used to set variables used during the test
     */
    @Before
    public void setUp()
    {
        service = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");
        city = new City("Chicago");
        try 
        {
            city.createFromJSON(service);
        } 
        catch (Exception e) {}
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        startDate.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        try 
        {
            startDate = city.fromCityOffsetToMyDate(startDate);
        } 
        catch (java.text.ParseException e) 
        {
            e.printStackTrace();
        }
        endDate.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        try 
        {
            endDate = city.fromCityOffsetToMyDate(endDate);
        } 
        catch (java.text.ParseException e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * test method used to verify that list of hourly weather conditions used for statistics isn't null
     */
    @Test
    public void testNullDateReturn()
    {
        try
        {
            assertNotNull(DatesManager.getHourlyWeatherFilteredByStartAndEndDates(service, city, startDate, endDate));
        } 
        catch (IOException | ParseException | DateOutOfRangeException e) {}
    }
}
