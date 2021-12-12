package it.project.weather.interfaces;

import java.util.Vector;

public interface CitiesManager
{
    public void add(Vector<String> citiesNames);
    public void add(String city);
    public String getWeather(String city);
    public String getAll();
    public String remove(String city);
}