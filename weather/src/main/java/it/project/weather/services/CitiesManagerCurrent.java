package it.project.weather.services;

import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.ForecastCurrent;

public class CitiesManagerCurrent extends CitiesManagerImpl 
{
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastCurrent(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
