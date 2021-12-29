package it.project.weather.interfaces;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotAddedException;
import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.NotRemovedCity;

public interface CitiesManager
{
    public JSONObject add(String[] citiesNames) throws CityNotFoundException, Exception;
    public void add(String city) throws CityNotFoundException, Exception, CityNotAddedException;
    public String getWeather(String[] cities) throws Exception;
    public JSONObject remove(String[] citiesNames);
    public void remove(String city) throws NotRemovedCity;
}