package com.projectmaxwell.service.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import org.apache.http.message.BasicNameValuePair;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.exception.PropertiesException;
import com.projectmaxwell.model.MailingListTuple;
import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.MailingListBatchImportDAO;
import com.projectmaxwell.service.dao.impl.csv.MailingListBatchImportDAOImpl;
import com.projectmaxwell.service.dao.impl.sendloop.SendloopBrowseListResponse;
import com.projectmaxwell.service.dao.impl.sendloop.SendloopClient;
import com.projectmaxwell.service.dao.impl.sendloop.SendloopImportResponse;
import com.projectmaxwell.service.dao.impl.sendloop.SendloopSubscriber;

public class SendloopBatchProcess implements Runnable {

	private SendloopClient client;
	Properties prop;
	String batchImportFileLocation;
	String batchImportFailuresFileLocation;
	FileReader batchImportFileReader;
	FileWriter batchImportFileWriter;
	File batchImportFile;
	File batchImportFailuresFile;
	FileWriter batchImportFailuresFileWriter;
	private MailingListBatchImportDAO batchImporter;
	
	public SendloopBatchProcess(){
		client = new SendloopClient();
		prop = new Properties();
		try{
			try {
				prop.load(DatasourceConnection.class.getClassLoader().getResourceAsStream("config.properties"));
				batchImportFileLocation = prop.getProperty("sendloop.batchimport.file.location");
				batchImportFailuresFileLocation = prop.getProperty("sendloop.batchimport.failures.file.location");
			} catch (IOException e) {
				System.out.println("Couldn't retrieve property defining BatchImport CSV locations due to exception.  " + e.getMessage());
				throw new PropertiesException(String.valueOf(Math.random()),"Couldn't retrieve property defining BatchImport CSV locations due to exception.  " + e.getMessage());
			}
			
			try{
				batchImportFailuresFile = new File(batchImportFailuresFileLocation);
				if(!batchImportFailuresFile.exists()){
					batchImportFailuresFile.createNewFile();
				}
			} catch (IOException e) {
				System.out.println("Couldn't find csv failures file defined in properties due to exception.  " + e.getMessage());
				throw new PropertiesException(String.valueOf(Math.random()),"Couldn't find csv failures file defined in properties due to exception.  " + e.getMessage());
			}
		}catch(NullPointerException e){
			System.out.println("Nullpointer in setup of files.");
		}
		batchImporter = MailingListBatchImportDAOImpl.getInstance();
	}
	
	@Override
	public void run() {
		
		HashSet<BasicNameValuePair> requestParams = new HashSet<BasicNameValuePair>();
    	
		HashMap<String,HashSet<User>> listUserMappings = batchImporter.readQueue();
    	
    	MailingListTuple chosenList = chooseListToImport(listUserMappings);
    	
    	if(chosenList == null || chosenList.getMailingListId() == null || chosenList.getUsers() == null || chosenList.getUsers().length < 1){
    		System.out.println("No subscribers to add.");
    		return;
    	}
    	User[] users = chosenList.getUsers();

		requestParams.add(new BasicNameValuePair("ListID",chosenList.getMailingListId()));
    	System.out.println("Found " + users.length + " user records, sending to sendloop.");
    	System.out.println("Printing out users list from DAO.");
    	int i = 0;
    	for(User u : users){
    		System.out.println("Email: " + u.getEmail());
    		System.out.println("FirstName: " + u.getFirstName());
    		System.out.println("LastName: " + u.getLastName());
    		requestParams.add(new BasicNameValuePair("Subscribers[" + i + "][EmailAddress]",u.getEmail()));
    		if(u.getFirstName() != null){
    			String name = u.getFirstName();
    			if(u.getLastName() != null){
    				name += " " + u.getLastName();
    			}
        		requestParams.add(new BasicNameValuePair("Subscribers[" + i + "][CustomField2]",name));
    		}
    		i++;
    	}
    	
    	
    	SendloopImportResponse importResponse = client.importUsersToList(requestParams.toArray(new BasicNameValuePair[requestParams.size()]));
    	if(!importResponse.isSuccess()){
    		System.out.println("Import failed.");
    		return;
    	}
    	

		HashSet<String> retrievedSubscribers = new HashSet<String>();
    	try{
			SendloopBrowseListResponse response = client.browseList(chosenList.getMailingListId());
			for(SendloopSubscriber subscriber : response.getSubscribers()){
				retrievedSubscribers.add(subscriber.getEmailAddress());
			}
    	}catch(Exception e){
    		System.out.println("NPE IN retrieval.");
    		return;
    	}
		
		try{
			HashSet<User> failedUsers = new HashSet<User>();
			for(User u : users){
				if(!retrievedSubscribers.contains(u.getEmail())){
					System.out.println(u.getEmail() + " was not added to mailing list.");
					failedUsers.add(u);
				}
			}
			batchImporter.writeFailures(failedUsers.toArray(new User[failedUsers.size()]));
		}catch(Exception e){
			System.out.println("NPE IN comparison.");
			return;
		}
		batchImporter.clearQueue();
		
		//Write values for the lists not chosen for import this time back out to the queue
		for(String key : listUserMappings.keySet()){
			if(key.equalsIgnoreCase(chosenList.getMailingListId())){
				continue;
			}
			HashSet<User> usersToWrite = listUserMappings.get(key);
			for(User u : usersToWrite){
				batchImporter.writeToQueue(u, key);
			}
		}
	}

	/**
	 * Helper method to figure out which list of users to prioritize
	 * This could theoretically be overriden to use a different algorithm for deciding which list goes next
	 * @param listUserMappings
	 * @return
	 */
	public static MailingListTuple chooseListToImport(HashMap<String, HashSet<User>> listUserMappings) {
		if(listUserMappings == null || listUserMappings.size() < 1){
			return null;
		}
		User[] users = null;
		String mailingListToUse = null;
		//Iterate over the list of mailing list ids
		for(String key : listUserMappings.keySet()){
			//find the size of the set for mailingList with id = key
			int numQueuedForList = listUserMappings.get(key).size();
			//If the new size is bigger than the size of any previous list, focus on that list.
			if(users == null || users.length < numQueuedForList){
				users = listUserMappings.get(key).toArray(new User[numQueuedForList]);
				mailingListToUse = key;
			}
		}
		//Return the key and user array for whichever list was biggest
		return new MailingListTuple(mailingListToUse, users);
	}
}
