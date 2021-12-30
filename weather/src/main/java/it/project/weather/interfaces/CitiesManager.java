package it.project.weather.interfaces;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.NotRemovedCity;

public interface CitiesManager
{
    public JSONObject add(String[] citiesNames) throws Exception;
    public void add(String city) throws Exception;
    public String getWeather(String[] cities) throws Exception;
    public JSONObject remove(String[] citiesNames);
    public void remove(String city) throws NotRemovedCity;
}