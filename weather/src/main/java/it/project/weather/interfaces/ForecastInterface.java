package it.project.weather.interfaces;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;

public interface ForecastInterface {
	
	public void createFromJSON(WeatherService wService) throws CityNotFoundException, Exception;
    public JSONObject toJSON();

}
