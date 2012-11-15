package com.projectmaxwell.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class InvalidUserIdException extends WebApplicationException {

	protected static final String errorCode = "INVALID_USERID";
	
	public InvalidUserIdException(String errorId, String errorMessage) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(
				new MaxwellException(errorId, errorCode, errorMessage))
				.build()); 
		/*super(errorId, errorMessage);
		this.errorId = errorId;
		this.errorMessage = errorMessage;*/
	}
}
