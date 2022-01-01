package it.project.weather.exeptions;

import org.json.simple.JSONObject;

import it.project.weather.interfaces.WeatherException;

public class StatNotAvailableException extends Exception implements WeatherException
{
    private static final String error = "Statistics not available for date or time specified";
    protected String city = null;

    public StatNotAvailableException() 
    {
        super(StatNotAvailableException.error);
    }

    public StatNotAvailableException(String cityName) 
    {
        super(StatNotAvailableException.error);
        this.city = cityName;
    }
  
    /** 
     * Is just a call to main method, used in case there are no personalized messages
     * 
     * @return JSONObject Json taken from main method
     */
    @Override
    public JSONObject getErrorJSONObject()
    {
        return getErrorJSONObject(StatNotAvailableException.error);
    }
 
    /** 
     * This is the main method for generating a Json containing the error message for this exception and the specified city which is referred to
     * 
     * @param errorMessage string of the message to be put in the json
     * @return JSONObject  a Json containing the error message for this exception and the specified city which is referred to
     */
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
