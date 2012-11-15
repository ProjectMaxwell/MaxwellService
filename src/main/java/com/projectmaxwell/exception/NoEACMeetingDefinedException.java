package com.projectmaxwell.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class NoEACMeetingDefinedException extends WebApplicationException {

	protected static final String errorCode = "EAC_MEETING_MISSING";
	
	public NoEACMeetingDefinedException(String errorId, String errorMessage) {
		super(Response.status(Response.Status.NOT_FOUND).entity(
				new MaxwellException(errorId, errorCode, errorMessage))
				.build()); 
		
	}
}
