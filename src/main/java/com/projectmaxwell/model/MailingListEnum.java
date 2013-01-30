package com.projectmaxwell.model;

public enum MailingListEnum {
	EVERGREEN_ALUMNI_CLUB("Mailing list for all alumni in the Seattle area"),
	ALPHA_PI_ALUMNI("Mailing list for all alumni of the Alpha Pi chapter");
	
	private String description;
	
	private MailingListEnum(String s){
		description = s;
	}

	public String getDescription() {
		return description;
	}
}
