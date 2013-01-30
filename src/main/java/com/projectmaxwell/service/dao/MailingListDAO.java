package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.MailingListEnum;

public interface MailingListDAO {

	public MailingList[] getMailingLists();
	
	public String[] getEmailsForMailingList(String groupId);
	
	public boolean addUserToMailingList(MailingListEnum mailingList);
}
