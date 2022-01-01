package it.project.weather.services;

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

public abstract class CitiesManagerImpl implements CitiesManager
{
    /**
     * A list of cities, mostly used for statistics
     */
    protected static Vector<City> cityList = new Vector<City>();
    /**
     * Service used in every other class which requires a service for performing OpenWeather API calls
     */
    protected WeatherService wService = new WeatherServiceImpl("d6a4e0d799239c1f85eaf82a5088ddfe");

    
    /** 
     * Main method for adding a city to the personal list
     * 
     * @param citiesNames list of cities to add
     * @return JSONArray errors formatted as Json
     * @throws Exception any exception is rethrown
     */
    @Override
    public JSONArray add(String[] citiesNames) throws Exception
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
            catch (CityNotFoundException e)
            {
                response.add(e.getErrorJSONObject());
            }
        }
        JSONArray o = new JSONArray();
        for(City c : cityList)
            o.add(c.toJSON());
        JSONObject obj = new JSONObject();
        obj.put("cities", o);
        response.add(o);
        return response.size() == 0 ? null : response;
    }

    
    /** 
     * Allows to add a single city if not already exists in the list
     * 
     * @param city single city to add
     * @throws Exception any exception is rethrown
     */
    @Override
    public void add(String city) throws Exception
    {
        City tempCity = new City(city);
        for(City c : cityList)
            if(c.getNamecity().equals(tempCity.getNamecity()))
                throw new CityNotAddedException(city);
        tempCity.createFromJSON(wService);
        cityList.add(tempCity);
    }

    
    /** 
     * Return weather informations for every city in cities parameter
     * 
     * @param cities a list of cities
     * @return String JsonString of the weather informations of every city
     * @throws Exception any Exception is rethrown
     */
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
                City city = null;
                for(City c : cityList)
                    if(check = c.getNamecity().equals(name))
                    {
                        city = c;
                        break;
                    }
                if(!check)
                {
                    city  = new City(name);
                    city.createFromJSON(wService);
                    cityList.add(city);
                }
                array.add(getJSONString(city));
            }
            catch(CityNotFoundException e)
            {
                array.add(e.getErrorJSONObject().toJSONString());
            }
        }
        for(City c : cityList)
            System.out.println(c.getNamecity());
        return array.toJSONString();
    }

    protected abstract JSONObject getJSONString(City city) throws Exception;
    
    /** 
     * Main method for removing a city to the personal list
     * 
     * @param citiesNames list of cities to remove
     * @return JSONArray errors formatted as Json
     */
    @Override
    public JSONArray remove(String[] citiesNames)
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
        JSONArray o = new JSONArray();
        for(City c : cityList)
            o.add(c.toJSON());
        JSONObject obj = new JSONObject();
        obj.put("cities", o);
        response.add(o);
        return response.size() == 0 ? null : response;
    }

    
    /** 
     * Allows to remove a single city if it exists
     * 
     * @param city Name of the city to remove
     * @throws NotRemovedCity This Exception occurs when is not possible removing the city
     */
    @Override
    public void remove(String city) throws NotRemovedCity
    {
        try
        {
            City tempCity = new City(city);
            for(City c : cityList)
                if(c.getNamecity().equals(tempCity.getNamecity()))
                {
                    cityList.remove(c);
                    return;
                }
            throw new NotRemovedCity(tempCity.getNamecity());
        }
        catch(CityNotFoundException e)
        {
            throw new NotRemovedCity(city);
        }
    }
}
