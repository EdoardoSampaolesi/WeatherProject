package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;

public class Weather implements WeatherModelEntity 
{

    @Override
    public void createFromJSON(WeatherService wService) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public JSONObject toJSON() 
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
