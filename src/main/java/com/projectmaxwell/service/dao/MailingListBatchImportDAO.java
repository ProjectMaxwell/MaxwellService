package com.projectmaxwell.service.dao;

import java.util.HashMap;
import java.util.HashSet;

import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.User;

public interface MailingListBatchImportDAO {
	
	/*
	 * This method should retrieve all of the values that are waiting to be imported.
	 */
	public HashMap<String,HashSet<User>> readQueue();
	
	/*
	 * Add another user's data to be queued up for import into our mailing lists
	 */
	public void writeToQueue(User user, String listId);
	
	/*
	 * Store all users that couldn't be imported for later troubleshooting.
	 */
	public void writeFailures(User[] users);
	
	/*
	 * Clear out the import queue after reading all the values in
	 */
	public void clearQueue();
}
