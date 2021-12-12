package it.project.weather.utils;

import java.util.Vector;

import it.project.weather.interfaces.CitiesManager;
import it.project.weather.interfaces.City;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.CityImpl;
import it.project.weather.services.WeatherServiceImpl;

public abstract class CitiesManagerImpl implements CitiesManager
{
    protected static Vector<City> cityList;
    protected static WeatherService wService = new WeatherServiceImpl("example");

    @Override
    public void add(Vector<String> citiesNames)
    {
        for (String name : citiesNames) {
            this.add(name);
        }
    }

    @Override
    public void add(String city)
    {
        City tempCity = new CityImpl(city);
        tempCity.createFromJSON(wService.GeocodingAPI(city));
        cityList.add(tempCity);
    }

    @Override
    public abstract String getAll();

    @Override
    public abstract String getWeather(String city);

    @Override
    public String remove(String city)
    {
        if(cityList.remove(new CityImpl(city)))
            return "Succesful removed " + city + " from the list";
        return "Fail";
    }
}
