package it.project.weather.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController 
{
    private final String DATEFORMAT = "dd/MM/yyyy";
    private final String HOURFORMAT = "HH:mm:ss";

    @GetMapping("/stats")
    public ResponseEntity<String> createStats(@RequestParam(value="exclude",required = false) String[] cities,
                                              @RequestParam(value="bthour", required = false) String bthour,
                                              @RequestParam(value="btdate", required = false) String btdate)
    {
        Date startDate,endDate,startTime,endTime;
        try
        {
            btdate.replaceAll(" ", "");
            startDate = new SimpleDateFormat("["+this.DATEFORMAT+",").parse(btdate);
            btdate.replace(
                btdate.subSequence(
                    0, 
                    btdate.indexOf(',', 0)
                    ),
                "");
            endDate = new SimpleDateFormat(","+this.DATEFORMAT+"]").parse(btdate);
            bthour.replaceAll(" ", "");
            startTime = new SimpleDateFormat("["+this.HOURFORMAT+",").parse(bthour);
            bthour.replace(
                bthour.subSequence(
                    0, 
                    bthour.indexOf(',', 0)
                    ),
                "");
            endTime = new SimpleDateFormat(","+this.HOURFORMAT+"]").parse(bthour);
            StatisticsFilter filter = new StatisticsFilter(cities,startDate,endDate,startTime,endTime);
            return new ResponseEntity<String>(filter.getWeather(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }           
    }
    
}
