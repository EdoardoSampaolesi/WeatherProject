package it.project.weather.utils;

/**
 * @author @EdoardoSampaolesi
 */

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.NotRemovedCity;
import it.project.weather.interfaces.CitiesManager;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.services.WeatherServiceImpl;

public abstract class CitiesManagerImpl implements CitiesManager
{
    protected static Vector<City> cityList;
    protected WeatherService wService = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");

    @Override
    public void add(Vector<String> citiesNames) throws CityNotFoundException, Exception
    {
        for (String name : citiesNames)
        {
            this.add(name);
        }
    }

    @Override
    public void add(String city) throws CityNotFoundException, Exception
    {
        City tempCity = new City(city);
        tempCity.createFromJSON(wService);
        cityList.add(tempCity);
    }

    @Override
    public String getWeather(Vector<String> cities) throws Exception
    {
        JSONArray array = new JSONArray();
        for(String name : cities) 
        {
            try
            {
                City city  = new City(name);
                city.createFromJSON(wService);
                if(!cityList.contains(city))
                    cityList.add(city);
                array.add(getJSONString(city));
            }
            catch(CityNotFoundException e)
            {
                array.add(e.getErrorJSONObject().toJSONString());
            }
        }
        return array.toJSONString();
    }

    protected abstract String getJSONString(City city) throws Exception;

    @Override
    public JSONObject remove(Vector<String> citiesNames)
    {
        JSONArray response = new JSONArray();
        for (String name : citiesNames)
        {
            try
            {
                this.remove(name);
            }
            catch(NotRemovedCity e)
            {
                response.add(e.getErrorJSONObject());
            }
        }
        return JSONObject.class.cast(response);
    }

    @Override
    public void remove(String city) throws NotRemovedCity
    {
        try
        {
            if(!cityList.remove(new City(city)))
            throw new NotRemovedCity(city);
        }
        catch(CityNotFoundException e)
        {
            throw new NotRemovedCity(city);
        }
    }
}
