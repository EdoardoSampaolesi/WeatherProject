package it.project.weather.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.exeptions.CityNotFoundException;

public interface WeatherService 
{
    public JSONObject oneCallAPI(Coord coord,Vector<String> exclude) throws IOException, ParseException;
    //private String createOneCallAPILink(Coord coords,Vector<String> exclusions);
    public JSONObject geocodingAPI(String name) throws CityNotFoundException, IOException, ParseException;
    //private String createGeocodingAPILink(String cityName);
    public JSONObject historicalWeatherAPI(Coord coord, Date start) throws IOException, ParseException;
    //private String createHistoricalWeatherAPILink(Coord coords);
}
