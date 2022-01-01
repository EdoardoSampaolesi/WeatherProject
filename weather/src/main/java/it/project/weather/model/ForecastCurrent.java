package it.project.weather.model;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import it.project.weather.interfaces.WeatherService;

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
    	    JSONObject obj= (JSONObject) wService.oneCallAPI(city.getCoord(), exclude).get("current");  
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
    	for(Weather w : weatherList) 
    	{
    		arrayobj.add(w.toJSON());
    	}
    	JSONObject obj= new JSONObject();
        obj.put("Current weather", arrayobj);
        obj.put("City", city.toJSON());
        return obj;
    }
}