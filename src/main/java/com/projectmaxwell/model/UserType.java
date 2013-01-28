package com.projectmaxwell.model;

public class UserType {
	
	private int userTypeId;
	private String name;
	private String description;
	
	public int getUserTypeId() {
		return userTypeId;
	}
	
	public void setUserTypeId(int id) {
		this.userTypeId = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
