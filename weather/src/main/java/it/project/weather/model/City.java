package it.project.weather.model;

import org.json.simple.JSONObject;


import it.project.weather.exeptions.CityNotFoundException;

import it.project.weather.interfaces.CityInterface;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import java.util.Date;


public class City implements WeatherModelEntity
{
	private String name;
	private CoordImpl coord;
	private int offset;
	
	public City(String name)
    {
        this.name=name;
    }

    @Override
    public void createFromJSON(WeatherService wService) throws CityNotFoundException, Exception
    {
    	try 
    	{
    		wService.geocodingAPI(name);
    		JSONObject obj= wService.geocodingAPI(name);
    		double lat=(double) obj.get("lat");
    		double lon=(double) obj.get("lon");
    		this.coord = new CoordImpl(lat, lon); 
    		offset=(int) obj.get("timezone_offeset");
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
    
    public int getOffset() 
    {
		return offset;
	}
    public void setOffset(int offset) 
    {
		this.offset = offset;
	}
   
    //metodo toString?
}
