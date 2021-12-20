package it.project.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController 
{
    @GetMapping("/stats")
    public ResponseEntity<String> createStats(@RequestParam(value="exclude",required = false) String[] cities,
                                              @RequestParam(value="bthour", required = false) String bthour,
                                              @RequestParam(value="btdate", required = false) String btdate)
    {
        //Date date = new Date();
        //if(bthour != null)
            
        return new ResponseEntity<String>("example",HttpStatus.OK);
    }
    
}
