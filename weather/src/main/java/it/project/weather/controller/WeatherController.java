package it.project.weather.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController 
{
    @PostMapping
    public String addCity(@RequestBody String[] city)
    {
        return "example";
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
