   package it.project.weather.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.Forecast;

public class ForecastMorDayEveNight extends Forecast
{
	private Vector<String> exclude;
	
    public ForecastMorDayEveNight(City city) 
    {
        super(city);
        exclude.add("current");
        exclude.add("daily");
        exclude.add("minutely");
        exclude.add("alerts");
    }
    
   
    
    // da 00:00 a 6:00 è notte
    // da 6:01 a 12:00 è mattina
    // da 12:01 a 18:00 è pomeriggio
    // da 18:01 a 23:59 è sera

    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Date now =new Date();
    	Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	Weather weather;
 	    Vector<Weather> wlist=new Vector<Weather>();
 	    JSONArray obj = null;
 	    startDate.setTime(now);
 	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        parser.setTimeZone(city.getOffset());
        //parser.setTimeZone(TimeZone.getTimeZone("GMT"));
        try 
        {
        	startDate.setTime(sdf.parse( parser.format(startDate.getTime()).toString() )); //parser trasforma la data di chicago in una stringa, poi sdf lo trasforma in una data
            startDate.set(Calendar.HOUR_OF_DAY, 0);
     	    startDate.set(Calendar.MINUTE, 0);
     	    startDate.set(Calendar.SECOND, 0);
     	    endDate=(Calendar) startDate.clone();
     	    endDate.set(Calendar.HOUR_OF_DAY, 23);
    	    endDate.set(Calendar.MINUTE, 59);
    	    endDate.set(Calendar.SECOND, 59);
    	    
            //metodo che ritorna un jsonarray delle informazioni di edo
     	          
    	    JSONObject o;
    	    weather = new Weather();
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
    	    	for(int i=0; i<6;i++)
         	    {   	
         	    	o = (JSONObject) obj.get(i); 	    	
         	    	/*
         	    	String main=(String) o1.get("main");
    	 		    weather.setMainweather(main); 	
    	 		    */		    
    	 		    double humidity=(double) o.get("humidity");	 	 	 
    	 	 	    average_humidity+=humidity;	 	 	    
    	 	 	    double cloudiness=(double) o.get("clouds");	 	 	    
    	 	 	    average_clouds+=cloudiness;	 	 	    
    	 	 	    double wind_speed=(double) o.get("wind_speed");	 	 	    
    	 	 	    average_windS+=wind_speed;	 	 	    
    	 	 	    short wind_deg=(short) o.get("wind_deg");	 	 	    
    	 	 	    average_windD+=wind_deg;	 	 	    
    	 	 	    //String wind_type=(String) o.get("wind_deg");
    	 	 	    //weather.setWind_deg(wind_deg);	 	 	    
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
         	    //weather.setMainweather(main);
         	    average_humidity/=6;
         	    average_clouds/=6;
         	    average_windS/=6;
         	    average_windD/=6;
         	    average_rain/=6;
         	    average_snow/=6;
         	    average_vis/=6;
         	    average_Tcurrent/=6;
         	    average_Tfeels/=6;
         	    average_pop/=6;
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
		}

    }

    @Override
    public JSONObject toJSON() 
    {
        return null;
    }
    
}
