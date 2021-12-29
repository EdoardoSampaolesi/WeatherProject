package it.project.weather.exeptions;

import org.json.simple.JSONObject;

public class CityNotAddedException extends CityNotFoundException {

    private static final String error = "Already exists a city with that name in the list";

    public CityNotAddedException() 
    {
        super(CityNotAddedException.error);
    }

    public CityNotAddedException(String cityName) 
    {
        super(CityNotAddedException.error);
        super.city = cityName;
    }

    @Override
    public JSONObject getErrorJSONObject() {
        return super.getErrorJSONObject(CityNotAddedException.error);
    }
    
}
