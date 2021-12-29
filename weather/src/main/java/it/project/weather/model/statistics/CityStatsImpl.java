package it.project.weather.model.statistics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.interfaces.statistics.CityStats;
import it.project.weather.interfaces.statistics.Stats;
import it.project.weather.model.City;
import it.project.weather.utils.DatesManager;

public class CityStatsImpl implements CityStats
{
    City city;
    Vector<Stats> statsList = new Vector<Stats>();
    Vector<String> StatisticNames = new Vector<String>();

    public CityStatsImpl(City city) 
    {
        this.city = city;
        StatisticNames.add("temp");
        StatisticNames.add("clouds");
        StatisticNames.add("pressure");
        StatisticNames.add("humidity");
    }

    /**
     * if startDate and endDate are null, the default range for statistics is between 5 days before and 7 days after
     * @throws ParseException
     * @throws IOException
     */
    @Override
    public void createStats(WeatherService wService, Calendar startDate, Calendar endDate) throws IOException, ParseException 
    {
        //clear statistics list
        statsList.clear();

        //main method
        JSONArray weatherEveryHour = DatesManager.getHourlyWeatherFilteredByStartAndEndDates(wService, city, startDate, endDate);

       for(String statName : StatisticNames)
       {
           Stats s = new StatsImpl(statName);
           s.createFromJSON(weatherEveryHour);
           statsList.add(s);
       }
    } 

    @Override
    public JSONObject toJSON() 
    {
        JSONObject jobj = new JSONObject();
        JSONArray jarray = new JSONArray();
        jobj.put("city", city.getNamecity());
        for(Stats s : statsList)
            jarray.add(s.toJSON());
        jobj.put("statistics", jarray);
        return jobj;
    }
    
}
