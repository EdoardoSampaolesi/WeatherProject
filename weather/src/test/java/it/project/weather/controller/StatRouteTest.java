package it.project.weather.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatRouteTest 
{
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        try 
        {
            //mockMvc.perform(MockMvcRequestBuilders.get("/weather/add?cities=Chicago"));
        } 
        catch (Exception e) {}
    }

    @Test
    public void StatsTest() throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar today = Calendar.getInstance(), end, start;
        today.setTime(new Date());
        start = (Calendar) today.clone();
        end = (Calendar) today.clone();
        start.add(Calendar.DAY_OF_MONTH, -1);
        end.add(Calendar.DAY_OF_MONTH, -2);
        this.mockMvc.perform(
            get(
                "/stats?btdate=[" + sdf.format(start.getTime()) + "," + sdf.format(end.getTime()) + "]"
            )
            ).andExpect(status().isBadRequest())
             .andExpect(content().string(containsString("End must be after start!"))
        );
    }
}
