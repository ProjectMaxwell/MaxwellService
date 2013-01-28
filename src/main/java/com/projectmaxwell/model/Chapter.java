package com.projectmaxwell.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include=Inclusion.NON_DEFAULT)
public class Chapter {

	private int chapterId;
	private String name;
	private String school;
	private Boolean active;
	
	public int getChapterId() {
		return chapterId;
	}
	
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
