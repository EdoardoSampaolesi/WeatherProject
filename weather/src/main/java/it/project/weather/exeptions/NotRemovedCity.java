package it.project.weather.exeptions;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONObject;

/**
 * That class is a particular case of a CityNotFoundException, we don't remove if we aren't able to find the city
 */
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

    /** 
     * Same method used in CityNotFoundException, this just reuse it
     * 
     * @return JSONObject Json containing the error message for this exception and the specified city which is referred to
     */
    @Override
    public JSONObject getErrorJSONObject() {
        return super.getErrorJSONObject(NotRemovedCity.error);
    } 
}
