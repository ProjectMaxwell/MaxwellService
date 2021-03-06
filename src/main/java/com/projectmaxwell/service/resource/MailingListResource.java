package com.projectmaxwell.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.MailingListOld;
import com.projectmaxwell.service.dao.MailingListDAO;
import com.projectmaxwell.service.dao.impl.google.MailingListDAOImpl;

@Path("/mailingLists")
@Produces("application/json")
public class MailingListResource extends AbstractResource {

	MailingListDAO mailingListDAO;
	
	public MailingListResource(){
		super();
		mailingListDAO = new MailingListDAOImpl();
	}
	
	@GET
	@Path("/eac")
	public MailingListOld[] getAlumniMailingLists(){
		/*ArrayList<MailingList> lists = new ArrayList<MailingList>();
		MailingList ml = new MailingList();
		ml.setDescription("Fake Mailing List");
		ml.setEmailAddress("fakeList@fakedomain.com");
		lists.add(ml);
		
		MailingList ml2 = new MailingList();
		ml2.setDescription("Other Mailing List");
		ml2.setEmailAddress("fakeList2@fakedomain.com");
		lists.add(ml2);
		
		return lists.toArray(new MailingList[2]);*/
		
		return mailingListDAO.getMailingLists();
	}
	
	@GET
	@Path("/evergreenalumniclub/")
	public MailingListOld[] getAlumniMailingListsAlias(){
		return getAlumniMailingLists();
	}
	
	@GET
	@Path("/eac/{groupId}")
	public String[] getEmailAddressesByGroupId(@PathParam("groupId") String groupId){
		return mailingListDAO.getEmailsForMailingList(groupId);
	}
	
	@GET
	@Path("/evergreenalumniclub/{groupId}")
	public String[] getEmailAddressesByGroupIdAlias(@PathParam("groupId") String groupId){
		return getEmailAddressesByGroupId(groupId);
	}
	
	
}
