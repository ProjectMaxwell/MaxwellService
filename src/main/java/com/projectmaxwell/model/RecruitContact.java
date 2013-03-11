package com.projectmaxwell.model;

public class RecruitContact {

	private int recruitContactId;
	private int recruitUserId;
	private int recruitContactorUserId;
	private int recruitContactTypeId;
	private int contactTimestamp;
	private String notes;

	public int getRecruitContactId() {
		return recruitContactId;
	}

	public void setRecruitContactId(int recruitContactId) {
		this.recruitContactId = recruitContactId;
	}

	public int getRecruitUserId() {
		return recruitUserId;
	}

	public void setRecruitUserId(int recruitUserId) {
		this.recruitUserId = recruitUserId;
	}

	public int getRecruitContactorUserId() {
		return recruitContactorUserId;
	}

	public void setRecruitContactorUserId(int recruitContactorUserId) {
		this.recruitContactorUserId = recruitContactorUserId;
	}

	public int getRecruitContactTypeId() {
		return recruitContactTypeId;
	}

	public void setRecruitContactTypeId(int recruitContactTypeId) {
		this.recruitContactTypeId = recruitContactTypeId;
	}

	public int getContactTimestamp() {
		return contactTimestamp;
	}

	public void setContactTimestamp(int contactTimestamp) {
		this.contactTimestamp = contactTimestamp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
