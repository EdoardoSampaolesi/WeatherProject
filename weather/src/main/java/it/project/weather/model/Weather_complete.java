package it.project.weather.model;

import java.util.Date;

public class Weather_complete extends Weather {
	
	public Weather_complete(Date date, String main_weather, String description, double humidity, short clouds, double wind_speed, short wind_deg, String wind_type, short rain, short snow, short visibility, double temp_current, double temp_feelslike, short pop_rain, Weather_complete t) 
	{
		super(date, main_weather, description, humidity, clouds, wind_speed, wind_deg, wind_type, rain, snow, visibility, temp_current, temp_feelslike, pop_rain, t);
	}
	
	private double temp_max;
	private double temp_min;
	
	public double getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}
	public double getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}
}
