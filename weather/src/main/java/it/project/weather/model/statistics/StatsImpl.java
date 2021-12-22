package it.project.weather.model.statistics;

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

    @Override
    public void createFromJSON(JSONArray jarray)
    {
        double sum = 0,
        max = Double.MAX_VALUE,  // this correspond to maximum value for a double variable so every number will be greater
        min = Double.MIN_VALUE, // this correspond to minimum value for a double variable so every number will be lower
        val;
        for(int i = 0; i < jarray.size(); i++)
        {
            val = Integer.parseInt(
                (String) ((JSONObject) jarray.get(i)).get(this.statName)
                );
            sum += val;
            if(val > max)
                max = val;
            if(val < min)
                min = val;
        }
        this.max = max;
        this.min = min;
        this.av = sum/jarray.size();
        sum = 0;
        //variance
        for(int i = 0; i < jarray.size(); i++)
        {
            val = Integer.parseInt(
                (String) ((JSONObject) jarray.get(i)).get(this.statName)
                );
            val = val - this.av;
            sum += Math.pow(val, 2);
        }
        this.var = sum/jarray.size();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jobj = new JSONObject();
        jobj.put("max", this.max);
        jobj.put("min", this.max);
        jobj.put("variance", this.var);
        jobj.put("average", this.max);
        JSONObject returnObj = new JSONObject();
        returnObj.put(this.statName,jobj);
        return returnObj;
    }
    
}
