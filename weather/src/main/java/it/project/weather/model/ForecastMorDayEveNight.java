package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.utils.Forecast;

public class ForecastMorDayEveNight extends Forecast
{

    public ForecastMorDayEveNight(City city) 
    {
        super(city);
    }

    @Override
    public void createFromJSON(WeatherService wService) 
    {
        
        
    }

    @Override
    public JSONObject toJSON() 
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
