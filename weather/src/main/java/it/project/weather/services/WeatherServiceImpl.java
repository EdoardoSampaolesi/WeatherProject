package it.project.weather.services;

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.interfaces.Coord;
import it.project.weather.interfaces.WeatherService;

public class WeatherServiceImpl implements WeatherService
{
    private String apikey = null;
    private final String oneCallAPILink = "http://api.openweathermap.org/geo/1.0/direct?";
    private final String GeocodingAPILink = "http://api.openweathermap.org/geo/1.0/direct?";
    private final String HistoricalWeatherAPILink = "https://api.openweathermap.org/data/2.5/onecall/timemachine?";

    public WeatherServiceImpl(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public JSONObject OneCallAPI(City city) {
        String link = CreateOneCallAPILink(city.getName());
        //to do
        return null;
    }

    private String CreateOneCallAPILink(String cityName)
    {
        String link = oneCallAPILink;
        link += "q="+cityName;
        link += "&appid=" + apikey;
        return link;
    }

    @Override
    public JSONObject GeocodingAPI(String name) {
        String link = CreateGeocodingAPILink(name);
        //to do
        return null;
    }

    private String CreateGeocodingAPILink(String cityName)
    {
        String link = GeocodingAPILink;
        link += "q="+cityName;
        link += "&appid=" + apikey;
        return link;
    }

    @Override
    public JSONObject HistoricalWeatherAPI(City city) {
        String link = CreateHistoricalWeatherAPILink(city.getCoords());
        //to do
        return null;
    }

    private String CreateHistoricalWeatherAPILink(Coord coords)
    {
        String link = HistoricalWeatherAPILink;
        link += coords.toString();
        link += "&dt" + null;//date needed
        link += "&appid=" + apikey;
        return link;
    }
    
    
}
