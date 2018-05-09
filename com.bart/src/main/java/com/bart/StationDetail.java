package com.bart;

public class StationDetail {
	
	private String stationName, stationShortForm;
	
	public StationDetail(String name, String shortForm) {
		// TODO Auto-generated constructor stub
		this.stationName = name;
		this.stationShortForm=shortForm;
	}

	public String getStationName()
	{
		return this.stationName;
	}
	
	public String getstationShortForm()
	{
		return this.stationShortForm;
	}
	
	public void setStationName(String stationName)
	{
		this.stationName=stationName;
	}
	
	public void setStationShortForm(String stationShortForm)
	{
		this.stationShortForm=stationShortForm;
	}
	
	public String toString()
	{
		return this.stationName + " "+ this.stationShortForm;
	}
}
