package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.RecruitContact;
import com.projectmaxwell.model.RecruitContactType;

public interface RecruitContactDAO {

	public RecruitContactType[] getRecruitContactTypes();

	public RecruitContact[] getRecruitContactHistoryByParameters(
			Integer recruitUserId, Integer recruitContactorUserId,
			Integer maxResults);
	
	public RecruitContact recordRecruitContact(RecruitContact recruitContact);
	
}
