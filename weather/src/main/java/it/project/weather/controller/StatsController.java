package it.project.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController 
{
    @GetMapping("/stats")
    public String createStats(@RequestParam(value="example") String[] cities ) //incomplete method
    {
        return "example";
    }
    
}
