package it.project.weather.controller;

/**
 * @author @EdoardoSampaolesi
 */

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

    /** 
     * This method return an error messagge if the user try to access to a not existing route
     * 
     * @return ResponseEntity<String> the response containing the error message and a NOT FOUND HttpStatus
     */
    @GetMapping("/error")
    public ResponseEntity<String> errorMessage()
    {
        return new ResponseEntity<String>("Route not found!", HttpStatus.NOT_FOUND);
    }
  
    /** 
     * This method manage statistics requests. 
     * It takes as input an hours range and a dates range, than it returns ( unless error occurred ) 
     * a list of statistics for every cities in a personal list, already available to the program.
     * <p>
     * Statistics include max, min, average and variance for: humidity, cloudiness, temperature and visibility.
     * <p>
     * If the user send a request with bthour = [17:00:00,18:00:00] and btdate = [19/01/2022,21/01/2022], 
     * this method will set the startDate as 19/01/2022 17:00:00 and endDate as 21/01/2022 18:00:00;
     * remembering that dates and hours indicated are reffered to the city offset, so the statistics will be
     * generated for every city from 17:00 to 18:00 in the city
     * <p>
     * If not specified bthour or btdate parameters, default values are 00:00-23:59 for time, 
     * today -4 days for startDate and today +1 day for endDate
     * 
     * @param cities the list of cities to exclude from the statistic
     * @param bthour range of hours, written as [HH:mm:ss,HH:mm:ss] where the first hour is start and second is end
     * @param btdate range of dates, written as [dd/MM/yyyy,dd/MM/yyyy] where the first hour is start and second is end
     * @return ResponseEntity<String> the return is a JSON or an error message if an Exception occurred
     */

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
