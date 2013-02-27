package com.projectmaxwell.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class RecruitInfo {

//	private int userId;
	private Integer recruitSourceId;
	private Integer recruitEngagementLevelId;
	private int rushListUserId;
	private String classStanding;
	private String lifeExperiences;
	private String lookingFor;
	private String expectations;
	private String extracurriculars;
	private double gpa;
	private long dateAdded;
	
/*	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}*/
	
	public Integer getRecruitSourceId() {
		return recruitSourceId;
	}

	public void setRecruitSourceId(Integer recruitSourceId) {
		this.recruitSourceId = recruitSourceId;
	}
	
	public Integer getRecruitEngagementLevelId() {
		return recruitEngagementLevelId;
	}

	public void setRecruitEngagementLevelId(Integer recruitEngagementLevelId) {
		this.recruitEngagementLevelId = recruitEngagementLevelId;
	}
	
	public Integer getRushListUserId() {
		return rushListUserId;
	}

	public void setRushListUserId(Integer rushListUserId) {
		this.rushListUserId = rushListUserId;
	}

	public String getClassStanding() {
		return classStanding;
	}

	public void setClassStanding(String classStanding) {
		this.classStanding = classStanding;
	}

	public String getLifeExperiences() {
		return lifeExperiences;
	}

	public void setLifeExperiences(String lifeExperiences) {
		this.lifeExperiences = lifeExperiences;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getExpectations() {
		return expectations;
	}

	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}

	public String getExtracurriculars() {
		return extracurriculars;
	}

	public void setExtracurriculars(String extracurriculars) {
		this.extracurriculars = extracurriculars;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public Long getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Long dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
}
