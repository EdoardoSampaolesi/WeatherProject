package it.project.weather.model;

import it.project.weather.interfaces.Coord;

public class CoordImpl implements Coord {
	
	private double lat;
	private double lon;
	
	public CoordImpl(double lat, double lon)
	{
		this.lat=lat;
		this.lon=lon;
	}
	
	public String toString() {
		//lat={[(-)00.00]}&lon={[(-)00.00]}
		return "lat="+lat+"&lon="+lon;
	}

	public double getLat() 
	{
		return lat;
	}
	public void setLat(double lat) 
	{
		this.lat = lat;
	}

	public double getLon() 
	{
		return lon;
	}
	public void setLon(double lon) 
	{
		this.lon = lon;
	}
	

}
