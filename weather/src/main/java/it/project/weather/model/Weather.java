package it.project.weather.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherInterface;

public class Weather implements WeatherInterface
{
	private String date;
	private String main_weather;
	private String description;
	private long humidity; // %
	private long clouds; //cloudiness %
	private double wind_speed;
	private long wind_deg; //degrees
	private double rain;
	private double snow;
	private long visibility;
	private double temp_current;
	private double temp_feelslike;
	private long pop_rain; // %
	
	public Weather(String date, String main_weather, String description, long humidity, long clouds, double wind_speed, long wind_deg, double rain, double snow, long visibility, double temp_current, double temp_feelslike, long pop_rain)
	{
		this.date = date;
		this.main_weather = main_weather;
		this.description= description;
		this.humidity = humidity;
		this.clouds = clouds; 
		this.wind_speed = wind_speed;
		this.wind_deg = wind_deg;
		this.rain = rain;
		this.snow = snow;
		this.visibility = visibility;
		this.temp_current = temp_current;
		this.temp_feelslike=temp_feelslike;
		this.pop_rain = pop_rain;
	}
	
	public Weather() 
	{
		this(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

    @Override
    public void createFromJSON(JSONObject jobj, TimeZone offset) 
    {
		Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis((long)jobj.get("dt")*1000);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	sdf.setTimeZone(offset); 	 
     	this.date =sdf.format(calendar.getTime());
    	String main=(String) ((JSONObject)((JSONArray) jobj.get("weather")).get(0)).get("main");
    	this.main_weather=main;
		String description=(String) ((JSONObject)((JSONArray) jobj.get("weather")).get(0)).get("description");
    	this.description=description;
    	long humidity=(long) jobj.get("humidity");
    	this.humidity=humidity;
    	long clouds=(long) jobj.get("clouds");
    	this.clouds=clouds;
    	double wind_speed=(double) jobj.get("wind_speed");
     	this.wind_speed = wind_speed;
     	long wind_deg=(long) jobj.get("wind_deg");
     	this.wind_deg = wind_deg;   
     	if(((JSONObject) jobj.get("rain")).get("1h")!=null) 
     	{
     		double rain=(double)((JSONObject) jobj.get("rain")).get("1h"); 
         	this.rain = rain;
     	}
     	if(jobj.get("snow")!=null)
     	{
     		double snow=(double) jobj.get("snow");
         	this.snow = snow;
     	}
     	if(jobj.get("pop")!=null)
     	{
     		long pop_rain=(long) jobj.get("pop");
         	this.pop_rain = pop_rain;
     	}
     	long visibility=(short) jobj.get("visibility");
     	this.visibility = visibility;    	
     	double temp=(double) jobj.get("temp");
     	this.temp_current = temp;
     	double temp_feelslike=(double) jobj.get("feels_like");
     	this.temp_feelslike = temp_feelslike;
     	
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
    	att.put("precipitation volume", this.rain);
    	att.put("snow volume", this.snow);
    	att.put("visibility", this.visibility);
    	att.put("current temperature", this.temp_current);
    	att.put("feels like temperature", this.temp_feelslike);
    	att.put("probability percipitation", this.pop_rain);
        return att;
    }
    
    public String getDate() 
    {
		return date;
	}
    public void setDate(String date) 
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
    
    public long getHumidity() 
    {
		return humidity;
	}
    public void setHumidity(long humidity) 
    {
		this.humidity = humidity;
	}

	public long getClouds() {
		return clouds;
	}
	public void setClouds(long clouds) {
		this.clouds = clouds;
	}
	
	public double getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public long getWind_deg() {
		return wind_deg;
	}
	public void setWind_deg(long wind_deg) {
		this.wind_deg = wind_deg;
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

	public long getVisibility() {
		return visibility;
	}
	public void setVisibility(long visibility) {
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

	public long getPop_rain() {
		return pop_rain;
	}
	public void setPop_rain(long pop_rain) {
		this.pop_rain = pop_rain;
	}  
}
