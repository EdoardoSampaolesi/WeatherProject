package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.DatesManager;

public class ForecastMorDayEveNight extends Forecast
{
	private final int SLOT=6;
	private final String PERIODS[]= {"Night","Morning","Afternoon","Evening"};
	
	/**
	 * Constructor of the night, morning, afternoon and evening forecast.
	 * 
	 * @param city City parameter, used to perform needed API and to convert dates using offset
	 */
    public ForecastMorDayEveNight(City city) 
    {
        super(city);  
    }   

    /**
	 * This method store the weather attributes in the main 4 parts of the day. 
	 * 
	 * @param wService WeatherService used to perform needed API
	 */
    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Date now =new Date();
    	Calendar startDate = Calendar.getInstance();
    	startDate.setTime(now);
    	Calendar endDate = Calendar.getInstance();
    	endDate.setTime(now);
 	    JSONArray obj = null;
 	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject o;
        try 
        {      	
        	startDate.setTime(sdf.parse(parser.format(startDate.getTime()).toString()+" 00:00:00"));  
     	    endDate.setTime(sdf.parse(parser.format(startDate.getTime()).toString()+" 23:59:59"));
     	    startDate=city.fromCityOffsetToMyDate(startDate);
     	    endDate=city.fromCityOffsetToMyDate(endDate);    	 
    	    obj = DatesManager.getHourlyWeatherFilteredByStartAndEndDates(wService, city, startDate, endDate); 
    	    for(int j=0;j<24/SLOT;j++) 
    	    {   
				Weather weather = new Weather();
				String main[]=new String[SLOT];
				long average_humidity=0;
				long average_clouds=0;
				double average_windS=0;
				long average_windD=0;
				double average_rain=0;
				double average_snow=0;
				long average_vis=0;
				double average_Tcurrent=0;
				double average_Tfeels=0;
				double average_pop=0;	    
    	    	for(int i=0; i<SLOT;i++)
         	    {   	
         	    	o = (JSONObject) obj.get(j*SLOT + i);
					JSONArray jsontemp = (JSONArray) o.get("weather");
					String main_weather = (String) ((JSONObject) jsontemp.get(0)).get("main");
					for(int k = 1; k < jsontemp.size(); k++)
						main_weather += " / " + (String) ((JSONObject) jsontemp.get(k)).get("main");    	         	    	       	  
         	    	main[i]= main_weather;    	    	
    	 	 	    average_humidity+=(long) o.get("humidity");;	 	 	    
    	 	 	    average_clouds+=(long) o.get("clouds");;	 	 	    
    	 	 	    average_windS+=Double.parseDouble(o.get("wind_speed") + "");   	 	 	
    	 	 	    average_windD+=(long) o.get("wind_deg");    
    	 	 	    if(o.get("rain")!=null) 
  	 	 	    	{
	  	 	 	    	average_rain+=Double.parseDouble(((JSONObject) o.get("rain")).get("1h") + "");
  	 	 	    	}
    	 	 	    
  	 	 	        if(o.get("snow")!=null) 
  	 	 	        {
	    	 	 	    average_snow+=Double.parseDouble(((JSONObject) o.get("snow")).get("1h") + "");
  	 	 	        }	 
  	 	 	        
  	 	 	        if(o.get("pop")!=null) 
  	 	 	        {
	    	 	 	    average_pop+=Double.parseDouble(o.get("pop") + "");
  	 	 	        }
    	 	 	    average_vis+=(long) o.get("visibility");	 	 	    
    	 	 	    average_Tcurrent+=Double.parseDouble(o.get("temp") + "");	 	 	    
    	 	 	    average_Tfeels+=Double.parseDouble(o.get("feels_like") + "");    	 	 	     	 	 	    
         	    }
    	    	
				//used to get the most repeated main word
    	    	Map<String, Integer> occurrences = new HashMap<String, Integer>();   	    	
     	    	for (String Word : main ) 
     	    	{
     	    	   Integer oldCount = occurrences.get(Word);
     	    	   if ( oldCount == null ) 
				   		occurrences.put(Word, 1);
     	    	   else
						occurrences.replace(Word, oldCount + 1);
     	    	}     	    	
     	    	Integer maxCounter=0;
     	    	String maxWord = null;
     	    	for (String Word : main ) 
     	    	{
     	    		Integer oldCount = occurrences.get(Word);
     	    		if ( oldCount > maxCounter ) 
     	    		{
       	    	      maxCounter=oldCount;
       	    	      maxWord=Word;
       	    	    }
     	    		else if ( oldCount == maxCounter && !maxWord.contains(Word) ) 
     	    		{    	    	    
       	    	      maxWord=maxWord+"\""+Word;
       	    	    }
     	    	}      	    	
     	    	average_humidity/=SLOT;
         	    average_clouds/=SLOT;
         	    average_windS/=SLOT;
         	    average_windD/=SLOT;
         	    average_rain/=SLOT;
         	    average_snow/=SLOT;
         	    average_vis/=SLOT;
         	    average_Tcurrent/=SLOT;
         	    average_Tfeels/=SLOT;
         	    average_pop/=SLOT;
         	    weather.setMainweather(maxWord);
         	    weather.setHumidity(average_humidity);
         	    weather.setClouds(average_clouds);
         	    weather.setWind_speed(average_windS);
         	    weather.setWind_deg(average_windD);
         	    weather.setRain(average_rain);
         	    weather.setSnow(average_snow);
         	    weather.setVisibility(average_vis);
         		weather.setTemp_current(average_Tcurrent);
         	    weather.setTemp_feelslike(average_Tfeels);
         	    weather.setPop_rain(average_pop);
         	    weatherList.add(weather);
     		}
        }
        catch (Exception e) {}
    }

    /**
	 * This method returns a JSON containing the weather attributes in the main 4 parts of the day. 
	 * 
	 * @return JSONObject
	 */
    @Override
    public JSONObject toJSON() 
    {
    	JSONArray arrayobj = new JSONArray();
    	JSONObject obj= new JSONObject();    	
    	obj.put("City", city.toJSON());  	
    	for(short i=0; i<weatherList.size() ;i++ ) 
    	{
    		JSONObject obj2= new JSONObject();
    		obj2.put(PERIODS[i], weatherList.elementAt(i).toJSON());  		
    		arrayobj.add(obj2);  		
    	}
    	obj.put("Dayslot weather", arrayobj); 
        return obj;     
    }   
}