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
    
   
    
    // da 00:00 a 5:00 è notte
    // da 5:01 a 12:00 è mattina
    // da 12:01 a 17:00 è pomeriggio
    // da 17:01 a 21:00 è sera
    // da 21:01 a 23:59 è notte

    @Override
    public void createFromJSON(WeatherService wService) 
    {
    	Date now =new Date();
    	Calendar startDate = Calendar.getInstance();
    	Calendar endDate = Calendar.getInstance();
    	Weather weather;
 	    Vector<Weather> wlist=new Vector<Weather>();
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
    	    
            //metodo che ritiorna un jsonarray di info
     	    
    	    
        	//faccio una media 
     	    for(int i=0; i<5;i++) 
     	    {
     	    	
     	    }
        	//salvo la media in weatherList
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
