package it.project.weather.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        JSONParser parser = new JSONParser();
        String url = CreateOneCallAPILink(city.getName(),exclude);
        JSONObject jObject;

        try 
        {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new URL(url)
                        .openConnection()
                        .getInputStream()
                )
            );
            String inputLine;
            JSONArray jArray = new JSONArray();
            while ((inputLine = reader.readLine()) != null) {              
                jArray.add((JSONArray) parser.parse(inputLine));
            }
            jObject = JSONObject.class.cast(jArray);
            return jObject;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            jObject = (JSONObject) parser.parse(e.getMessage());
            return jObject;
        }
    }

    private String CreateOneCallAPILink(String cityName,Vector<String> exclusions)
    {
        String link = oneCallAPILink;
        link += "q="+cityName;
        link += "&appid=" + apikey;
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
        link += "&units=imperial";
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
