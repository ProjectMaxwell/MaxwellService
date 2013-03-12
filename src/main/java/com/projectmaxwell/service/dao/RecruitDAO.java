package com.projectmaxwell.service.dao;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.model.RecruitComment;
import com.projectmaxwell.model.RecruitEngagementLevel;
import com.projectmaxwell.model.RecruitInfo;

public interface RecruitDAO {

	public RecruitEngagementLevel[] getRecruitEngagementLevels();

	RecruitInfo createRecruitInfo(RecruitInfo recruitInfo, int userId);

	RecruitInfo updateRecruitInfo(RecruitInfo recruitInfo, int userId);

	RecruitInfo getRecruitInfoByUserId(int userId)
			throws WebApplicationException;
	
	RecruitComment addRecruitComment(RecruitComment recruitComment, int userId);
	
	RecruitComment[] getRecruitCommentsByRecruitUserId(int userId);
	
}
