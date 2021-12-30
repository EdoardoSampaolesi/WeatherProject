package it.project.weather.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
import org.json.simple.parser.ParseException;

import it.project.weather.exeptions.DateOutOfRangeException;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.DatesManager;
import it.project.weather.utils.Forecast;

public class ForecastMorDayEveNight extends Forecast
{
	private final int SLOT=6;
	private final String PERIODS[]= {"Night","Morning","Afternoon","Evening"};
	
    public ForecastMorDayEveNight(City city) 
    {
        super(city);  
    }   

    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Date now =new Date();
    	Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	Weather weather;
 	    JSONArray obj = null;
 	    startDate.setTime(now);
 	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        parser.setTimeZone(city.getOffset());
        //parser.setTimeZone(TimeZone.getTimeZone("GMT"));
        try 
        {
        	startDate.setTime(sdf.parse(parser.format(startDate.getTime()).toString() )); //parser trasforma la data di chicago in una stringa, poi sdf lo trasforma in una data
            startDate.set(Calendar.HOUR_OF_DAY, 0);
     	    startDate.set(Calendar.MINUTE, 0);
     	    startDate.set(Calendar.SECOND, 0);
     	    endDate=(Calendar) startDate.clone();
     	    endDate.set(Calendar.HOUR_OF_DAY, 23);
    	    endDate.set(Calendar.MINUTE, 59);
    	    endDate.set(Calendar.SECOND, 59);
    	    
    	    obj = DatesManager.getHourlyWeatherFilteredByStartAndEndDates(wService, city, startDate, endDate);
    	  
    	    JSONObject o;
    	    weather = new Weather();
    	    String main[]=new String[SLOT];
    	    double average_humidity=0;
    	    double average_clouds=0;
    	    double average_windS=0;
    	    short average_windD=0;
    	    double average_rain=0;
    	    double average_snow=0;
    	    short average_vis=0;
    	    double average_Tcurrent=0;
    	    double average_Tfeels=0;
    	    double average_pop=0;  
    	    
    	    for(int j=0;j<4;j++) 
    	    {   	    
    	    	for(int i=0; i<SLOT;i++)
         	    {   	
         	    	o = (JSONObject) obj.get(i); 	    	         	    	       	  
         	    	main[i]=(String) o.get("main");      	    	
    	 		    double humidity=(double) o.get("humidity");	 	 	 
    	 	 	    average_humidity+=humidity;	 	 	    
    	 	 	    double cloudiness=(double) o.get("clouds");	 	 	    
    	 	 	    average_clouds+=cloudiness;	 	 	    
    	 	 	    double wind_speed=(double) o.get("wind_speed");	 	 	    
    	 	 	    average_windS+=wind_speed;	 	 	    
    	 	 	    short wind_deg=(short) o.get("wind_deg");	 	 	    
    	 	 	    average_windD+=wind_deg;	 	 	      	 	 	    	 	 	    
    	 	 	    double rain=(double) o.get("rain");	 	 	    
    	 	 	    average_rain+=rain;	 	 	    
    	 	 	    double snow=(double) o.get("snow");	 	 	    
    	 	 	    average_snow+=snow;	 	 	    
    	 	 	    short vis=(short) o.get("visibility");	 	 	    
    	 	 	    average_vis+=vis;	 	 	    
    	 	 	    double temp=(double) o.get("temp");	 	 	    
    	 	 	    average_Tcurrent+=temp;	 	 	    
    	 	 	    double temp_feels=(double) o.get("feels_like");
    	 	 	    average_Tfeels+=temp_feels;	 	 	    	 	 	    
    	 	 	    double pop_rain=(double) o.get("pop");
    	 	 	    average_pop+=pop_rain;
         	    }
    	    	
    	    	Map<String, Integer> occurrences = new HashMap<String, Integer>();   	    	
     	    	for (String Word : main ) 
     	    	{
     	    	   Integer oldCount = occurrences.get(Word);
     	    	   if ( oldCount == null ) 
     	    	   {
     	    	      oldCount = 0;
     	    	   }
     	    	   occurrences.put(Word, oldCount + 1);
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
     	    		else if ( oldCount == maxCounter ) 
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
         	    weather.setWind_deg(average_vis);
         		weather.setTemp_current(average_Tcurrent);
         	    weather.setTemp_feelslike(average_Tfeels);
         	    weather.setPop_rain(average_pop);
         	    weatherList.add(weather);
     		}
        
        }
        catch (java.text.ParseException e) 
        {		
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateOutOfRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @Override
    public JSONObject toJSON() 
    {
    	JSONArray arrayobj = new JSONArray();
    	JSONObject obj= new JSONObject();    	
    	obj.put("City", city.getNamecity());  	
    	for(short i=0; i<weatherList.size() ;i++ ) 
    	{
    		JSONObject obj2= new JSONObject();
    		obj2.put(PERIODS[i], weatherList.elementAt(i).toJSON());  		
    		arrayobj.add(obj2);  		
    	}
    	obj.put("Night, morning, afternoon, evening weather", arrayobj); 
        return obj;     
    }   
}
