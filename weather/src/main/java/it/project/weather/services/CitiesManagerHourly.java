package it.project.weather.services;

import it.project.weather.model.City;
import it.project.weather.model.ForecastHourly;
import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;

public class CitiesManagerHourly extends CitiesManagerImpl
{
    @Override
    protected String getJSONString(City city) 
    {
        Forecast forecast = new ForecastHourly(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON().toJSONString();
    }
}
