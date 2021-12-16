package it.project.weather.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ch.qos.logback.core.joran.conditional.ElseAction;
import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.Coord;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;

public class WeatherServiceImpl implements WeatherService
{
    private String apikey = null;
    private final String oneCallAPILink = "https://api.openweathermap.org/data/2.5/onecall?";
    private final String GeocodingAPILink = "http://api.openweathermap.org/geo/1.0/direct?";
    private final String HistoricalWeatherAPILink = "https://api.openweathermap.org/data/2.5/onecall/timemachine?";

    public WeatherServiceImpl(String apikey) 
    {
        this.apikey = apikey;
    }

    @Override
    public JSONObject oneCallAPI(City city,Vector<String> exclude) throws IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        String url = createOneCallAPILink(city.getCoords(),exclude);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new URL(url)
                    .openConnection()
                    .getInputStream()
            )
        );
        String inputLine,finalString = "";
        while ((inputLine = reader.readLine()) != null)            
            finalString += inputLine;
        return JSONObject.class.cast(parser.parse(finalString));
    }

    @Override
    public JSONObject geocodingAPI(String name) throws CityNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        String url = createGeocodingAPILink(name);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new URL(url)
                    .openConnection()
                    .getInputStream()
            )
        );
        String inputLine,finalString = "";
        while ((inputLine = reader.readLine()) != null)           
            finalString += inputLine;
        if(finalString.equals("[]"))
            throw new CityNotFoundException(name);
        JSONObject jObject = JSONObject.class.cast(parser.parse(finalString));
        return jObject;
    }

    @Override
    public JSONObject historicalWeatherAPI(City city, Date start, int count)  throws IOException, ParseException
    {
        String url = createHistoricalWeatherAPILink(city.getName());
        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new URL(url)
                    .openConnection()
                    .getInputStream()
            )
        );
        String inputLine,finalString = "";
        while ((inputLine = reader.readLine()) != null)            
            finalString += inputLine;
        return JSONObject.class.cast(parser.parse(finalString));
    }

    private String createOneCallAPILink(Coord coords,Vector<String> exclusions)
    {
        String link = oneCallAPILink;
        link += coords.toString(); //lat={[(-)00.00]}&lon={[(-)00.00]}
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

    private String createGeocodingAPILink(String cityName)
    {
        String link = GeocodingAPILink;
        link += "q="+cityName+",US"; //only US cities
        link += "&limit=1";
        link += "&appid=" + apikey;
        return link;
    }

    private String createHistoricalWeatherAPILink(String cityName, Date start, int count)
    {
        String link = HistoricalWeatherAPILink;
        link += "q="+cityName+",US"; //only US cities
        link += "&type=hour";
        link += "&start" + start; // maybe conversion needed
        link += "&cnt" + count;
        link += "&appid=" + apikey;
        return link;
    }
    
    
}
