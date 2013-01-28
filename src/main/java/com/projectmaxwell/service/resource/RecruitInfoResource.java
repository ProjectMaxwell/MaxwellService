package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.RecruitInfo;

@Produces("application/json")
@Consumes("application/json")
public class RecruitInfoResource extends AbstractResource {

	@POST
	public RecruitInfo createRecruitInfo(@PathParam("userId") String userId, RecruitInfo recruitInfo){
		RecruitInfo info = new RecruitInfo();
//		info.setUserId(recruitInfo.getUserId());
		info.setRecruitSourceId(recruitInfo.getRecruitSourceId());
		info.setRecruitEngagementLevelId(recruitInfo.getRecruitEngagementLevelId());
		return info;
	}
	
	@GET
	public RecruitInfo getRecruitInfo(@PathParam("userId") String userId){
		RecruitInfo info = new RecruitInfo();
//		info.setUserId(1);
		info.setRecruitSourceId(1);
		info.setRecruitEngagementLevelId(1);
		return info;
	}
}
