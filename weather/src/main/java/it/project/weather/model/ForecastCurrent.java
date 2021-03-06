package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.WeatherService;

public class ForecastCurrent extends Forecast 
{
	
	private Vector<String> exclude = new Vector<String>();
	
	/**
	 * Constructor of the current forecast.
	 * 
	 * @param city City parameter, used to perform needed API and to convert dates using offset
	 */
    public ForecastCurrent(City city) 
    {
        super(city);
        exclude.add("hourly");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }

    /**
	 * This method call the oneCallAPI from WeatherService class and createFromJSON method from the Weather class to store the current weather attributes. 
	 * 
	 * @param wService WeatherService used to perform needed API
	 */
    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Weather weather;
        try 
	    {
    	    JSONObject obj= (JSONObject) wService.oneCallAPI(city.getCoord(), exclude).get("current");  
    	    weather = new Weather();
    	    weather.createFromJSON(obj, city.getOffset());
    	    weatherList.add(weather);
	   	 }
	   	 catch(Exception e) 
	   	 {
	   		
	   	 }
    }

    /**
	 * This method returns a JSON containing the city and all the current weather attributes. 
	 * 
	 * @return JSONObject
	 */
    @Override
    public JSONObject toJSON() 
    {
    	JSONArray arrayobj = new JSONArray();
    	for(Weather w : weatherList) 
    	{
    		arrayobj.add(w.toJSON());
    	}
    	JSONObject obj= new JSONObject();
    	obj.put("City", city.toJSON());
        obj.put("Current weather", arrayobj);
        return obj;
    }
}