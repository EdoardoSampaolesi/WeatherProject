package it.project.weather.services;

import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;
import it.project.weather.model.City;
import it.project.weather.model.ForecastCurrent;

public class CitiesManagerCurrent extends CitiesManagerImpl 
{
    @Override
    protected String getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastCurrent(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON().toJSONString();
    }
}
