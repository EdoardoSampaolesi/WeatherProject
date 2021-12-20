package it.project.weather.interfaces;

import it.project.weather.exeptions.CityNotFoundException;

public interface CityInterface{

	public void createFromJSON(WeatherService wService) throws CityNotFoundException;

}
