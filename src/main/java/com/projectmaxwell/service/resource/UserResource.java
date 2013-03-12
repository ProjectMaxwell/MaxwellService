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
import com.projectmaxwell.service.dao.RecruitDAO;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.impl.csv.MailingListBatchImportDAOImpl;
import com.projectmaxwell.service.dao.impl.mysql.RecruitDAOImpl;
import com.projectmaxwell.service.dao.impl.mysql.UserDAOImpl;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserDAO userDAO;
	private RecruitDAO recruitDAO;
	private HashMap<Integer,HashSet<MailingList>> mailingListMappings;
	private MailingListBatchImportDAOImpl mailingListBatchImportDAO;
	
	public UserResource(){
		userDAO = new UserDAOImpl();
		recruitDAO = new RecruitDAOImpl();
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
		
		//If user creation worked, see if we need to do special recruit info creation
		if(user.getUserTypeId() == 5){
			RecruitInfo recruitInfo = user.getRecruitInfo();
			if(recruitInfo == null){
				recruitInfo = new RecruitInfo();
				recruitInfo.setRecruitEngagementLevelId(1);
				recruitInfo.setRecruitSourceId(6);
			}
			recruitDAO.createRecruitInfo(recruitInfo, Integer.valueOf(user.getUserId()));
		}
		
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
	@RolesAllowed({"update_user_others"})
	public User updateUser(User user, @PathParam("userId") int userId) throws WebApplicationException{
		return userDAO.updateUser(user, userId);
	}
	
	@POST
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"CUD_recruit_info"})
	@Deprecated
	public RecruitInfo createRecruitInfo(@PathParam("userId") String userId, RecruitInfo recruitInfo){
		return recruitDAO.createRecruitInfo(recruitInfo, Integer.valueOf(userId));
	}
	
	@GET
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"view_recruit_info"})
	@Deprecated
	public RecruitInfo getRecruitInfoByUserId(@PathParam("userId") int userId){
		return recruitDAO.getRecruitInfoByUserId(userId);
	}
	
	@PUT
	@Path("/{userId}/recruitInfo")
	@RolesAllowed({"CUD_recruit_info"})
	@Deprecated
	public RecruitInfo updateRecruitInfo(@PathParam("userId") int userId, RecruitInfo recruitInfo){
		return recruitDAO.updateRecruitInfo(recruitInfo, userId);
	}
	
	@GET
	@Path("/userTypes")
	@RolesAllowed({"view_system_metadata"})
	public UserType[] getUserTypes(){
		return userDAO.getUserTypes();
	}
}
