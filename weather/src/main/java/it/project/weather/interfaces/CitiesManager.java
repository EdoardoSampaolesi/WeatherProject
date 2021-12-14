package it.project.weather.interfaces;

import java.util.Vector;

public interface CitiesManager
{
    public boolean add(Vector<String> citiesNames);
    public void add(String city);
    public String getWeather(Vector<String> cities);
    public String remove(Vector<String> citiesNames);
    public String remove(String city);
}