package it.project.weather.exeptions;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherException;

public class CityNotFoundException extends Exception implements WeatherException{

    private static final String error = "City not found in OpenWeather cities database";
    private String city;

    public CityNotFoundException() 
    {
        super(error);
    }

    public CityNotFoundException(String cityName) 
    {
        super();
        this.city = cityName;
    }

    @Override
    public JSONObject getErrorJSONObject()
    {
        JSONObject jObject = new JSONObject();
        jObject.put("error", error);
        jObject.put("city", city);
        return jObject;
    }
}
