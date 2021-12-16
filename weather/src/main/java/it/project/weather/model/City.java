package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import java.util.Date;


public class City implements WeatherModelEntity
{
	private String name;
	private CoordImpl coordimpl =new CoordImpl();
	private Date offset = new Date();
	
	public City(String name, CoordImpl coordimpl, Date offset)
    {
        this.name=name;
        this.coordimpl=coordimpl;
        this.offset=offset;
    }

    @Override
    public void createFromJSON(WeatherService wService)
    {
        //wService.geocodingAPI(this.name);
    }

    @Override
    public JSONObject toJSON() 
    {
    	JSONObject att = new JSONObject();
    	att.put("name", this.name);
    	att.put("latitude and longitude", this.coordimpl);
    	att.put("time zone", this.offset);
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
		return coordimpl;
	}
    public void setCoord(CoordImpl coordimpl) 
    {
		this.coordimpl = coordimpl;
	}
    
    public Date getOffset() 
    {
		return offset;
	}
    public void setOffset(Date offset) 
    {
		this.offset = offset;
	}
}
