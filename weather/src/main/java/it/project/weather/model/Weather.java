package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import it.project.weather.exeptions.CityNotFoundException;
import it.project.weather.interfaces.WeatherInterface;

public class Weather implements WeatherInterface
{
	private String date;
	private String main_weather;
	private String description;
	private long humidity;
	private long clouds;
	private double wind_speed;
	private long wind_deg;
	private double rain;
	private double snow;
	private long visibility;
	private double temp_current;
	private double temp_feelslike;
	private double pop_rain;
	
	/**
	 * Constructor of the weather.
	 * 
	 * @param date of the city
	 * @param main_weather, a word to describe the weather
	 * @param description a description of the weather
	 * @param humidity
	 * @param clouds, cloudiness
	 * @param wind speed
	 * @param wind degrees
	 * @param rain volume
	 * @param snow volume
	 * @param visibility
	 * @param temp_current, current temperature
	 * @param temp_feelslike, temperature parameter for the human perception of weather
	 * @param pop_rain, probaility of precipitation
	 */
	public Weather(String date, String main_weather, String description, long humidity, long clouds, double wind_speed, long wind_deg, double rain, double snow, long visibility, double temp_current, double temp_feelslike, double pop_rain)
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

	/**
	 * This method set the date of its own city and the other weather attributes. 
	 * 
	 * @param jobj JSONObject to store the attributes.
	 * @param offset TimeZone, the time offset of the city. 
	 */
    @Override
    public void createFromJSON(JSONObject jobj, TimeZone offset) 
    {
		Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis((long)jobj.get("dt")*1000);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	sdf.setTimeZone(offset); 	 
     	this.date =sdf.format(calendar.getTime());
		JSONArray jsontemp = (JSONArray) jobj.get("weather");
		this.main_weather = (String) ((JSONObject) jsontemp.get(0)).get("main");
		this.description = (String) ((JSONObject) jsontemp.get(0)).get("description");
		for(int i = 1; i < jsontemp.size(); i++)
		{
			this.main_weather += " / " + (String) ((JSONObject) jsontemp.get(i)).get("main");
			this.description += " / " + (String) ((JSONObject) jsontemp.get(i)).get("description");
		}
    	this.humidity = (long) jobj.get("humidity");
    	this.clouds=(long) jobj.get("clouds");
    	this.wind_speed=Double.parseDouble(jobj.get("wind_speed") + "");
     	this.wind_deg=(long) jobj.get("wind_deg");
		try
		{
			if(((JSONObject) jobj.get("rain")) !=null ) 
				this.rain=Double.parseDouble(((JSONObject) jobj.get("rain")).get("1h") + "");
		}
		catch(ClassCastException e)
		{
			if(jobj.get("rain") !=null ) 
				this.rain=Double.parseDouble(jobj.get("rain") + "");
		} 
		try
		{
			if(((JSONObject) jobj.get("snow")) !=null ) 
				this.rain=Double.parseDouble(((JSONObject) jobj.get("snow")).get("1h") + "");
		}
		catch(ClassCastException e)
		{
			if(jobj.get("snow") !=null ) 
				this.rain=Double.parseDouble(jobj.get("snow") + "");
		}
     	if(jobj.get("pop")!=null)
     		this.pop_rain=Double.parseDouble(jobj.get("pop") + "");

		if(jobj.get("visibility") != null)
     		this.visibility=(long) jobj.get("visibility");
		else
			this.visibility = Long.MAX_VALUE;

		try
		{
     		this.temp_current=Double.parseDouble(jobj.get("temp") + "");
		}
		catch(Exception e)
		{
			this.temp_current = 1000; //value never registered on earth 
		}
		
		try
		{
			this.temp_feelslike=Double.parseDouble(jobj.get("feels_like") + "");
		}
		catch(Exception e)
		{
			this.temp_feelslike = 1000; //value never registered on earth 
		}
    }
    
    /**
	 * This method returns a JSON containing all the weather attributes. 
	 * 
	 * @return JSONObject
	 */
	@Override
    public JSONObject toJSON() 
    {
    	JSONObject att = new JSONObject();
		if(this.date != null)
    		att.put("date", this.date);
    	att.put("weather", this.main_weather);
		if(this.description != null)
    		att.put("weather descritpion", this.description);
    	att.put("humidity", this.humidity +  " %");
    	att.put("cloudiness", this.clouds +  " %");
    	att.put("wind speed", Math.round(this.wind_speed*100.0)/100.0  + " mi/h");
    	att.put("wind direction", this.wind_deg + "°");
    	att.put("precipitation volume", Math.round(this.rain*100.0)/100.0  + " mm");
    	att.put("snow volume", Math.round(this.snow*100.0)/100.0  + " mm");
		if(this.visibility != Long.MAX_VALUE)
    		att.put("visibility", this.visibility + " ft");
		if(this.temp_feelslike < 1000)
    		att.put("current temperature", Math.round(this.temp_current*100.0)/100.0 + " F");
		if(this.temp_feelslike < 1000)
    		att.put("feels like temperature", Math.round(this.temp_feelslike*100.0)/100.0 + " F");
    	att.put("probability percipitation", Math.round(this.pop_rain*100.0) + " %");
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

	public double getPop_rain() {
		return pop_rain;
	}
	public void setPop_rain(double pop_rain) {
		this.pop_rain = pop_rain;
	}  
}
