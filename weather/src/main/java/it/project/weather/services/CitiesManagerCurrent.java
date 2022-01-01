package it.project.weather.services;

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.Forecast;
import it.project.weather.model.ForecastCurrent;

/**
 * The cities manager specify the method getJSONString for getting current weather, so it is used with ForecastCurrent
 */
public class CitiesManagerCurrent extends CitiesManagerImpl 
{
    
    /** 
     * Create a Json containing current weather informations
     * 
     * @param city
     * @return JSONObject 
     * @throws Exception any exception is rethrown
     */
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastCurrent(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
