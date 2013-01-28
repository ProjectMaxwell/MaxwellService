package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.projectmaxwell.model.EACMeeting;
import com.projectmaxwell.service.dao.EACMeetingResponseDAO;
import com.projectmaxwell.service.dao.impl.mysql.EACMeetingResponseDAOImpl;

@Path("/EAC")
@Produces("application/json")
@Consumes("application/json")
public class EACResource extends AbstractResource {

	EACMeetingResponseDAO meetingResponseDAO;
	
	public EACResource(){
		meetingResponseDAO = new EACMeetingResponseDAOImpl();
	}
	
	@GET
	@Path("/meet-ups/next")
	public EACMeeting getNextEACMeeting(){
		EACMeeting meeting = meetingResponseDAO.getNextEACMeeting();
		return meeting;
	}
	
	@POST
	@Path("/meet-ups")
	public EACMeeting createEACMeeting(EACMeeting meeting){
		return meetingResponseDAO.createEACMeeting(meeting);
	}
	
	@GET
	@Path("/meet-ups")
	public EACMeeting[] getEACMeetings(@QueryParam("numRows") int numRows){
		System.out.println(numRows);
		return meetingResponseDAO.getEACMeetings(numRows);
	}
}
