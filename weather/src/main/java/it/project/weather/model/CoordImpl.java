package it.project.weather.model;

/**
 * @author @MatteoSeresi
 */

import it.project.weather.interfaces.Coord;

public class CoordImpl implements Coord 
{
	private double lat;
	private double lon;
	
	/**
	 * Constructor CoordImpl, it implements the coordinates.
	 * 
	 * @param lat latitude
	 * @param long longitude
	 */
	public CoordImpl(double lat, double lon)
	{
		this.lat=lat;
		this.lon=lon;
	}
	
	/**
	 * This method returns the latitude and longitude attributes. 
	 * 
	 * @return string that contains the latitude and longitude
	 */
	public String toString() {
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