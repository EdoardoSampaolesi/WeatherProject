package it.project.weather.utils;

import java.util.Vector;

import org.json.simple.JSONArray;

import it.project.weather.interfaces.CitiesManager;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.services.WeatherServiceImpl;

public abstract class CitiesManagerImpl implements CitiesManager
{
    protected static Vector<City> cityList;
    protected WeatherService wService = new WeatherServiceImpl("example");

    @Override
    public boolean add(Vector<String> citiesNames)
    {
        for (String name : citiesNames)
        {
            this.add(name);
        }
        return true;
    }

    @Override
    public void add(String city)
    {
        City tempCity = new City(city);
        tempCity.createFromJSON(wService);
        cityList.add(tempCity);
    }

    @Override
    public String getWeather(Vector<String> cities) 
    {
        JSONArray array = new JSONArray();
        for(String name : cities) 
        {
            City city  = new City(name);
            if(!cityList.contains(city))
                cityList.add(city);
            array.add(getJSONString(city));
        }
        return array.toJSONString();
    }

    protected abstract String getJSONString(City city);

    @Override
    public String remove(Vector<String> citiesNames)
    {
        String response = "";
        for (String name : citiesNames)
        {
            response += this.remove(name) + "\n";
        }
        return response;
    }

    @Override
    public String remove(String city)
    {
        if(cityList.remove(new City(city)))
            return "Succesful removed " + city + " from the list";
        return city + " wasn't part of the list";
    }
}
