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
        /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Calendar GMTstartDate = (Calendar) startDate.clone();
        Calendar GMTendHourEveryDay = (Calendar) endHourEveryDay.clone();
        parser.setTimeZone(TimeZone.getTimeZone("GMT"));
        try 
        {
            GMTstartDate.setTime(
                sdf.parse( parser.format(today.getTime()).toString() )
                );
            GMTendHourEveryDay.setTime(
                sdf.parse( parser.format(today.getTime()).toString() )
                );
        } catch (java.text.ParseException e) 
        {
            e.printStackTrace();
        } */ 
        if(doesEndHourEveryDayExceed = !startDate.before(endHourEveryDay))
            endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
 // test ->
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
System.out.println(f.format(today.getTime()));
System.out.println(f.format(startDate.getTime()));
System.out.println(f.format(start.getTime()));
// <- 
        if(!doesEndHourEveryDayExceed)
        {
            //before today
            while(startDate.before(today) && endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), startDate.getTime());
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                for(int i = 0; i < jarray.size(); i++)
                {
                    dt = ""+((JSONObject) jarray.get(i)).get("dt");
                    weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                    //test ->
                    System.out.println(dt + " " + i);
                SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                k.setTimeZone(TimeZone.getTimeZone("GMT"));
                System.out.println("open data " + k.format(weatherDate.getTime()));
                k.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                System.out.println("Chicago quella di sopra " + k.format(weatherDate.getTime()));
                k.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
                System.out.println("end Every day " + k.format(endHourEveryDay.getTime()));
                System.out.println("startDate" + k.format(startDate.getTime()));
                System.out.println("start" + k.format(start.getTime()));
                System.out.println("fine:");
                System.out.println(weatherDate.compareTo(endHourEveryDay) <= 0);
                System.out.println(weatherDate.compareTo(startDate) >= 0);
                    // <- 
                    if(weatherDate.compareTo(endHourEveryDay) > 0)
                        break;
                    if((weatherDate.compareTo(endHourEveryDay) <= 0) && (weatherDate.compareTo(startDate) >= 0))
                        weatherEveryHour.add((JSONObject) jarray.get(i));
                }
                startDate.add(Calendar.DAY_OF_MONTH, 1);
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
                        /* //test ->
                    System.out.println(dt + " " + i);
                    SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    //k.setTimeZone(TimeZone.getTimeZone("GMT"));
                    System.out.println(k.format(weatherDate.getTime()));
                    k.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                    System.out.println(k.format(weatherDate.getTime()));
                    System.out.println(k.format(endHourEveryDay.getTime()));
                    System.out.println(k.format(startDate.getTime()));
                    System.out.println(weatherDate.compareTo(endHourEveryDay) <= 0);
                    System.out.println(weatherDate.compareTo(startDate) >= 0);
                    // <- */
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
            while(start.before(today) && endHourEveryDay.compareTo(endDate) <= 0)
            {
                JSONObject jobj = wService.historicalWeatherAPI(city.getCoord(), start.getTime());
                JSONArray jarray = (JSONArray) jobj.get("hourly");
                for(int i = 0; i < jarray.size(); i++)
                {
                    dt = ""+((JSONObject) jarray.get(i)).get("dt");
                    weatherDate.setTimeInMillis(Long.parseLong(dt)*1000);
                 /* //test ->
                System.out.println(dt + " " + i);
                SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                k.setTimeZone(TimeZone.getTimeZone("GMT"));
                System.out.println("open data " + k.format(weatherDate.getTime()));
                k.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                System.out.println("Chicago quella di sopra " + k.format(weatherDate.getTime()));
                k.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
                System.out.println("end Every day " + k.format(endHourEveryDay.getTime()));
                System.out.println("startDate" + k.format(startDate.getTime()));
                System.out.println("start" + k.format(start.getTime()));
                System.out.println("fine:");
                System.out.println(weatherDate.compareTo(endHourEveryDay) <= 0);
                System.out.println(weatherDate.compareTo(startDate) >= 0);
                // <-  */
                    if(((weatherDate.compareTo(endHourEveryDay) <= 0) || (weatherDate.compareTo(startDate) >= 0)) && (weatherDate.compareTo(endDate) <= 0))
                        weatherEveryHour.add((JSONObject) jarray.get(i));
                }
                startDate.add(Calendar.DAY_OF_MONTH, 1);
                start.add(Calendar.DAY_OF_MONTH, 1);
                endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
            }
            //after today
            start.add(Calendar.DAY_OF_MONTH, 1);
            endHourEveryDay.add(Calendar.DAY_OF_MONTH, -1);
            startDate.add(Calendar.DAY_OF_MONTH, -1);
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
                    /* //test ->
                    System.out.println(dt + " " + i);
                    SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    k.setTimeZone(TimeZone.getTimeZone("GMT"));
                    System.out.println("open data " + k.format(weatherDate.getTime()));
                    k.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
                    System.out.println("Chicago quella di sopra " + k.format(weatherDate.getTime()));
                    System.out.println("end Every day " + k.format(endHourEveryDay.getTime()));
                    System.out.println("startDate" + k.format(startDate.getTime()));
                    System.out.println(weatherDate.compareTo(endHourEveryDay) <= 0);
                    System.out.println(weatherDate.compareTo(startDate) >= 0);
                    // <- */
                        if(weatherDate.after(endDate) || weatherDate.after(start))
                            break;
                        if((weatherDate.compareTo(endHourEveryDay) <= 0) || (weatherDate.compareTo(startDate) >= 0))
                            weatherEveryHour.add((JSONObject) jarray.get(i));
                        
                    }
                    endHourEveryDay.add(Calendar.DAY_OF_MONTH, 1);
                    startDate.add(Calendar.DAY_OF_MONTH, 1);
                    start.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }
         System.out.println(
            "-------------"
        );
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
