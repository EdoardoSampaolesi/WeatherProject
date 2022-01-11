package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import java.util.TimeZone;

import org.json.simple.JSONObject;

public class WeatherComplete extends Weather {
	
	private double temp_max;
	private double temp_min;
	
	/**
	 * Constructor WeateherComplete that extends Weather.
	 * 
	 * @param temp_max, max temperature
	 * @param temp_min, min temperature
	 */
	public WeatherComplete(double temp_max, double temp_min, String date, String main_weather, String description, long humidity, long clouds, double wind_speed, long wind_deg, double rain, double snow, long visibility, double temp_current, double temp_feelslike, long pop_rain) 
	{
		super(date, main_weather, description, humidity, clouds, wind_speed, wind_deg, rain, snow, visibility, temp_current, temp_feelslike, pop_rain);
		this.temp_max=temp_max;
		this.temp_min=temp_min;
	}
	
	public WeatherComplete(double temp_max, double temp_min) 
	{
		super(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		this.temp_max=temp_max;
		this.temp_min=temp_min;
	}
	
	public WeatherComplete() 
	{
		this(0,0);
	}
	
	/**
	 * This method set max and minimum temperature. 
	 * 
	 * @param jobj JSONObject to store the attributes.
	 * @param offset TimeZone, the time offset of the city. 
	 */
	 public void createFromJSON(JSONObject jobj, TimeZone offset) 
	 {
		 super.createFromJSON(jobj, offset);
		 double temp_max=Double.parseDouble(((JSONObject) jobj.get("temp")).get("max") + "");
	     this.temp_max = temp_max;
	     double temp_min=Double.parseDouble(((JSONObject) jobj.get("temp")).get("min") + "");
	     this.temp_min = temp_min;
	 }

	 /**
		 * This method returns a JSON containing the max and minimum temperature. 
		 * 
		 * @return JSONObject
		 */
	 public JSONObject toJSON() 
	 {
		 JSONObject att = super.toJSON();
		 att.put("max temperature", this.temp_max + " F");
		 att.put("min temperature", this.temp_min + " F");
		 return att;
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