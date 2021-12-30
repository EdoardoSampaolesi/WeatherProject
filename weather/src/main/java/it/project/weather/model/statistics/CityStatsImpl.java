package it.project.weather.model.statistics;

/**
 * @author @EdoardoSampaolesi
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
     * Method used to fill the statistics' list
     * <p>
     * For getting the datas it uses getHourlyWeatherFilteredByStartAndEndDates method which return a complete array filtered
     *  containing datas every hour, filtered by dates
     * 
     * @param wService needed to perform API call to OpenWeather site
     * @param startDate indicated as city local time
     * @param endDate indicated as city local time
     * @throws Exception every exception is rethrown
     */
    @Override
    public void createStats(WeatherService wService, Calendar startDate, Calendar endDate) throws Exception
    {
        //clear statistics list
        statsList.clear();

        startDate = city.fromCityOffsetToMyDate(startDate);
        endDate = city.fromCityOffsetToMyDate(endDate);
        /* //this will be a method in city class ->
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        parser.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
        startDate.setTime(
            sdf.parse( parser.format(startDate.getTime()).toString() )
            );
        endDate.setTime(
            sdf.parse( parser.format(endDate.getTime()).toString() )
            );
        // <- */

        //main method
        JSONArray weatherEveryHour = DatesManager.getHourlyWeatherFilteredByStartAndEndDates(wService, city, startDate, endDate);

       for(String statName : StatisticNames)
       {
           Stats s = new StatsImpl(statName);
           s.createFromJSON(weatherEveryHour);
           statsList.add(s);
       }
    } 

    
    /** 
     * Returns a Json containing all statistics for the specified city inside the class 
     * 
     * @return JSONObject
     */
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
