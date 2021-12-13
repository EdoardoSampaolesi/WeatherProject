package it.project.weather.interfaces;

import org.json.simple.JSONObject;

public interface WeatherModelEntity
{
    public void createFromJSON(WeatherService wService);
    public JSONObject toJSON();
}
