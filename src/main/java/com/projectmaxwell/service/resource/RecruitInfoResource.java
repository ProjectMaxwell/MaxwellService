package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.RecruitEngagementLevel;
import com.projectmaxwell.service.dao.RecruitDAO;
import com.projectmaxwell.service.dao.impl.mysql.RecruitDAOImpl;

@Produces("application/json")
@Consumes("application/json")
@Path("/recruits")
public class RecruitInfoResource extends AbstractResource {

	RecruitDAO recruitDAO = new RecruitDAOImpl();
	
	@GET
	@Path("/recruitEngagementLevels")
	public RecruitEngagementLevel[] getRecruitEngagementLevels(){
		return recruitDAO.getRecruitEngagementLevels();
	}
}
