package it.project.weather.services.statistics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.statistics.CityStats;
import it.project.weather.model.City;
import it.project.weather.model.statistics.CityStatsImpl;
import it.project.weather.utils.CitiesManagerImpl;

public class StatisticsFilter extends CitiesManagerImpl
{
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
    protected JSONObject getJSONString(City city) throws Exception 
    {
        CityStats stats = new CityStatsImpl(city);
        //this will be a method in city class ->
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        parser.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(
            sdf.parse( parser.format(this.startDate.getTime()).toString() )
            );
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(
                sdf.parse( parser.format(this.endDate.getTime()).toString() )
                );
        
        // <-
        stats.createStats(super.wService,startDate,endDate);
        return stats.toJSON();
    }
    
}