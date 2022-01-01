package it.project.weather.services;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.Forecast;
import it.project.weather.model.ForecastDaily;

/**
 * The cities manager specify the method getJSONString for getting daily weather, so it is used with ForecastDaily
 */
public class CitiesManagerDaily extends CitiesManagerImpl 
{
    /** 
     * Create a Json containing daily weather informations
     * 
     * @param city
     * @return JSONObject 
     * @throws Exception any exception is rethrown
     */
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastDaily(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
