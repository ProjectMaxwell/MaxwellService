package com.projectmaxwell.model;

import java.util.Date;

public class AcademicTerm {

	private int academicTermId;
	private String name;
	private Date startDate;
	private Date endDate;
	
	public int getAcademicTermId() {
		return academicTermId;
	}
	
	public void setAcademicTermId(int academicTermId) {
		this.academicTermId = academicTermId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	
}
