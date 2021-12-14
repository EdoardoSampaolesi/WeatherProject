package it.project.weather.utils;

import java.util.Vector;

import org.json.simple.JSONArray;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.CitiesManager;
import it.project.weather.interfaces.WeatherService;
import it.project.weather.model.City;
import it.project.weather.services.WeatherServiceImpl;

public abstract class CitiesManagerImpl implements CitiesManager
{
    protected static Vector<City> cityList;
    protected WeatherService wService = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");

    @Override
    public void add(Vector<String> citiesNames) throws CityNotFoundException
    {
        for (String name : citiesNames)
        {
            this.add(name);
        }
    }

    @Override
    public void add(String city) throws CityNotFoundException
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
            catch(Exception e)
            {
                throw e;
            }
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
