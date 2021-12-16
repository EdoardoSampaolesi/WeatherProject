package it.project.weather.interfaces;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.NotRemovedCity;

public interface CitiesManager
{
    public void add(Vector<String> citiesNames) throws CityNotFoundException;
    public void add(String city) throws CityNotFoundException;
    public String getWeather(Vector<String> cities);
    public JSONObject remove(Vector<String> citiesNames);
    public void remove(String city) throws NotRemovedCity;
}