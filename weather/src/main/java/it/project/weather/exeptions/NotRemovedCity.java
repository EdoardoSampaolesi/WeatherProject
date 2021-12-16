package it.project.weather.exeptions;

import org.json.simple.JSONObject;

public class NotRemovedCity extends CityNotFoundException {

    private static final String error = "City not found in personal list of cities";

    public NotRemovedCity() 
    {
        super(NotRemovedCity.error);
    }

    public NotRemovedCity(String cityName) 
    {
        super(NotRemovedCity.error);
        super.city = cityName;
    }

    @Override
    public JSONObject getErrorJSONObject() {
        return super.getErrorJSONObject(NotRemovedCity.error);
    }
    
}
