package it.project.weather.interfaces;

import java.util.Vector;

import it.project.weather.exeptions.CityNotFoundException;

public interface CitiesManager
{
    public void add(Vector<String> citiesNames) throws CityNotFoundException;
    public void add(String city) throws CityNotFoundException;
    public String getWeather(Vector<String> cities);
    public String remove(Vector<String> citiesNames);
    public String remove(String city);
}