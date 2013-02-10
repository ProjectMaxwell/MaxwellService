package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.MailingListOld;
import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.User;

public interface MailingListDAO {

	public MailingListOld[] getMailingLists();
	
	public String[] getEmailsForMailingList(String groupId);
	
	public boolean addUserToMailingList(MailingList mailingList, User user);
}
