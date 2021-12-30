package it.project.weather.interfaces.statistics;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.util.Calendar;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.DateOutOfRangeException;
import it.project.weather.interfaces.WeatherService;

public interface CityStats
{
    public void createStats(WeatherService wService, Calendar startDate, Calendar endDate) throws IOException, ParseException, DateOutOfRangeException;
    public JSONObject toJSON();
}
