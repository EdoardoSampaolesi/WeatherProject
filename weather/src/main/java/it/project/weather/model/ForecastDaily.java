package it.project.weather.model;

import java.io.IOException;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.Forecast;

public class ForecastDaily extends Forecast 
{
	private Vector<String> exclude;
	
    public ForecastDaily(City city) 
    {
        super(city);
        exclude.add("current");
        exclude.add("hourly");
        exclude.add("minutely");
        exclude.add("alerts");
    }

    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	WeatherComplete weather;
 	    JSONArray obj;
	    try 
	    {
		    obj = (JSONArray) wService.oneCallAPI(city.getCoord(), exclude).get("daily");
	        JSONObject o;
	 	    for(int i=0; i<7;i++)
	 	    {
	 		    o = (JSONObject) obj.get(i);
	 		    weather = new WeatherComplete();
	 		   
	 		    String main=(String) o.get("main");
	 		    weather.setMainweather(main);
	 		    String description=(String) o.get("description");
	 		    weather.setDescription(description);
	 		    double humidity=(double) o.get("humidity");
	 	 	    weather.setHumidity(humidity);
	 	 	    double cloudiness=(double) o.get("clouds");
	 	 	    weather.setClouds(cloudiness);
	 	 	    double wind_speed=(double) o.get("wind_speed");
	 	 	    weather.setWind_speed(wind_speed);
	 	 	    short wind_deg=(short) o.get("wind_deg");
	 	 	    weather.setWind_deg(wind_deg); 	 	   
	 	 	    double rain=(double) o.get("rain");
	 	 	    weather.setRain(rain);
	 	 	    double snow=(double) o.get("snow");
	 	 	    weather.setSnow(snow);
	 	 	    double pop_rain=(double) o.get("pop");
	 	 	    weather.setPop_rain(pop_rain);	 	  	 	    
	 	 	    double temp_max=(double) ((JSONObject) o.get("temp")).get("max");
	 	 	    weather.setTemp_max(temp_max);
	 	 	    double temp_min=(double) ((JSONObject) o.get("temp")).get("min");
	 	 	    weather.setTemp_min(temp_min);
	 	 	    
	 	 	    weatherList.add(weather);
	 	   }
	   } 
	   catch (IOException | ParseException e)
	   {
			e.printStackTrace();
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
        obj.put("Daily weather", arrayobj);
        return obj;     	
    }
    
}
