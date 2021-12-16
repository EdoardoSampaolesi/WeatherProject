package it.project.weather.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.model.City;

public interface WeatherService 
{
    public JSONObject oneCallAPI(City city,Vector<String> exclude) throws IOException, ParseException;
    //private String createOneCallAPILink(Coord coords,Vector<String> exclusions);
    public JSONObject geocodingAPI(String name) throws CityNotFoundException, IOException, ParseException;
    //private String createGeocodingAPILink(String cityName);
    public JSONObject historicalWeatherAPI(City city, Date start, int count) throws IOException, ParseException;
    //private String createHistoricalWeatherAPILink(Coord coords);
}
