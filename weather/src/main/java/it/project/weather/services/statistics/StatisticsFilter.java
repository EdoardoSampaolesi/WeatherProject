package it.project.weather.services.statistics;

/**
 * @author @EdoardoSampaolesi
 */

import java.util.Calendar;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.exeptions.DateOutOfRangeException;
import it.project.weather.exeptions.StatNotAvailableException;
import it.project.weather.interfaces.statistics.CityStats;
import it.project.weather.model.City;
import it.project.weather.model.statistics.CityStatsImpl;
import it.project.weather.services.CitiesManagerImpl;

/**
 * That class is the filter, it contains all informations insert as route parameters
 */
public class StatisticsFilter extends CitiesManagerImpl
{
    /**
     * This paramter represents the list of cities to exclude in creating statistics
     */
    Vector<City> citiesToExclude = new Vector<City>();
    Calendar startDate = null,endDate = null;

    public StatisticsFilter(String[] cities, Calendar startDate, Calendar endDate) throws Exception
    {
        for(String stringCityExclude : cities)
        {
            try
            {
                City c = new City(stringCityExclude);
                c.createFromJSON(super.wService);
                citiesToExclude.add(c);
            }
            catch(CityNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public StatisticsFilter(Calendar startDate, Calendar endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    
    /** 
     * Main method used to create statistics
     * 
     * @return String the Json response as string
     * @throws Exception any exception is rethorwn because it is used to send an INTERNAL ERROR HttpStatus to the user
     */
    public String getWeather() throws Exception
    {
        JSONArray array = new JSONArray();
        boolean check = false;
        for(City city : cityList)
        {
            for(City cityName : citiesToExclude)
                if(check = cityName.getNamecity().equals(city.getNamecity()))
                    break;
            if(!check)
                array.add(this.getJSONString(city)); 
        }    
        return array.toJSONString();
    }

    
    /** 
     * Calcultes statistics for a single city
     * 
     * @param city
     * @return JSONObject the Json of the statistic
     * @throws Exception any exception is rethorwn because it is used to send an INTERNAL ERROR HttpStatus to the user
     */
    @Override
    protected JSONObject getJSONString(City city) throws Exception 
    {
        CityStats stats = new CityStatsImpl(city);
        try
        {
            stats.createStats(super.wService,startDate,endDate);
            return stats.toJSON();
        }
        catch(DateOutOfRangeException e)
        {
            JSONObject o = e.getErrorJSONObject();
            o.put("city", city.getNamecity());
            return o;
        }
        catch(StatNotAvailableException e)
        {
            JSONObject o = e.getErrorJSONObject();
            o.put("city", city.getNamecity());
            return o;
        }
        
    }
    
}