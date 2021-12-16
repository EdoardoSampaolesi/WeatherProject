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
        // to do
        return null;
    }    
}
