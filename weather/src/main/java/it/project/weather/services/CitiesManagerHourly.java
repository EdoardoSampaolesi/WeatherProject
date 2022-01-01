package it.project.weather.services;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.Forecast;
import it.project.weather.model.ForecastHourly;

/**
 * The cities manager specify the method getJSONString for getting hourly weather, so it is used with ForecastHourly
 */
public class CitiesManagerHourly extends CitiesManagerImpl
{
    /** 
     * Create a Json containing hourly weather informations
     * 
     * @param city
     * @return JSONObject 
     * @throws Exception any exception is rethrown
     */
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastHourly(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
