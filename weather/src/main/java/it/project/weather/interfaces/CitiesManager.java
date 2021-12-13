package it.project.weather.interfaces;

import java.util.Vector;

import it.project.weather.model.City;

public interface CitiesManager
{
    public boolean add(Vector<String> citiesNames);
    public void add(String city);
    public String getWeather(Vector<String> cities);
    public String remove(String city);
}