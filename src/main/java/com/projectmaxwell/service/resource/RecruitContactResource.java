package com.projectmaxwell.service.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.projectmaxwell.model.RecruitContact;
import com.projectmaxwell.model.RecruitContactType;
import com.projectmaxwell.service.dao.RecruitContactDAO;
import com.projectmaxwell.service.dao.impl.mysql.RecruitContactDAOImpl;

@Consumes("application/json")
@Produces("application/json")
@Path("recruitContact")
public class RecruitContactResource extends AbstractResource {
	
	RecruitContactDAO recruitContactDAO;
	
	public RecruitContactResource(){
		super();
		recruitContactDAO = new RecruitContactDAOImpl();
	}
	
	@GET
	@Path("/recruitContactTypes")
	@RolesAllowed({"view_system_metadata"})
	public RecruitContactType[] getChapters(){
		return recruitContactDAO.getRecruitContactTypes();
	}
	
	@GET
	@RolesAllowed({"view_recruit_contact_history"})
	public RecruitContact[] getRecruitContactHistory(@QueryParam("recruitUserId") Integer recruitUserId, 
			@QueryParam("recruitContactorUserId") Integer recruitContactorUserId,
			@QueryParam("maxResults") Integer maxResults){
		
		return recruitContactDAO.getRecruitContactHistoryByParameters(recruitUserId, recruitContactorUserId, maxResults);
	}
	
	@POST
	@RolesAllowed({"record_recruit_contact"})
	public RecruitContact recordRecruitContact(RecruitContact recruitContact){
		return recruitContactDAO.recordRecruitContact(recruitContact); 
	}
}
