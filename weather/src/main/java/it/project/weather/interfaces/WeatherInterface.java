package it.project.weather.interfaces;

import java.util.TimeZone;

import org.json.simple.JSONObject;

public interface WeatherInterface {
	
	public void createFromJSON(JSONObject jobj, TimeZone offset);
    public JSONObject toJSON();
}
