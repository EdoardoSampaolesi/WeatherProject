package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;

public class City implements WeatherModelEntity
{
    public City(String name)
    {
        
    }

    @Override
    public void createFromJSON(JSONObject jObject)
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
