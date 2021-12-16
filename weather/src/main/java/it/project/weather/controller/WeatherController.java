package it.project.weather.controller;

import java.util.Vector;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<String> addCity(@RequestBody String[] cities)
    {
        manager = new CitiesManagerCurrent();
        try {
            manager.add(Vector.class.cast(cities));
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<String> currentWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hourly")
    public ResponseEntity<String> hourlyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerHourly();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    @GetMapping("/daily")
    public ResponseEntity<String> dailyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerDaily();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    @GetMapping("/dayslot")
    public ResponseEntity<String> morningDayEveNightWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerMorDayEveNight();
        try
        {
            return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cities}")
    public ResponseEntity<String> deleteCity(@PathVariable(value = "cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        ResponseEntity<String> response;
        try
        {
            JSONObject jObjectRemovedError = manager.remove(Vector.class.cast(cities));
            if(jObjectRemovedError != null)
                response = new ResponseEntity<String>(HttpStatus.OK);
            else
                response = new ResponseEntity<String>(jObjectRemovedError.toJSONString(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e)
        {
            response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
 
}
