package it.project.weather.exeptions;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherException;

public class DateOutOfRangeException extends Exception implements WeatherException{

    private static final String error = "Requested time is out of allowed range";

    public DateOutOfRangeException() 
    {
        super(DateOutOfRangeException.error);
    }

    @Override
    public JSONObject getErrorJSONObject()
    {
        return getErrorJSONObject(DateOutOfRangeException.error);
    }

    @Override
    public JSONObject getErrorJSONObject(String errorMessage)
    {
        JSONObject jObject = new JSONObject();
        jObject.put("error", errorMessage);
        return jObject;
    }
}
