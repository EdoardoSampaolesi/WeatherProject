package it.project.weather.utils;

/**
 * @author @EdoardoSampaolesi
 */

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotAddedException;
import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.NotRemovedCity;
import it.project.weather.interfaces.CitiesManager;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.services.WeatherServiceImpl;

public abstract class CitiesManagerImpl implements CitiesManager
{
    protected static Vector<City> cityList = new Vector<City>();
    protected WeatherService wService = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");

    @Override
    public JSONObject add(String[] citiesNames) throws CityNotFoundException, Exception
    {
        JSONArray response = new JSONArray();
        for (String name : citiesNames)
        {
            try {
                this.add(name);
            } 
            catch (CityNotAddedException e) 
            {
                response.add(e.getErrorJSONObject());
            }
        }
        return JSONObject.class.cast(response);
    }

    @Override
    public void add(String city) throws CityNotFoundException, Exception, CityNotAddedException
    {
        for(City c : cityList)
            if(c.getNamecity().equals(city))
                throw new CityNotAddedException(city);
        City tempCity = new City(city);
        tempCity.createFromJSON(wService);
        cityList.add(tempCity);
    }

    @Override
    public String getWeather(String[] cities) throws Exception
    {
        JSONArray array = new JSONArray();
        boolean check;
        for(String name : cities) 
        {
            check = false;
            try
            {
                for(City c : cityList)
                    if(check = c.getNamecity().equals(name))
                        break;
                City city  = new City(name);
                city.createFromJSON(wService);
                if(!check)
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

    protected abstract JSONObject getJSONString(City city) throws Exception;

    @Override
    public JSONObject remove(String[] citiesNames)
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
