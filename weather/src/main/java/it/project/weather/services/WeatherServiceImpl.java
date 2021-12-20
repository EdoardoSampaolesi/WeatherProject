package it.project.weather.services;

/**
 * @author @EdoardoSampaolesi
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.Coord;
import it.project.weather.interfaces.WeatherService;

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

    /** 
     * @param coord this parameter is the coordinates parameter, lat and lon are needed to compute the API
     * @param exclude this vector contains the strings of which parts of the API have to be ignored, key words are: Current ( for current weather),
     * Hourly ( weather hour by hour), Daily ( weather day by day ), Alerts ( national weather alerts, in our routes it isn't used )
     * @return JSONObject the API return a JSONObject which contains all weather informations required for the routes
     * @throws IOException if the class that call this method get this exception, it has to rethrow its.
     * @throws ParseException if the class that call this method get this exception, it has to rethrow its.
     */
    @Override
    public JSONObject oneCallAPI(Coord coord,Vector<String> exclude) throws IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        String url = createOneCallAPILink(coord,exclude);
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
 
    /** 
     * @param name the only input parameter is a name of city
     * @return JSONObject the API return a JSONObject which contains informations about the city ( name in different languages, latitude, longitude , country)
     * @throws CityNotFoundException if the class that call this method get this exception, it has to rethrow its. ( personalized exception )
     * @throws IOException if the class that call this method get this exception, it has to rethrow its.
     * @throws ParseException if the class that call this method get this exception, it has to rethrow its.
     */
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
 
    /** 
     * this API returns weather hour by hour only for the day in start parameter
     * @param coord this parameter is the coordinates parameter, lat and lon are needed to compute the API
     * @param start that represents the datetime where the API starts to return weather informations hour by hour and it does up to the midnight of variable's day
     * @return JSONObject the return object contains informations on the weather hour by hour 
     * @throws IOException if the class that call this method get this exception, it has to rethrow its.
     * @throws ParseException if the class that call this method get this exception, it has to rethrow its.
     */
    @Override
    public JSONObject historicalWeatherAPI(Coord coord, Date start)  throws IOException, ParseException
    {
        String url = createHistoricalWeatherAPILink(coord, start);
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
  
    /** 
     * Creates the link of OneCall Api
     */
    private String createOneCallAPILink(Coord coords,Vector<String> exclusions)
    {
        String link = oneCallAPILink;
        //link += coords.toString(); //lat={[(-)00.00]}&lon={[(-)00.00]}
        link += "lat=41.87&lon=-87.62";
        link += "&appid=" + apikey;
        if(exclusions.size() > 0)
        {
            link += "&exclude=";
            for(String s : exclusions)
            {
                link += s;
                if(!exclusions.elementAt(exclusions.size()-1).equals(s))
                    link += ",";
            }
        }
        link += "&units=imperial";
        System.out.println(link);
        return link;
    }
   
    /** 
     * Creates the link of Geocoding Api
     */
    private String createGeocodingAPILink(String cityName)
    {
        String link = GeocodingAPILink;
        link += "q="+cityName+",US"; //only US cities
        link += "&limit=1";
        link += "&appid=" + apikey;
        return link;
    }
   
    /** 
     * Creates the link of HsitoricalWeather Api
     */
    private String createHistoricalWeatherAPILink(Coord coords, Date start)
    {
        String link = HistoricalWeatherAPILink;
        link += coords.toString();
        link += "&start" + start.toString(); // maybe conversion needed
        link += "&appid=" + apikey;
        link += "&units=imperial";
        return link;
    }   
}
