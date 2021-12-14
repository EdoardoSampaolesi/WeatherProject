package it.project.weather.interfaces;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.model.City;

public interface WeatherService 
{
    public JSONObject oneCallAPI(City city,Vector<String> exclude);
    //private String createOneCallAPILink(Coord coords,Vector<String> exclusions);
    public JSONObject geocodingAPI(String name);
    //private String createGeocodingAPILink(String cityName);
    public JSONObject historicalWeatherAPI(City city);
    //private String createHistoricalWeatherAPILink(Coord coords);
}
