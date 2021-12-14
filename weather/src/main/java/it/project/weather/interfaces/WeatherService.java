package it.project.weather.interfaces;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.model.City;

public interface WeatherService 
{
    public JSONObject OneCallAPI(City city,Vector<String> exclude);
    //private String CreateOneCallAPILink(String cityName);
    public JSONObject GeocodingAPI(String name);
    //private String CreateGeocodingAPILink(String cityName);
    public JSONObject HistoricalWeatherAPI(City city);
    //private String CreateHistoricalWeatherAPILink(Coord coords);
}
