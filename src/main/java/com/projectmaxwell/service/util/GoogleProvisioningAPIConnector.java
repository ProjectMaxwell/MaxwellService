package com.projectmaxwell.service.util;

public class GoogleProvisioningAPIConnector {
	
	public void doStuff(){
		try{
			AppsForYourDomainClient client = new AppsForYourDomainClient("eric@evergreenalumniclub.com", "eric691234", "apps");
		}catch(Exception e){
			System.out.println("Lame");
		}
	}
}
