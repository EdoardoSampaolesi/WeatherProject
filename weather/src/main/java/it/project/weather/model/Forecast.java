package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.ForecastInterface;
import it.project.weather.interfaces.WeatherService;

public abstract class Forecast implements ForecastInterface
{
    protected Vector<Weather> weatherList = new Vector<Weather>();
    protected City city = null;

    /**
	 * Constructor of forecast.
	 * 
	 * @param city City paramter, used to perform needed API and to convert dates using offset
	 */
    public Forecast(City city)
    {
        this.city = city;
    }

    @Override
    public abstract void createFromJSON(WeatherService wService) throws CityNotFoundException;

    @Override
    public abstract JSONObject toJSON();
    
}
