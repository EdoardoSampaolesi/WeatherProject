package it.project.weather.utils;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.model.Weather;

public abstract class Forecast implements WeatherModelEntity 
{
    protected Vector<Weather> weatherList = new Vector<Weather>();
    protected City city = null;

    public Forecast(City city)
    {
        this.city = city;
    }

    @Override
    public abstract void createFromJSON(WeatherService wService) throws CityNotFoundException;

    @Override
    public abstract JSONObject toJSON();
    
}
