package it.project.weather.model;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.ForecastInterface;
import it.project.weather.interfaces.WeatherService;

public abstract class Forecast implements ForecastInterface
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
