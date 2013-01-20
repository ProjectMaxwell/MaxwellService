package com.projectmaxwell.service.dao.impl.google;

import com.google.gdata.client.appsforyourdomain.AppsGroupsService;
import com.google.gdata.util.AuthenticationException;
import com.projectmaxwell.exception.GoogleAppsDAOException;

public class AbstractGoogleDAOImpl {
	//AppsForYourDomainClient client = new AppsForYourDomainClient("groupsadmin@evergreenalumniclub.com", "52a71ecb-93f3-4fc5-abba-8a95df1d260d", "evergreenalumniclub.com");
	
	AppsGroupsService groups;
	
	public AbstractGoogleDAOImpl(){
		 try {
			groups = new AppsGroupsService("groupsadmin@evergreenalumniclub.com", "52a71ecb-93f3-4fc5-abba-8a95df1d260d", "evergreenalumniclub.com", "apps");
		} catch (AuthenticationException e) {
			throw new GoogleAppsDAOException(String.valueOf(Math.random()),"Could not set up connection to google apps due to exception.");
		}
	}
}
