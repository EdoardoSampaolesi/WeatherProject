package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.CityInterface;
import it.project.weather.interfaces.WeatherService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;

public class City implements CityInterface
{
	private String name;
	private CoordImpl coord;
	private TimeZone offset;
	
	public City(String name)
    {
        this.name=name;
    }

    @Override
    public void createFromJSON(WeatherService wService) throws CityNotFoundException, Exception
    {
        Vector<String> exclude = new Vector<String>();
        exclude.add("current");
        exclude.add("minutely");
        exclude.add("hourly");
        exclude.add("daily");
        exclude.add("alerts");
		JSONObject obj= wService.geocodingAPI(name);
		double lat=(double) obj.get("lat");
		double lon=(double) obj.get("lon");
		this.coord = new CoordImpl(lat,lon);
		JSONObject o= wService.oneCallAPI(this.coord,exclude);
		TimeZone offset = TimeZone.getTimeZone((String) o.get("timezone"));
	    this.offset=offset; 
    }

    @Override
    public JSONObject toJSON() 
    {
    	JSONObject att = new JSONObject();
    	att.put("Name", this.name);
    	att.put("Latitude and longitude", this.coord);
    	att.put("Time zone", this.offset);
        return att;
    }    
    
    public String getNamecity() 
    {
		return name;
	}
    public void setNamecity(String name) 
    {
		this.name = name;
	}
    
    public CoordImpl getCoord() 
    {
		return coord;
	}
    public void setCoord(CoordImpl coord) 
    {
		this.coord = coord;
	}
    
    public TimeZone getOffset() 
    {
		return offset;
	}
    public void setOffset(TimeZone offset) 
    {
		this.offset = offset;
	}
    public Calendar fromCityOffsetToMyDate(Calendar date) throws ParseException 
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        sdf.setTimeZone(this.offset);
        //parser.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar mydate = Calendar.getInstance();
        mydate.setTime(sdf.parse( parser.format(date.getTime()).toString() ));
        return mydate;
    }
   
}
