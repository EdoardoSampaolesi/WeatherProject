package it.project.weather.interfaces;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.NotRemovedCity;

public interface CitiesManager
{
    public void add(Vector<String> citiesNames) throws CityNotFoundException, Exception;
    public void add(String city) throws CityNotFoundException, Exception;
    public String getWeather(Vector<String> cities) throws Exception;
    public JSONObject remove(Vector<String> citiesNames);
    public void remove(String city) throws NotRemovedCity;
}