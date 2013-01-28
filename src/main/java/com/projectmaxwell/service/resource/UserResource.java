package com.projectmaxwell.service.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.model.User;
import com.projectmaxwell.model.UserType;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.impl.mysql.UserDAOImpl;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserDAO userDAO;
	
	public UserResource(){
		userDAO = new UserDAOImpl();
	}
	
	@GET
	public User[] getUsers(@QueryParam("userType") int userType){
		return userDAO.getUsers(userType);
	}
	
	@GET
	@Path("/{userId}")
	public User getUserByUsername(@PathParam("userId") int userId){
		return userDAO.getUserById(userId);		
	}
	
	@POST
	@RolesAllowed({"create_user"})
	public User createUser(User user) throws WebApplicationException{
		return userDAO.createUser(user);
	}
	
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(User user) throws WebApplicationException{
		return userDAO.updateUser(user);
	}
	
	@POST
	@Path("/{userId}/recruitInfo")
	public RecruitInfo createRecruitInfo(@PathParam("userId") String userId, RecruitInfo recruitInfo){
		RecruitInfo info = new RecruitInfo();
//		info.setUserId(recruitInfo.getUserId());
		info.setRecruitSourceId(recruitInfo.getRecruitSourceId());
		info.setRecruitEngagementLevelId(recruitInfo.getRecruitEngagementLevelId());
		return info;
	}
	
	@GET
	@Path("/{userId}/recruitInfo")
	public RecruitInfo getRecruitInfo(@PathParam("userId") String userId){
		RecruitInfo info = new RecruitInfo();
//		info.setUserId(1);
		info.setRecruitSourceId(1);
		info.setRecruitEngagementLevelId(1);
		return info;
	}
	
	@GET
	@Path("/userTypes")
	public UserType[] getUserTypes(){
		UserType[] userTypes = new UserType[5];
		UserType type1 = new UserType();
		type1.setUserTypeId(1);
		type1.setName("Associate");
		type1.setDescription("Sample description for associate.");
		userTypes[type1.getUserTypeId() - 1] = type1;
		UserType type2 = new UserType();
		type2.setUserTypeId(2);
		type2.setName("Initiate");
		userTypes[type2.getUserTypeId() - 1] = type2;
		UserType type3 = new UserType();
		type3.setUserTypeId(3);
		type3.setName("Alumnus");
		userTypes[type3.getUserTypeId() - 1] = type3;
		UserType type4 = new UserType();
		type4.setUserTypeId(4);
		type4.setName("Other");
		userTypes[type4.getUserTypeId() - 1] = type4;
		UserType type5 = new UserType();
		type5.setUserTypeId(5);
		type5.setName("Recruit");
		userTypes[type5.getUserTypeId() - 1] = type5;
		return userDAO.getUserTypes();
	}
}
