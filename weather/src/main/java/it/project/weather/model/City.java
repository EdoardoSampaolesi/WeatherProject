package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;

public class City implements WeatherModelEntity
{
    public City(String name)
    {
        
    }

    @Override
    public void createFromJSON(WeatherService wService)
    {
        //to do
    }

    @Override
    public JSONObject toJSON() 
    {
        // to do
        return null;
    }    
}
