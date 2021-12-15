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
    public JSONObject oneCallAPI(City city,Vector<String> exclude) 
    {
        JSONParser parser = new JSONParser();
        String url = createOneCallAPILink(city.getCoords(),exclude);

        try 
        {
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
        catch (IOException e) 
        {
            return JSONObject.class.cast(parser.parse(e.getMessage())); //to do with exception
        }
    }

    @Override
    public JSONObject geocodingAPI(String name) throws CityNotFoundException, IOException
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
        JSONObject jObject;
        try {
            jObject = JSONObject.class.cast(parser.parse(finalString));
            return jObject;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject historicalWeatherAPI(City city) 
    {
        String url = createHistoricalWeatherAPILink(city.getCoords());
        JSONParser parser = new JSONParser();

        try 
        {
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
        catch (IOException | ParseException e) 
        {
            return JSONObject.class.cast(parser.parse(e.getMessage())); //to do with exception
        }
    }

    private String createOneCallAPILink(Coord coords,Vector<String> exclusions)
    {
        String link = oneCallAPILink;
        link += coords.toString();
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
        link += "q="+cityName;
        link += "&limit=1";
        link += "&appid=" + apikey;
        return link;
    }

    private String createHistoricalWeatherAPILink(Coord coords)
    {
        String link = HistoricalWeatherAPILink;
        link += coords.toString();
        link += "&dt" + null;//date needed
        link += "&appid=" + apikey;
        return link;
    }
    
    
}
