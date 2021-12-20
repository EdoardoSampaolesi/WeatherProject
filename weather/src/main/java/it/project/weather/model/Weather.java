package it.project.weather.model;

import org.json.simple.JSONObject; 

import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.WeatherInterface;
import it.project.weather.interfaces.WeatherModelEntity;
import it.project.weather.interfaces.WeatherService;
import java.util.Date;
import java.util.TimeZone;

public class Weather implements WeatherInterface
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
	private Weather_complete t; // temp_max e temp_min
	
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
	
	public Weather() 
	{
		
	}

    @Override
    public void createFromJSON(JSONObject jobj, TimeZone offset) 
    {
    	double temp=(double) jobj.get("temp");
     	this.temp_current = temp; 
     	//Date date = (Date)(jobj.get("dt")-offset.getOffset());
     	this.date = date;
    }
    

    @Override
    public JSONObject toJSON() 
    {
    	JSONObject att = new JSONObject();
    	att.put("date", this.date);
    	att.put("weather", this.main_weather);
    	att.put("weather descritpion", this.description);
    	att.put("humidity", this.humidity);
    	att.put("cloudiness", this.clouds);
    	att.put("wind speed", this.wind_speed);
    	att.put("wind direction", this.wind_deg);
    	att.put("wind type", this.wind_type);
    	att.put("precipitation volume", this.rain);
    	att.put("snow volume", this.snow);
    	att.put("visibility", this.visibility);
    	att.put("current temperature", this.temp_current);
    	att.put("feels like temperature", this.temp_feelslike);
    	att.put("probability percipitation", this.pop_rain);
    	//att.put(" ", this.t);
        return att;
    }
    
    public Date getDate() 
    {
		return date;
	}
    public void setDate(Date date) 
    {
		this.date = date;
	}
    
    public String getMainwether() 
    {
		return main_weather;
	}
    public void setMainweather(String main_weather) 
    {
		this.main_weather = main_weather;
	}
    
    public String getDescription() 
    {
		return description;
	}
    public void setDescription(String description) 
    {
		this.description = description;
	}
    
    public Double getHumidity() 
    {
		return humidity;
	}
    public void setDescription(Double humidity) 
    {
		this.humidity = humidity;
	}

	public short getClouds() {
		return clouds;
	}
	public void setClouds(short clouds) {
		this.clouds = clouds;
	}
	
	public double getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public short getWind_deg() {
		return wind_deg;
	}
	public void setWind_deg(short wind_deg) {
		this.wind_deg = wind_deg;
	}

	public String getWind_type() {
		return wind_type;
	}
	public void setWind_type(String wind_type) {
		this.wind_type = wind_type;
	}

	public short getRain() {
		return rain;
	}
	public void setRain(short rain) {
		this.rain = rain;
	}

	public short getSnow() {
		return snow;
	}
	public void setSnow(short snow) {
		this.snow = snow;
	}

	public short getVisibility() {
		return visibility;
	}
	public void setVisibility(short visibility) {
		this.visibility = visibility;
	}

	public double getTemp_current() {
		return temp_current;
	}
	public void setTemp_current(double temp_current) {
		this.temp_current = temp_current;
	}

	public double getTemp_feelslike() {
		return temp_feelslike;
	}
	public void setTemp_feelslike(double temp_feelslike) {
		this.temp_feelslike = temp_feelslike;
	}

	public short getPop_rain() {
		return pop_rain;
	}
	public void setPop_rain(short pop_rain) {
		this.pop_rain = pop_rain;
	}

	public Weather_complete getT() {
		return t;
	}
	public void setT(Weather_complete t) {
		this.t = t;
	}
    
    
    
}
