package com.projectmaxwell.service.dao.impl.sendloop;

import com.projectmaxwell.model.MailingListOld;
import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.MailingListDAO;

public class MailingListDAOImpl extends AbstractSendloopDAOImpl implements MailingListDAO {

	@Override
	public MailingListOld[] getMailingLists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEmailsForMailingList(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUserToMailingList(MailingList mailingList, User user) {
		client.importUserToList(user, mailingList.getId());
		return false;
	}

}
