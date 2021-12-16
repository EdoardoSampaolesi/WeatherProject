package it.project.weather.model;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import java.util.Date;

public class Weather implements WeatherModelEntity 
{
	private Date date= new Date();
	private String main_weather;
	private String description;
	private double humidity;
	private short clouds; //nuvolosità %
	private double wind_speed;
	private short wind_deg;
	private String wind_type;
	private short rain;
	private short snow;
	private short visibility;
	private double temp_current;
	private double temp_feelslike;
	private short pop_rain; // %
	private Weather_complete t = new Weather_complete(); // temp_max e temp_min
	
	public Weather(Date date, String main_weather, String description, double humidity, short clouds, double wind_speed, short wind_deg, String wind_type, short rain, short snow, short visibility, double temp_current, double temp_feelslike, short pop_rain, Weather_complete t )
	{
		this.date = date;
		this.main_weather = main_weather;
		this.description= description;
		this.humidity = humidity;
		this.clouds = clouds; 
		this.wind_speed = wind_speed;
		this.wind_deg = wind_deg;
		this.wind_type = wind_type;
		this.rain = rain;
		this.snow = snow;
		this.visibility = visibility;
		this.temp_current = temp_current;
		this.temp_feelslike=temp_feelslike;
		this.pop_rain = pop_rain;
		this.t = t;
	}

    @Override
    public void createFromJSON(WeatherService wService) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public JSONObject toJSON() 
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
