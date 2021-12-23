package it.project.weather.services;

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.ForecastHourly;
import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;

public class CitiesManagerHourly extends CitiesManagerImpl
{
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastHourly(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
