package it.project.weather.model.statistics;

/**
 * @author @EdoardoSampaolesi
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.project.weather.interfaces.statistics.Stats;

public class StatsImpl implements Stats {

    protected double max,min,var,av;
    protected String statName = null;

    public StatsImpl(double max, double min, double var, double av,String statName) {
        this.max = max;
        this.min = min;
        this.var = var;
        this.av = av;
        this.statName = statName;
    }

    public StatsImpl(String statName) {
        this(0,0,0,0,statName);
    }
   
    /** 
     * this method calculates maximum, minimum, average and variance values for the field specified as statName
     * 
     * @param jarray an array containing hourly weather informations
     */
    @Override
    public void createFromJSON(JSONArray jarray)
    {
        double sum = 0, value = 0,
        max = Double.MIN_VALUE,  // this correspond to minimum value for a double variable so every number will be greater
        min = Double.MAX_VALUE; // this correspond to maximum value for a double variable so every number will be lower
        for(int i = 0; i < jarray.size(); i++)
        {
            value = Double.parseDouble(((JSONObject) jarray.get(i)).get(this.statName) + "");
            sum += value;
            if(value > max)
                max = value;
            if(value < min)
                min = value;
        }
        this.max = max;
        this.min = min;
        this.av = sum/jarray.size();
        sum = 0;
        //variance
        for(int i = 0; i < jarray.size(); i++)
        {
            value = Double.parseDouble(((JSONObject) jarray.get(i)).get(this.statName) + "");
            value = value - this.av;
            sum += Math.pow(value, 2);
        }
        this.var = sum/jarray.size();
    }

    
    /** 
     * Create a Json containing max, min, average and variance values
     * 
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        JSONObject jobj = new JSONObject();
        jobj.put("max", Math.round(this.max*100.0)/100.0);
        jobj.put("min", Math.round(this.min*100.0)/100.0);
        jobj.put("variance", Math.round(this.var*100.0)/100.0);
        jobj.put("average", Math.round(this.av*100.0)/100.0);
        JSONObject returnObj = new JSONObject();
        returnObj.put(this.statName,jobj);
        return returnObj;
    }
    
}
