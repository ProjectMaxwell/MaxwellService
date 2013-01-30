package com.projectmaxwell.service.dao.impl.google;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.data.appsforyourdomain.generic.GenericFeed;
import com.projectmaxwell.exception.GoogleAppsDAOException;
import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.MailingListEnum;
import com.projectmaxwell.service.dao.MailingListDAO;

public class MailingListDAOImpl extends AbstractGoogleDAOImpl implements MailingListDAO {

	@Override
	public MailingList[] getMailingLists() {
		ArrayList<MailingList> lists = new ArrayList<MailingList>();
		GenericFeed feed;
		try {
			feed = groups.retrieveAllGroups();
		} catch (Exception e) {
			throw new GoogleAppsDAOException(String.valueOf(Math.random()),"Could not retrieve groups due to exception.  " + e.getMessage());
		}
		List<GenericEntry> entries = feed.getEntries();
		for(GenericEntry e : entries){ 
			String s = e.getProperty("groupId");
			System.out.println(s);
			MailingList mailingList = new MailingList();
			mailingList.setDescription(e.getProperty("description"));
			mailingList.setEmailAddress(e.getProperty("groupId"));
			lists.add(mailingList);
		}
		return lists.toArray(new MailingList[lists.size()]);
	}

	@Override
	public String[] getEmailsForMailingList(String groupId) {
		HashSet<String> emails = new HashSet<String>();
		GenericFeed feed;
		try {
			feed = groups.retrieveAllMembers(groupId);
		} catch (Exception e) {
			throw new GoogleAppsDAOException(String.valueOf(Math.random()),"Could not retrieve members of group due to exception.  " + e.getMessage());
		}

		List<GenericEntry> entries = feed.getEntries();
		for(GenericEntry e : entries){
			emails.add(e.getProperty("memberId"));
		}
		
		return emails.toArray(new String[emails.size()]);
	}

	@Override
	public boolean addUserToMailingList(MailingListEnum mailingList) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
