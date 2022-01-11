package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.io.IOException;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;

public class ForecastHourly extends Forecast
{
	private Vector<String> exclude = new Vector<String>();
	
	/**
	 * Constructor of the hourly forecast.
	 * 
	 * @param city City parameter, used to perform needed API and to convert dates using offset
	 */
    public ForecastHourly(City city) 
    {
        super(city);
        exclude.add("current");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }

    /**
	 * This method call the oneCallAPI from WeatherService class and createFromJSON method from the Weather class to store the hourly weather attributes. 
	 * 
	 * @param wService WeatherService used to perform needed API
	 */
    @Override
    public void createFromJSON(WeatherService wService)
    {
        Weather weather;
 	    JSONArray obj;
	    try 
	    {
		    obj = (JSONArray) wService.oneCallAPI(city.getCoord(), exclude).get("hourly");
	        JSONObject o;
	 	    for(int i=0; i<24;i++)
	 	    {
				o = (JSONObject) obj.get(i);
				weather = new Weather();
				/* String main=(String) ((JSONObject)((JSONArray) o.get("weather")).get(0)).get("main");	 
				weather.setMainweather(main);
				String description=(String) ((JSONObject)((JSONArray) o.get("weather")).get(0)).get("description");
				weather.setDescription(description);
				long humidity=(long) o.get("humidity");
				 weather.setHumidity(humidity);
				 long cloudiness=(long) o.get("clouds");
				 weather.setClouds(cloudiness);
				 double wind_speed=(double) o.get("wind_speed");
				 weather.setWind_speed(wind_speed);
				 long wind_deg=(long) o.get("wind_deg");
				 weather.setWind_deg(wind_deg); 
				 double rain=0;
				 if(o.get("rain")!=null) 
				 {
					 rain=(double) o.get("rain");
					 weather.setRain(rain);
				 }	 	 	    
				 double snow=0;
				 if(o.get("snow")!=null) 
				 {
					 snow=(double) o.get("snow");
					 weather.setSnow(snow);
				 }
				 long pop_rain=0;
				 if(o.get("pop")!=null) 
				 {
					 pop_rain=(long) o.get("pop");
					  weather.setPop_rain(pop_rain);
				 }
				 long vis=(long) o.get("visibility");
				 weather.setWind_deg(vis);
				 double temp=(double) o.get("temp");
				 weather.setTemp_current(temp);
				 double temp_feels=(double) o.get("feels_like");
				 weather.setTemp_feelslike(temp_feels); */
			    weather.createFromJSON(o, super.city.getOffset());
				weatherList.add(weather);
	 	   }
	   } 
	   catch (IOException | ParseException e)
	   {
			e.printStackTrace();
	   }   
    }

    /**
	 * This method returns a JSON containing the city and all the hourly weather attributes. 
	 * 
	 * @return JSONObject
	 */
    @Override
    public JSONObject toJSON() 
    {
    	JSONArray arrayobj = new JSONArray();
    	for(Weather w: weatherList) 
    	{
    		arrayobj.add(w.toJSON());
    	}
    	JSONObject obj= new JSONObject();
    	obj.put("City", city.toJSON());
        obj.put("Hourly weather", arrayobj);
        return obj;    	        
    }
    
}
