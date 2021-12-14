package it.project.weather.services;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.Coord;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;

public class WeatherServiceImpl implements WeatherService
{
    private String apikey = null;
    private final String oneCallAPILink = "http://api.openweathermap.org/geo/1.0/direct?";
    private final String GeocodingAPILink = "http://api.openweathermap.org/geo/1.0/direct?";
    private final String HistoricalWeatherAPILink = "https://api.openweathermap.org/data/2.5/onecall/timemachine?";

    public WeatherServiceImpl(String apikey) 
    {
        this.apikey = apikey;
    }

    @Override
    public JSONObject OneCallAPI(City city,Vector<String> exclude) 
    {
        String url = CreateOneCallAPILink(city.getName(),exclude);
        return null;
    }

    private String CreateOneCallAPILink(String cityName,Vector<String> exclusions)
    {
        String link = oneCallAPILink;
        link += "q="+cityName;
        link += "&units=imperial";
        if(exclusions.size() > 0)
        {
            link += "&exclude=";
            boolean check = false;
            do{
                link += exclusions.iterator().next();
                if(check = exclusions.iterator().hasNext())
                    link += ",";
            }while(check);
        }
        link += "&appid=" + apikey;
        return link;
    }

    @Override
    public JSONObject GeocodingAPI(String name) 
    {
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
    public JSONObject HistoricalWeatherAPI(City city) 
    {
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
