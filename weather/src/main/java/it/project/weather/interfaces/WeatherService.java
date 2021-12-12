package it.project.weather.interfaces;

import org.json.simple.JSONObject;

public interface WeatherService 
{
    public JSONObject OneCallAPI(City city);
    //private String CreateOneCallAPILink(String cityName);
    public JSONObject GeocodingAPI(String name);
    //private String CreateGeocodingAPILink(String cityName);
    public JSONObject HistoricalWeatherAPI(City city);
    //private String CreateHistoricalWeatherAPILink(Coord coords);
}
