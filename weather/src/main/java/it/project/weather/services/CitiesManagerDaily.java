package it.project.weather.services;

import it.project.weather.model.City;
import it.project.weather.model.ForecastCurrent;
import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;

public class CitiesManagerDaily extends CitiesManagerImpl 
{
    @Override
    protected String getJSONString(City city) 
    {
        Forecast forecast = new ForecastCurrent(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON().toJSONString();
    }
}
