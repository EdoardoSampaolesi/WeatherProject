package it.project.weather.controller;

import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.weather.services.CitiesManagerCurrent;
import it.project.weather.services.CitiesManagerDaily;
import it.project.weather.services.CitiesManagerHourly;
import it.project.weather.services.CitiesManagerMorDayEveNight;
import it.project.weather.utils.CitiesManagerImpl;

@RestController
@RequestMapping("/weather")
public class WeatherController 
{
    CitiesManagerImpl manager;

    /** 
     * This method let users to add cities to a personal city list
     * 
     * @param cities list of cities name
     * @return ResponseEntity<String> status of excecution and, if occurred, an error message in JSON format
     */
    @GetMapping("/add")
    public ResponseEntity<String> addCity(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        ResponseEntity<String> response;
        try 
        {
            JSONArray jObjectAddedError = manager.add(cities);
            response = new ResponseEntity<String>(jObjectAddedError.toJSONString(),HttpStatus.OK);

        } 
        catch (Exception e) 
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    
    /** 
     * Method used to get current weather, it returns a JSONOnject with main weather informations
     * 
     * @param cities list of cities for which wheather is required
     * @return ResponseEntity<String> response data in Json format, it can also return an INTERNAL_SERVER_ERROR HttpStatus if exceptions occurred
     */
    @GetMapping("/current")
    public ResponseEntity<String> currentWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(cities),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /** 
     * Method used to get hourly weather, it returns a JSONArray with main weather informations.
     * <p> 
     * Hourly means we return a list of 24 weather from now to 24 hours forward
     * 
     * @param cities list of cities for which wheather is required
     * @return ResponseEntity<String> response data in Json format, it can also return an INTERNAL_SERVER_ERROR HttpStatus if exceptions occurred
     */
    @GetMapping("/hourly")
    public ResponseEntity<String> hourlyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerHourly();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(cities),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    
    /** 
     * Method used to get daily weather, it returns a JSONOnject with main weather informations
     * <p> 
     * Daily means we return a list of 7 weather for each day, from today to 7 days forward
     * 
     * @param cities list of cities for which wheather is required
     * @return ResponseEntity<String> response data in Json format, it can also return an INTERNAL_SERVER_ERROR HttpStatus if exceptions occurred
     */
    @GetMapping("/daily")
    public ResponseEntity<String> dailyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerDaily();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(cities),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    
    /** 
     * Method used to get daily weather, it returns a JSONOnject with main weather informations
     * <p> 
     * Daily means we return a list of 4 weather, one for each today's dayslot (night, morning, afternoon and evening), 
     * so that route provied a summary of today's weather informations grouped by dayslot
     * 
     * @param cities list of cities for which wheather is required
     * @return ResponseEntity<String> response data in Json format, it can also return an INTERNAL_SERVER_ERROR HttpStatus if exceptions occurred
     */
    @GetMapping("/dayslot")
    public ResponseEntity<String> morningDayEveNightWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerMorDayEveNight();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(cities),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /** 
     * This method let users to remove cities from the personal city list
     * 
     * @param cities list of cities name
     * @return ResponseEntity<String> status of excecution and, if occurred, an error message in JSON format
     */
    @GetMapping("/remove")
    public ResponseEntity<String> deleteCity(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        ResponseEntity<String> response;
        try
        {
            JSONArray jObjectRemovedError = manager.remove(cities);
            response = new ResponseEntity<String>(jObjectRemovedError.toJSONString(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
 
}
