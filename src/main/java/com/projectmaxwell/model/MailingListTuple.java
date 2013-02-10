package com.projectmaxwell.model;


public class MailingListTuple{
	private String mailingListId;
	private User[] users;
	
	public String getMailingListId() {
		return mailingListId;
	}
	
	public void setMailingListId(String key) {
		this.mailingListId = key;
	}
	
	public User[] getUsers() {
		return users;
	}
	
	public void setUsers(User[] value) {
		this.users = value;
	}

	public MailingListTuple(String mailingListId, User[] users) {
		this.mailingListId = mailingListId;
		this.users = users;
	}
}