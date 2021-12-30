package it.project.weather.services;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONObject;

import it.project.weather.model.City;
import it.project.weather.model.ForecastMorDayEveNight;
import it.project.weather.utils.CitiesManagerImpl;
import it.project.weather.utils.Forecast;

/**
 * The cities manager specify the method getJSONString for getting daily weather informations grouped in dayslot, so it is used with ForecastDayMorEveNight
 */
public class CitiesManagerMorDayEveNight extends CitiesManagerImpl 
{
    /** 
     * Create a Json containing daily weather informations grouped in dayslot
     * 
     * @param city
     * @return JSONObject 
     * @throws Exception any exception is rethrown
     */
    @Override
    protected JSONObject getJSONString(City city) throws Exception
    {
        Forecast forecast = new ForecastMorDayEveNight(city);
        forecast.createFromJSON(wService);
        return forecast.toJSON();
    }
}
