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
	private double humidity; //%
	private double clouds; //nuvolosità %
	private double wind_speed;
	private short wind_deg; //gradi
	private String wind_type;
	private double rain;
	private double snow;
	private short visibility;
	private double temp_current;
	private double temp_feelslike;
	private double pop_rain; // %
	private Weather_complete t; // temp_max e temp_min
	
	public Weather(Date date, String main_weather, String description, double humidity, double clouds, double wind_speed, short wind_deg, String wind_type, double rain, double snow, short visibility, double temp_current, double temp_feelslike, double pop_rain, Weather_complete t )
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
    	 
     	//Date date = (Date)(jobj.get("dt")-offset.getOffset());
     	this.date = date;
    	String main=(String) jobj.get("main");
    	this.main_weather=main;
    	String description=(String) jobj.get("description");
    	this.description=description;
    	double humidity=(double) jobj.get("humidity");
    	this.humidity=humidity;
    	double clouds=(double) jobj.get("clouds");
    	this.clouds=clouds;
    	double wind_speed=(double) jobj.get("wind_speed");
     	this.wind_speed = wind_speed;
     	short wind_deg=(short) jobj.get("wind_deg");
     	this.wind_deg = wind_deg;
     	//String wind_type=(String) jobj.get("main");
    	//this.wind_type=wind_type;
     	double rain=(double) jobj.get("rain");
     	this.rain = rain;
     	double snow=(double) jobj.get("snow");
     	this.rain = snow;
     	short visibility=(short) jobj.get("visibility");
     	this.visibility = visibility;    	
     	double temp=(double) jobj.get("temp");
     	this.temp_current = temp;
     	double temp_feelslike=(double) jobj.get("feels_like");
     	this.temp_feelslike = temp_feelslike;
     	double pop_rain=(double) jobj.get("pop");
     	this.pop_rain = pop_rain;
     	double max=(double) jobj.get("max");
     	double min=(double) jobj.get("min");
     	this.t = new Weather_complete(max, min);
     	
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
    
    public double getHumidity() 
    {
		return humidity;
	}
    public void setDescription(double humidity) 
    {
		this.humidity = humidity;
	}

	public double getClouds() {
		return clouds;
	}
	public void setClouds(double clouds) {
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

	public double getRain() {
		return rain;
	}
	public void setRain(double rain) {
		this.rain = rain;
	}

	public double getSnow() {
		return snow;
	}
	public void setSnow(double snow) {
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

	public double getPop_rain() {
		return pop_rain;
	}
	public void setPop_rain(double pop_rain) {
		this.pop_rain = pop_rain;
	}

	public Weather_complete getT() {
		return t;
	}
	public void setT(Weather_complete t) {
		this.t = t;
	}
    
    
    
}
