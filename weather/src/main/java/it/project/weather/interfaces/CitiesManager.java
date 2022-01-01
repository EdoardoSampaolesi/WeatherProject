package it.project.weather.interfaces;

import org.json.simple.JSONArray;

import it.project.weather.exeptions.NotRemovedCity;

public interface CitiesManager
{
    public JSONArray add(String[] citiesNames) throws Exception;
    public void add(String city) throws Exception;
    public String getWeather(String[] cities) throws Exception;
    public JSONArray remove(String[] citiesNames);
    public void remove(String city) throws NotRemovedCity;
}