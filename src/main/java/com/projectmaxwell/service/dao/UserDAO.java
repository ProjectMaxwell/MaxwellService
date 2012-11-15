package com.projectmaxwell.service.dao;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.model.User;

public interface UserDAO {

	public User getUserById(String userId) throws WebApplicationException;
	
	public User[] getUsers() throws WebApplicationException;
	
	public User createUser(User user) throws WebApplicationException;

	public User updateUser(User user) throws WebApplicationException;
}
