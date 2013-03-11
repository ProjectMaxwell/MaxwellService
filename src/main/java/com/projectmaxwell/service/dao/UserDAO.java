package com.projectmaxwell.service.dao;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.model.User;
import com.projectmaxwell.model.UserType;

public interface UserDAO {

	public User getUserById(int userId) throws WebApplicationException;
	
	public User[] getUsers(int userType) throws WebApplicationException;
	
	public User createUser(User user) throws WebApplicationException;

	public User updateUser(User user, int userId) throws WebApplicationException;

	public RecruitInfo createRecruitInfo(RecruitInfo recruitInfo, int userId);
	
	public UserType[] getUserTypes();

	public RecruitInfo getRecruitInfoByUserId(int userId);
	
	public RecruitInfo updateRecruitInfo(RecruitInfo recruitInfo, int userId);
}
