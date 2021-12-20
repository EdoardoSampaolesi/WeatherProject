package it.project.weather.model;

import org.json.simple.JSONObject;


import it.project.weather.exeptions.CityNotFoundException;

import it.project.weather.interfaces.CityInterface;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import java.util.Date;
import java.util.TimeZone;


public class City implements WeatherModelEntity
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
    	try 
    	{
    		JSONObject obj= wService.geocodingAPI(name);
    		double lat=(double) obj.get("lat");
    		double lon=(double) obj.get("lon");
    		this.coord = new CoordImpl(lat, lon); 
    	}
    	catch(CityNotFoundException e) 
    	{
    		throw e;
    	}
    	catch(Exception e) 
    	{
    		throw e;
    	}
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
   
}
