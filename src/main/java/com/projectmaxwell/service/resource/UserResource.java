package com.projectmaxwell.service.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.UserDAOImpl;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserDAO userDAO;
	
	public UserResource(){
		userDAO = new UserDAOImpl();
	}
	
	@GET
	@RolesAllowed({"read_user_list"})
	public User[] getUsers(){
		return userDAO.getUsers();
	}
	
	@GET
	@Path("/{userId}")
	public User getUserByUsername(@PathParam("userId") String userId){
		return userDAO.getUserById(userId);		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public User createUser(User user) throws WebApplicationException{
		return userDAO.createUser(user);
	}
	
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(User user) throws WebApplicationException{
		return userDAO.updateUser(user);
	}
}
