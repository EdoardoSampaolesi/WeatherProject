package it.project.weather.interfaces;

import java.text.ParseException;
import java.util.Calendar;

import org.json.simple.JSONObject;

import it.project.weather.exeptions.CityNotFoundException;

public interface CityInterface
{
    public void createFromJSON(WeatherService wService) throws CityNotFoundException, Exception;
    public JSONObject toJSON();
    public Calendar fromCityOffsetToMyDate(Calendar date) throws ParseException;
}