package com.projectmaxwell.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dateOfBirth;
	private int yearInitiated;
	private int yearGraduated;
	private int pin;
	private int associateClassId;
	private String chapter;
	private int userStatus;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getYearInitiated() {
		return yearInitiated;
	}

	public void setYearInitiated(int yearInitiated) {
		this.yearInitiated = yearInitiated;
	}

	public int getYearGraduated() {
		return yearGraduated;
	}

	public void setYearGraduated(int yearGraduated) {
		this.yearGraduated = yearGraduated;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getAssociateClassId() {
		return associateClassId;
	}

	public void setAssociateClassId(int associateClassId) {
		this.associateClassId = associateClassId;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	
}
