package com.projectmaxwell.service.resource;

import java.util.HashMap;
import java.util.HashSet;

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

import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.model.User;
import com.projectmaxwell.model.UserType;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.impl.csv.MailingListBatchImportDAOImpl;
import com.projectmaxwell.service.dao.impl.mysql.UserDAOImpl;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserDAO userDAO;
	private HashMap<Integer,HashSet<MailingList>> mailingListMappings;
	private MailingListBatchImportDAOImpl mailingListBatchImportDAO;
	
	public UserResource(){
		userDAO = new UserDAOImpl();
		mailingListMappings = getMailingListMappings();
		mailingListBatchImportDAO = MailingListBatchImportDAOImpl.getInstance();
	}
	
	protected final HashMap<Integer,HashSet<MailingList>> getMailingListMappings(){
		HashMap<Integer,HashSet<MailingList>> map = new HashMap<Integer,HashSet<MailingList>>();
		HashSet<MailingList> associateMailingLists = new HashSet<MailingList>();
		HashSet<MailingList> initiateMailingLists = new HashSet<MailingList>();
		HashSet<MailingList> alumnusMailingLists = new HashSet<MailingList>();
		alumnusMailingLists.add(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB);
		alumnusMailingLists.add(MailingList.SENDLOOP_ALPHA_PI_ALUMNI);
		HashSet<MailingList> otherMailingLists = new HashSet<MailingList>();
		HashSet<MailingList> recruitMailingLists = new HashSet<MailingList>();
		map.put(1, associateMailingLists);
		map.put(2, initiateMailingLists);
		map.put(3, alumnusMailingLists);
		map.put(4, otherMailingLists);
		map.put(5, recruitMailingLists);
		return map;
	}
	
	@GET
	public User[] getUsers(@QueryParam("userType") int userType){
		return userDAO.getUsers(userType);
	}
	
	@GET
	@Path("/{userId}")
	public User getUserById(@PathParam("userId") int userId){
		return userDAO.getUserById(userId);		
	}
	
	@POST
	@RolesAllowed({"create_user"})
	public User createUser(User user) throws WebApplicationException{
		User returnedUser = userDAO.createUser(user);
		//If user creation worked, now add that user to the appropriate mailing lists!
		if(returnedUser != null && user.getUserTypeId() != null && user.getEmail() != null){
			//Batch Importer not yet setup to do this on a per mailing list basis
			HashSet<MailingList> mailingLists = mailingListMappings.get(user.getUserTypeId());
			for(MailingList list : mailingLists){
				mailingListBatchImportDAO.writeToQueue(user, list.getId());
				System.out.println("Adding user '" + user.getUserId() + "' to mailing list '" + list.name() + "'.");
			}
			/*			HashSet<MailingList> mailingLists = mailingListMappings.get(user.getUserTypeId());
			if(mailingLists != null && mailingLists.size() > 0){
				for(MailingList mailingList : mailingLists){
					System.out.println("Adding user '" + user.getUserId() + "' to mailing list '" + mailingList.name() + "'.");
					mailingListDAO.addUserToMailingList(mailingList, returnedUser);
				}
			}*/
		}
		return returnedUser;
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
		info.setRecruitSourceId(recruitInfo.getRecruitSourceId());
		info.setRecruitEngagementLevelId(recruitInfo.getRecruitEngagementLevelId());
		return info;
	}
	
	@GET
	@Path("/{userId}/recruitInfo")
	public RecruitInfo getRecruitInfoByUserId(@PathParam("userId") int userId){
		return userDAO.getRecruitInfoByUserId(userId);
	}
	
	@GET
	@Path("/userTypes")
	public UserType[] getUserTypes(){
		return userDAO.getUserTypes();
	}
}
