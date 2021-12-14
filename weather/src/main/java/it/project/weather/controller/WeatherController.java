package it.project.weather.controller;

import java.util.Vector;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.weather.services.CitiesManagerCurrent;
import it.project.weather.services.CitiesManagerDaily;
import it.project.weather.services.CitiesManagerHourly;
import it.project.weather.services.CitiesManagerMorDayEveNight;
import it.project.weather.utils.CitiesManagerImpl;

@RestController
public class WeatherController 
{
    CitiesManagerImpl manager;

    @PostMapping
    public ResponseEntity<String> addCity(@RequestBody String[] cities)
    {
        manager = new CitiesManagerCurrent();
        manager.add(Vector.class.cast(cities));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<String> currentWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
    }

    @GetMapping("/hourly")
    public ResponseEntity<String> hourlyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerHourly();
        return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<String> dailyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerDaily();
        return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
    }

    @GetMapping("/dayslot")
    public ResponseEntity<String> morningDayEveNightWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerMorDayEveNight();
        return new ResponseEntity<String>(manager.getWeather(Vector.class.cast(cities)),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCity(@RequestBody String[] cities)
    {
        manager = new CitiesManagerCurrent();
        return new ResponseEntity<String>(manager.remove(Vector.class.cast(cities)),HttpStatus.OK);
    }
 
}
