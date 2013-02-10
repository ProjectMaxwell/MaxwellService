package com.projectmaxwell.service.dao.impl.sendloop;

import org.codehaus.jackson.annotate.JsonProperty;

public class SendloopResponse {
	private boolean success;
	private int errorCode;
	private String errorMessage;
	private Object[] errorFields;
	
	@JsonProperty("Success")
	public boolean isSuccess() {
		return success;
	}

	@JsonProperty("Success")
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JsonProperty("ErrorCode")
	public int getErrorCode() {
		return errorCode;
	}

	@JsonProperty("ErrorCode")
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@JsonProperty("ErrorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	@JsonProperty("ErrorMessage")
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@JsonProperty("ErrorFields")
	public Object[] getErrorFields() {
		return errorFields;
	}

	@JsonProperty("ErrorFields")
	public void setErrorFields(Object[] errorFields) {
		this.errorFields = errorFields;
	}
}
