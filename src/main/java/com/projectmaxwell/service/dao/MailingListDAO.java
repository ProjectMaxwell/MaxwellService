package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.MailingList;

public interface MailingListDAO {

	public MailingList[] getMailingLists();
	
	public String[] getEmailsForMailingList(String groupId);
}
