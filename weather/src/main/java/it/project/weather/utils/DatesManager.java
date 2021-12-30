package it.project.weather.utils;

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
import it.project.weather.model.City;

public class DatesManager 
{
    public static JSONArray getHourlyWeatherFilteredByStartAndEndDates(WeatherService wService, City city,Calendar startDate, Calendar endDate) throws IOException, ParseException
    {
        //array which contains all hourly json for creating stats
        JSONArray weatherEveryHour = new JSONArray();

        //Useful variables
        String dt = null;
        boolean doesEndHourEveryDayExceed = false;
        Vector<String> exclude = new Vector<String>();
        exclude.add("current");
        exclude.add("minutely");
        exclude.add("daily");
        exclude.add("alerts");

        Calendar start = (Calendar) startDate.clone(),
                 today = Calendar.getInstance(),
                 weatherDate = Calendar.getInstance(),
                 endHourEveryDay = (Calendar) endDate.clone();

        today.setTime(new Date());
        start.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),1,0,1);
        endHourEveryDay.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        if(doesEndHourEveryDayExceed = !startDate.before(endHourEveryDay))
            endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);

        if(!doesEndHourEveryDayExceed)
        {
            //before today
            while(startDate.before(today) && endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), start.getTime());
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                for(int i = 0; i < jarray.size(); i++)
                {
                    dt = ""+((JSONObject) jarray.get(i)).get("dt");
                    weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                    if(weatherDate.compareTo(endHourEveryDay) > 0)
                        break;
                    if((weatherDate.compareTo(endHourEveryDay) <= 0) && (weatherDate.compareTo(startDate) >= 0))
                        weatherEveryHour.add((JSONObject) jarray.get(i));

                }
                if(startDate.get(Calendar.HOUR_OF_DAY) == 0)
                {
                    jobj = wService.historicalWeatherAPI(city.getCoord(), startDate.getTime());
                    jarray = (JSONArray) jobj.get("hourly");
                    for(int i = 0; i < jarray.size(); i++)
                    {
                        dt = ""+((JSONObject) jarray.get(i)).get("dt");
                        weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                        if(weatherDate.compareTo(endHourEveryDay) > 0)
                            break;
                        if((weatherDate.compareTo(endHourEveryDay) <= 0) && (weatherDate.compareTo(startDate) >= 0))
                            weatherEveryHour.add((JSONObject) jarray.get(i));

                    }
                }
                startDate.add(Calendar.DAY_OF_MONTH, 1);
                start.add(Calendar.DAY_OF_MONTH, 1);
                endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
            }
            //after today
            if(endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.oneCallAPI(city.getCoord(), exclude);
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                int i=0;
                while(endHourEveryDay.compareTo(endDate) <= 0)
                {
                    for(; i < jarray.size(); i++)
                    {
                        dt = ""+((JSONObject) jarray.get(i)).get("dt");
                        weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                        if((weatherDate.compareTo(endHourEveryDay) <= 0) && (weatherDate.compareTo(startDate) >= 0))
                            weatherEveryHour.add((JSONObject) jarray.get(i));
                        if(weatherDate.compareTo(endHourEveryDay) > 0)
                            break;
                    }
                    endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
                    startDate.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
        else
        {
            //before today
            if(start.before(today))
            {
                JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), start.getTime());
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                for(int i = 0; i < jarray.size(); i++)
                {
                    dt = ""+((JSONObject) jarray.get(i)).get("dt");
                    weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);  
                    if(weatherDate.compareTo(startDate) >= 0)
                        weatherEveryHour.add((JSONObject) jarray.get(i));
                }
                startDate.add(Calendar.DAY_OF_MONTH, 1);
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
            System.out.println("----");
            while(start.before(today) && endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), start.getTime());
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                for(int i = 0; i < jarray.size(); i++)
                {
                    dt = ""+((JSONObject) jarray.get(i)).get("dt");
                    weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                    if(((weatherDate.compareTo(endHourEveryDay) <= 0) || (weatherDate.compareTo(startDate) >= 0)) && (weatherDate.compareTo(endDate) <= 0))
                        weatherEveryHour.add((JSONObject) jarray.get(i));
                }
                startDate.add(Calendar.DAY_OF_MONTH, 1);
                start.add(Calendar.DAY_OF_MONTH, 1);
                endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
            }
            //after today
            startDate.add(Calendar.DAY_OF_MONTH, -1); //that is done because we still are in the same day
            if(endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.oneCallAPI(city.getCoord(), exclude);
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                int i=0;
                while(endHourEveryDay.compareTo(endDate) <= 0)
                {
                    for(; i < jarray.size(); i++)
                    {
                        dt = ""+((JSONObject) jarray.get(i)).get("dt");
                        weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                        if(weatherDate.after(endHourEveryDay))
                            break;
                        if((weatherDate.compareTo(endHourEveryDay) <= 0) && (weatherDate.compareTo(startDate) >= 0))
                            weatherEveryHour.add((JSONObject) jarray.get(i));
                        
                    }
                    endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
                    startDate.add(Calendar.DAY_OF_MONTH, 1);
                    start.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
        
        //that prints all hours selected by method, we use it to check executions' results
        System.out.println("--weatherEveryHour list--");
        int j;
        for(j=0; j < weatherEveryHour.size(); j++)
        {
            SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            k.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                dt = ""+((JSONObject) weatherEveryHour.get(j)).get("dt");
                weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                System.out.println(
                    k.format(
                        weatherDate.getTime()
                    )
                );
        }
        System.out.println("--end list--");

        return weatherEveryHour;
    }
}