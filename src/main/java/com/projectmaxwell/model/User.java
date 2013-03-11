package com.projectmaxwell.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class User {
	
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dateOfBirth;
	private Integer yearInitiated;
	private Integer yearGraduated;
	private Integer pin;
	private Integer associateClassId;
	private int chapterId;
	private Integer userTypeId;
	private Integer referredById;
	private String highschool;
	private String phoneNumber;
	private String facebookId;
	private String linkedInId;
	private String twitterId;
	private String googleAccountId;
	private RecruitInfo recruitInfo;
	private Integer dateCreated;
	private Integer dateModified;
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
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

	public Integer getYearInitiated() {
		return yearInitiated;
	}

	public void setYearInitiated(Integer yearInitiated) {
		this.yearInitiated = yearInitiated;
	}

	public Integer getYearGraduated() {
		return yearGraduated;
	}

	public void setYearGraduated(Integer yearGraduated) {
		this.yearGraduated = yearGraduated;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public Integer getAssociateClassId() {
		return associateClassId;
	}

	public void setAssociateClassId(int associateClassId) {
		this.associateClassId = associateClassId;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapter) {
		this.chapterId = chapter;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userStatus) {
		this.userTypeId = userStatus;
	}

	public Integer getReferredById() {
		return referredById;
	}

	public void setReferredById(Integer referredById) {
		this.referredById = referredById;
	}

	public String getHighschool() {
		return highschool;
	}

	public void setHighschool(String highschool) {
		this.highschool = highschool;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getLinkedInId() {
		return linkedInId;
	}

	public void setLinkedInId(String linkedInId) {
		this.linkedInId = linkedInId;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public Integer getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Integer dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getDateModified() {
		return dateModified;
	}

	public void setDateModified(Integer dateModified) {
		this.dateModified = dateModified;
	}
	
	
}
