package it.project.weather.interfaces;

import org.json.simple.JSONObject;

public interface WeatherModelEntity
{
    public void createFromJSON(JSONObject jObject);
    public JSONObject toJSON();
}
