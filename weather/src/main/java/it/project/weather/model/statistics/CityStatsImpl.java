package it.project.weather.model.statistics;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.project.weather.interfaces.WeatherService;
import it.project.weather.interfaces.statistics.CityStats;
import it.project.weather.interfaces.statistics.Stats;
import it.project.weather.model.City;

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
        statsList.clear();
        int i;
        String dt = null;
        JSONArray weatherEveryHour = new JSONArray();
        Calendar endHourEveryDay = endDate, today = Calendar.getInstance(), weatherDate = Calendar.getInstance();
        today.setTime(new Date());
        endHourEveryDay.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        while(startDate.before(today) && endHourEveryDay.compareTo(endDate) <= 0)
        {
            JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), startDate.getTime());
            JSONArray jarray = (JSONArray) jobj.get("hourly");

            for(i = 0; i < jarray.size(); i++)
                dt = (String) ((JSONObject) jarray.get(i)).get("dt");
                weatherDate.setTimeInMillis(Integer.parseInt(dt)*1000);
                if(weatherDate.compareTo(endHourEveryDay) <= 0)
                    weatherEveryHour.add((JSONObject) jarray.get(i));
                else
                    break;
            endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        while(endHourEveryDay.compareTo(endDate) <= 0)
        {
            Vector<String> exclude = new Vector<String>();
            exclude.add("current");
            exclude.add("minutely");
            exclude.add("daily");
            exclude.add("alerts");
            JSONObject jobj = wService.oneCallAPI(city.getCoord(), exclude);
            JSONArray jarray = (JSONArray) jobj.get("hourly");

            for(i = 0; i < jarray.size(); i++)
                dt = (String) ((JSONObject) jarray.get(i)).get("dt");
                weatherDate.setTimeInMillis(Integer.parseInt(dt)*1000);
                if(weatherDate.compareTo(endHourEveryDay) <= 0)
                    weatherEveryHour.add((JSONObject) jarray.get(i));
                else
                    break;
            endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
        }

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
