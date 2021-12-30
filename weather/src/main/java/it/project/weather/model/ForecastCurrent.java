package it.project.weather.model;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.Forecast;

public class ForecastCurrent extends Forecast 
{
	
	private Vector<String> exclude = new Vector<String>();
	
    public ForecastCurrent(City city) 
    {
        super(city);
        exclude.add("hourly");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }

    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Weather weather;
        try 
	    {
    	    JSONObject obj= wService.oneCallAPI(city.getCoord(), exclude);  	
    	    weather = new Weather();
    	    weather.createFromJSON(obj, city.getOffset());
    	    weatherList.add(weather);
	   	 }
	   	 catch(Exception e) 
	   	 {
	   		
	   	 }
    }

    @Override
    public JSONObject toJSON() 
    {
    	JSONArray arrayobj = new JSONArray();
    	for(Weather w: weatherList) 
    	{
    		arrayobj.add(w.toJSON());
    	}
    	JSONObject obj= new JSONObject();
        obj.put("City", city.getNamecity());
        obj.put("Current weather", arrayobj);
        return obj;
    }
}