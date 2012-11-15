package com.projectmaxwell.model;

public class Office {
	
	private int officeId;
	private String name;
	private int housepoints;
	private boolean active;
	
	public int getOfficeId() {
		return officeId;
	}
	
	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHousepoints() {
		return housepoints;
	}
	
	public void setHousepoints(int housepoints) {
		this.housepoints = housepoints;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
