package com.projectmaxwell.service.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.RecruitComment;
import com.projectmaxwell.model.RecruitEngagementLevel;
import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.service.dao.RecruitDAO;
import com.projectmaxwell.service.dao.impl.mysql.RecruitDAOImpl;

@Produces("application/json")
@Consumes("application/json")
@Path("/recruits")
public class RecruitResource extends AbstractResource {

	RecruitDAO recruitDAO = new RecruitDAOImpl();
	
	@GET
	@Path("/recruitEngagementLevels")
	@RolesAllowed({"view_system_metadata"})
	public RecruitEngagementLevel[] getRecruitEngagementLevels(){
		return recruitDAO.getRecruitEngagementLevels();
	}
	
	@POST
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"CUD_recruit_info"})
	public RecruitInfo createRecruitInfo(@PathParam("userId") String userId, RecruitInfo recruitInfo){
		return recruitDAO.createRecruitInfo(recruitInfo, Integer.valueOf(userId));
	}
	
	@GET
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"view_recruit_info"})
	public RecruitInfo getRecruitInfoByUserId(@PathParam("userId") int userId){
		return recruitDAO.getRecruitInfoByUserId(userId);
	}
	
	@PUT
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"CUD_recruit_info"})
	public RecruitInfo updateRecruitInfo(@PathParam("userId") int userId, RecruitInfo recruitInfo){
		return recruitDAO.updateRecruitInfo(recruitInfo, userId);
	}
	
	@POST
	@Path("/{userId}/recruitComments")
	public RecruitComment addRecruitComment(@PathParam("userId") int userId, RecruitComment recruitComment){
		return recruitDAO.addRecruitComment(recruitComment, userId);
	}
	
	@GET
	@Path("/{userId}/recruitComments")
	public RecruitComment[] getRecruitCommentsByUserId(@PathParam("userId") int userId){
		return recruitDAO.getRecruitCommentsByRecruitUserId(userId);
	}
	
}
