package it.project.weather.controller;

import java.util.Vector;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.weather.services.CitiesManagerCurrent;
import it.project.weather.utils.CitiesManagerImpl;

@RestController
public class WeatherController 
{
    CitiesManagerImpl manager;

    @PostMapping
    public String addCity(@RequestBody String[] city)
    {
        manager = new CitiesManagerCurrent();
        manager.add(Vector.class.cast(city));
        return "successful operation!";
    }

    @GetMapping("/current")
    public String currentWeather(@RequestParam(value="cities") String[] cities)
    {
        String example = null;
        for (String city : cities) {
            example += city + " ";            
        }
        return example;
    }

    @GetMapping("/hourly")
    public String hourlyWeather(@RequestParam(value="cities") String[] cities)
    {
        String example = null;
        for (String city : cities) {
            example += city + " ";            
        }
        return example;
    }

    @GetMapping("/daily")
    public String dailyWeather(@RequestParam(value="cities") String[] cities)
    {
        String example = null;
        for (String city : cities) {
            example += city + " ";            
        }
        return example;
    }

    @GetMapping("/dayslot")
    public String morningDayEveNightWeather(@RequestParam(value="cities") String[] cities)
    {
        String example = null;
        for (String city : cities) {
            example += city + " ";            
        }
        return example;
    }

    @DeleteMapping
    public String deleteCity(@RequestBody String[] city)
    {
        return "example";
    }
 
}
