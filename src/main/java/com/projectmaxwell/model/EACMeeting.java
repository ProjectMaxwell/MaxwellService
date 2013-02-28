package com.projectmaxwell.model;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EACMeeting {

	private int id;
	private String location;
	private String website;
	private String googleMaps;
	private long date;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String name) {
		this.location = name;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getGoogleMaps() {
		return googleMaps;
	}
	
	public void setGoogleMaps(String googleMaps) {
		this.googleMaps = googleMaps;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	
}
