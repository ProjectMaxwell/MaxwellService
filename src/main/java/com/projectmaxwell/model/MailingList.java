package com.projectmaxwell.model;

public enum MailingList {
	SENDLOOP_EVERGREEN_ALUMNI_CLUB("Mailing list for all alumni in the Seattle area", "2"),
	SENDLOOP_ALPHA_PI_ALUMNI("Mailing list for all alumni of the Alpha Pi chapter", "3");
	
	private String description;
	private String id;

	private MailingList(String s, String id ){
		description = s;
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	
	public String getId() {
		return id;
	}
}
