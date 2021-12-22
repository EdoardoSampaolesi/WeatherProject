package it.project.weather.interfaces.statistics;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface Stats {
    public void createFromJSON(JSONArray jarray);
    public JSONObject toJSON();
}
