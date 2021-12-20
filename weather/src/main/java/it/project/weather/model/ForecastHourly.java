package it.project.weather.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.Forecast;

public class ForecastHourly extends Forecast
{
	private Vector<String> exclude;
	
    public ForecastHourly(City city) 
    {
        super(city);
        exclude.add("current");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }

    @Override
    public void createFromJSON(WeatherService wService)
    {
       Weather weather;
 	   JSONArray obj;
	   try 
	   {
	    obj = (JSONArray) wService.oneCallAPI(city, exclude).get("hourly");
	    JSONObject o;
	 	for(int i=0; i<24;i++)
	 	{
	 		   o= (JSONObject) obj.get(i);
	 		   weather = new Weather();
	 	 	   double temp=(double) o.get("temp");
	 	 	   weather.setTemp_current(temp);
	 	 	  
	 	 	   double temp_feels=(double) o.get("feels_like");
		 	   weather.setTemp_current(temp_feels);
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
        // TODO Auto-generated method stub
        return null;
    }
    
}
