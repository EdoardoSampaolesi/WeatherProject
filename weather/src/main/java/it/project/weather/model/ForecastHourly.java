package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.utils.Forecast;

public class ForecastHourly extends Forecast
{
    public ForecastHourly(City city) 
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
