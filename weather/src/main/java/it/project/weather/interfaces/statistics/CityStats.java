package it.project.weather.interfaces.statistics;

import java.util.Calendar;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherService;

public interface CityStats
{
    public void createStats(WeatherService wService, Calendar startDate, Calendar endDate) throws Exception;
    public JSONObject toJSON();
}
