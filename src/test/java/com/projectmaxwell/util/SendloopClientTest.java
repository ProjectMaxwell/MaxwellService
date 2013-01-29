package com.projectmaxwell.util;

import org.apache.http.NameValuePair;
import org.junit.Ignore;
import org.junit.Test;

import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.impl.sendloop.SendloopClient;

public class SendloopClientTest {

	@Test
	@Ignore
	public void testSendloopClientSubscribeAndUnsubscribe(){
		SendloopClient client = new SendloopClient();
		client.addBodyParam("ListID", "2");
		client.addHeader("Accept-language", "en-US");
		String gmailModified = String.valueOf(Math.random()).substring(2, 7);
		client.addBodyParam("EmailAddress","galactoise+" + gmailModified + "@gmail.com");	
		NameValuePair subscriptionParam = client.addBodyParam("SubscriptionIP","24.19.233.55");

		client.postRequest("Subscriber.Subscribe/json");
		
		client.removeBodyParam(subscriptionParam);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.addBodyParam("UnsubscriptionIP","24.19.233.55");
		client.postRequest("Subscriber.Unsubscribe/json");
	}
	
	@Test
	@Ignore
	public void testSendloopClientImport(){
		String gmailModified = String.valueOf(Math.random()).substring(2, 7);
		SendloopClient client = new SendloopClient();
/*		client.addBodyParam("ListID", "2");
		client.addHeader("Accept-language", "en-US");
		client.addBodyParam("Subscribers[0][EmailAddress]","galactoise+" + gmailModified + "@gmail.com");

		client.postRequest("Subscriber.Import/json");*/
		User u = new User();
		u.setEmail("galactoise+" + gmailModified + "@gmail.com");
		client.importUserToList(u, "2");
	}
	
}
