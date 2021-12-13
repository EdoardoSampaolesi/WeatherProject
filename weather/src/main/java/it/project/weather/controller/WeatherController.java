package it.project.weather.controller;

import java.util.Vector;

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
    public String addCity(@RequestBody String[] cities)
    {
        manager = new CitiesManagerCurrent();
        manager.add(Vector.class.cast(cities));
        return "successful operation!";
    }

    @GetMapping("/current")
    public String currentWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerCurrent();
        return manager.getWeather(Vector.class.cast(cities));
    }

    @GetMapping("/hourly")
    public String hourlyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerHourly();
        return manager.getWeather(Vector.class.cast(cities));
    }

    @GetMapping("/daily")
    public String dailyWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerDaily();
        return manager.getWeather(Vector.class.cast(cities));
    }

    @GetMapping("/dayslot")
    public String morningDayEveNightWeather(@RequestParam(value="cities") String[] cities)
    {
        manager = new CitiesManagerMorDayEveNight();
        return manager.getWeather(Vector.class.cast(cities));
    }

    @DeleteMapping
    public String deleteCity(@RequestBody String[] cities)
    {
        manager = new CitiesManagerCurrent();
        return manager.remove(Vector.class.cast(cities));
    }
 
}
