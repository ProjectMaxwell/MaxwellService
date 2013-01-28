package com.projectmaxwell.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EACMeeting {

	private int id;
	private String name;
	private String website;
	private String googleMaps;
	private int date;
	private Calendar calendar;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("location")
	public String getName() {
		return name;
	}
	
	@JsonProperty("location")
	public void setName(String name) {
		this.name = name;
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
	
	@JsonIgnore
	public int getDate() {
		return date;
	}
	
	@JsonProperty("date")
	public void setDate(int date) {
		this.date = date;
	}
	
	@JsonProperty("date")
	public String getCalendarAsString(){
		if(calendar == null){
			return null;
		}
		DateFormat df = new SimpleDateFormat("EEEE, MMMM dd hh:mm a");
		return df.format(calendar.getTime());
	}
	
	@JsonIgnore
	public Calendar getCalendar(){
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	
}
