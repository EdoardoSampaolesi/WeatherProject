package it.project.weather.exeptions;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONObject;

/**
 * That class is a particular case of a CityNotFoundException
 */
public class CityNotAddedException extends CityNotFoundException 
{
    private static final String error = "Already exists a city with that name in the list";

    public CityNotAddedException() 
    {
        super(null,CityNotAddedException.error);
    }

    public CityNotAddedException(String cityName) 
    {
        super(cityName,CityNotAddedException.error);
    }

    /** 
     * Same method used in CityNotFoundException, this just reuse it
     * 
     * @return JSONObject Json containing the error message for this exception and the specified city which is referred to
     */
    @Override
    public JSONObject getErrorJSONObject() {
        return super.getErrorJSONObject(CityNotAddedException.error);
    }
    
}
