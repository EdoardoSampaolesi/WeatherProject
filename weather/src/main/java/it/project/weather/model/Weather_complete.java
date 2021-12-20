package it.project.weather.model;

import java.util.TimeZone;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherService;

public class Weather_complete extends Weather {
	
	private double temp_max;
	private double temp_min;
	
	public Weather_complete(double temp_max, double temp_min, String date, String main_weather, String description, short humidity, double clouds, double wind_speed, short wind_deg, String wind_type, double rain, double snow, short visibility, double temp_current, double temp_feelslike, double pop_rain) 
	{
		super(date, main_weather, description, humidity, clouds, wind_speed, wind_deg, wind_type, rain, snow, visibility, temp_current, temp_feelslike, pop_rain);
		this.temp_max=temp_max;
		this.temp_min=temp_min;
	}
	
	public Weather_complete(double temp_max, double temp_min) {
		this.temp_max=temp_max;
		this.temp_min=temp_min;
	}
	
	 public void createFromJSON(JSONObject jobj, TimeZone offset) 
	 {
		 super.createFromJSON(jobj, offset);
		 double temp_max=(double) jobj.get("temp_max");
	     this.temp_max = temp_max;
	 }

	public double getTemp_max() 
	{
		return temp_max;
	}
	public void setTemp_max(double temp_max) 
	{
		this.temp_max = temp_max;
	}
	
	public double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(double temp_min) 
	{
		this.temp_min = temp_min;
	}
}
