package it.project.weather.utils;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.model.Weather;

public abstract class Forecast implements WeatherModelEntity 
{
    private Vector<Weather> weatherList = new Vector<Weather>();
    private City city = null;

    public Forecast(City city)
    {
        this.city = city;
    }

    @Override
    public abstract void createFromJSON(WeatherService wService);

    @Override
    public abstract JSONObject toJSON();
    
}
