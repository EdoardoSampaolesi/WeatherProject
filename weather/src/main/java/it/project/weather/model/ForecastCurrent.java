package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.utils.Forecast;

public class ForecastCurrent extends Forecast 
{
    public ForecastCurrent(City city) 
    {
        super(city);
    }

    @Override
    public void createFromJSON(JSONObject jObject) 
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
