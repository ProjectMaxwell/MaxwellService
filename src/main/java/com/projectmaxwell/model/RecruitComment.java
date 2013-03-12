package com.projectmaxwell.model;

public class RecruitComment {

	private int recruitCommentId;
	private int recruitUserId;
	private int commenterUserId;
	private int dateCreated;
	private String comment;
	
	public int getRecruitCommentId() {
		return recruitCommentId;
	}

	public void setRecruitCommentId(int recruitCommentId) {
		this.recruitCommentId = recruitCommentId;
	}

	public int getRecruitUserId() {
		return recruitUserId;
	}

	public void setRecruitUserId(int recruitUserId) {
		this.recruitUserId = recruitUserId;
	}

	public int getCommenterUserId() {
		return commenterUserId;
	}

	public void setCommenterUserId(int commenterUserId) {
		this.commenterUserId = commenterUserId;
	}

	public int getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(int dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
