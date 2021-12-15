package it.project.weather.exeptions;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherException;

public class CityNotFoundException extends Exception implements WeatherException{

    private static final String error = "City not found in OpenWeather cities database";
    protected String city = null;

    public CityNotFoundException() 
    {
        super(CityNotFoundException.error);
    }

    public CityNotFoundException(String cityName) 
    {
        super(CityNotFoundException.error);
        this.city = cityName;
    }

    @Override
    public JSONObject getErrorJSONObject()
    {
        return getErrorJSONObject(CityNotFoundException.error);
    }

    @Override
    public JSONObject getErrorJSONObject(String errorMessage)
    {
        JSONObject jObject = new JSONObject();
        jObject.put("error", errorMessage);
        if(this.city != null)
            jObject.put("city", this.city);
        return jObject;
    }
}
