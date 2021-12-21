package it.project.weather.services.statistics;

import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.statistics.CityStats;
import it.project.weather.model.City;
import it.project.weather.model.statistics.CityStatsImpl;
import it.project.weather.utils.CitiesManagerImpl;

public class StatisticsFilter extends CitiesManagerImpl
{
    Vector<City> citiesToExclude = new Vector<City>();
    Date startDate = null,endDate = null,startTime = null,endTime = null;

    public StatisticsFilter(String[] cities, Date startDate, Date endDate, Date startTime, Date endTime) throws Exception
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
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getWeather() throws Exception
    {
        JSONArray array = new JSONArray();
        for(City city : cityList) 
        {
                if(!citiesToExclude.contains(city))
                {
                    array.add(this.getJSONString(city));
                }
        }        
        return array.toJSONString();
    }

    @Override
    protected String getJSONString(City city) throws Exception 
    {
        CityStats stats = new CityStatsImpl(city);
        stats.createStats(super.wService,startDate,endDate,startTime,endTime);
        return stats.toJSON().toJSONString();
    }
    
}