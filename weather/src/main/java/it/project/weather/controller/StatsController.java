package it.project.weather.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.project.weather.services.statistics.StatisticsFilter;

@RestController
public class StatsController 
{
    public static final String DATEFORMAT = "dd/MM/yyyy";
    public static final String HOURFORMAT = "HH:mm:ss";

    @GetMapping("/stats")
    public ResponseEntity<String> createStats(@RequestParam(value="exclude",required = false) String[] cities,
                                              @RequestParam(value="bthour", required = false) String bthour,
                                              @RequestParam(value="btdate", required = false) String btdate)
    {
        Calendar startDate = Calendar.getInstance(),endDate = Calendar.getInstance();
        try
        { 
            String dstart=null,dend = null, tstart = null, tend = null;
            if(bthour != null)
            {
                bthour.replaceAll(" ", "");
                tstart = bthour.substring(
                    bthour.indexOf("[") + 1
                    ,bthour.indexOf(",")
                    );
                tend = bthour.substring(
                    bthour.indexOf(",") + 1
                    ,bthour.indexOf("]")
                    );
                if(tend.equals(tstart))
                return new ResponseEntity<String>("Start time must be different from end time!",HttpStatus.BAD_REQUEST);
            }
            else
            {
                tstart = "00:00:00";
                tend = "23:59:59";
            }

            if(btdate!=null)
            {
                btdate.replaceAll(" ", "");
                dstart = btdate.substring(
                     btdate.indexOf("[") + 1
                    ,btdate.indexOf(",")
                    );
                dend = btdate.substring(
                    btdate.indexOf(",") + 1
                    ,btdate.indexOf("]")
                    );   
                dstart = dstart + " " + tstart;
                dend = dend + " " + tend;
                startDate.setTime(new SimpleDateFormat(this.DATEFORMAT + " " +this.HOURFORMAT).parse(dstart));   
                endDate.setTime(new SimpleDateFormat(this.DATEFORMAT + " " +this.HOURFORMAT).parse(dend)); 
                Calendar today = Calendar.getInstance(); 
                today.setTime(new Date());
                if((today.getTimeInMillis() - startDate.getTimeInMillis()) > 363599000) //363599000 corresponds to 5 days 4 hours 59 minutes and 59 seconds
                    return new ResponseEntity<String>("Time exceeded!",HttpStatus.BAD_REQUEST);   
                if((endDate.getTimeInMillis() - today.getTimeInMillis()) > 104399000) //104399000 corresponds to 2 days 4 hours 59 minutes and 59 seconds
                    return new ResponseEntity<String>("Time exceeded!",HttpStatus.BAD_REQUEST);   

            }
            else
            {
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(this.DATEFORMAT);
                startDate.setTime(
                    sdf.parse( sdf.format(today) + " " + tstart )
                    );
                startDate.add(Calendar.DAY_OF_MONTH, -4);
                endDate.setTime(
                    sdf.parse( sdf.format(today) + " " + tend )
                    );
                endDate.add(Calendar.DAY_OF_MONTH, +1);
            }
            if(startDate.before(endDate))
            {
                StatisticsFilter filter;
                if(cities != null)
                    filter = new StatisticsFilter(cities,startDate,endDate);
                else
                    filter = new StatisticsFilter(startDate,endDate); 
                return new ResponseEntity<String>(filter.getWeather(),HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<String>("End must be after start!",HttpStatus.BAD_REQUEST);

        }
        catch(ParseException e)
        {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }     
        catch(Exception e)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }      
    }
    
}
