package com.projectmaxwell.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.MailingListTuple;
import com.projectmaxwell.model.User;
import com.projectmaxwell.service.util.SendloopBatchProcess;

public class SendloopBatchProcessTest {

	@Test
	public void testChooseListToImportFromNull(){
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(null);
		assertNull(chosenList);
	}
	
	@Test
	public void testChooseListToImportFromEmptySet(){
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(new HashMap<String,HashSet<User>>());
		assertNull(chosenList);
	}
	
	@Test
	public void testChooseListToImportFromOneListWithOneUser(){
		HashMap<String,HashSet<User>> userMailingList = new HashMap<String,HashSet<User>>();
		HashSet<User> users = new HashSet<User>();
		User u1 = new User();
		users.add(u1);
		userMailingList.put(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId(), users);
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(userMailingList);
		assertNotNull(chosenList);
		assertEquals(chosenList.getMailingListId(),MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertEquals(chosenList.getUsers().length, userMailingList.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId()).size());
	}
	
	@Test
	public void testChooseListToImportFromOneListWithMultipleUsers(){
		HashMap<String,HashSet<User>> userMailingList = new HashMap<String,HashSet<User>>();
		HashSet<User> users = new HashSet<User>();
		User u1 = new User();
		users.add(u1);
		User u2 = new User();
		users.add(u2);
		User u3 = new User();
		users.add(u3);
		userMailingList.put(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId(), users);
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(userMailingList);
		assertNotNull(chosenList);
		assertEquals(chosenList.getMailingListId(),MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertEquals(chosenList.getUsers().length, userMailingList.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId()).size());
	}
	
	@Test
	public void testChooseListToImportFromTwoListsOneEmpty(){
		HashMap<String,HashSet<User>> userMailingList = new HashMap<String,HashSet<User>>();
		HashSet<User> users = new HashSet<User>();
		User u1 = new User();
		users.add(u1);
		userMailingList.put(MailingList.SENDLOOP_ALPHA_PI_ALUMNI.getId(),new HashSet<User>());
		userMailingList.put(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId(), users);
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(userMailingList);
		assertNotNull(chosenList);
		assertEquals(chosenList.getMailingListId(),MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertEquals(chosenList.getUsers().length, userMailingList.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId()).size());
	}
	
	@Test
	public void testChooseListToImportFromTwoListsWithOneUser(){
		HashMap<String,HashSet<User>> userMailingList = new HashMap<String,HashSet<User>>();
		HashSet<User> users1 = new HashSet<User>();
		HashSet<User> users2 = new HashSet<User>();
		User u1 = new User();
		users1.add(u1);
		User u2 = new User();
		users2.add(u2);
		userMailingList.put(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId(), users1);
		userMailingList.put(MailingList.SENDLOOP_ALPHA_PI_ALUMNI.getId(), users2);
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(userMailingList);
		assertNotNull(chosenList);
		assertTrue(chosenList.getMailingListId().equals(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId()) || chosenList.getMailingListId().equals(MailingList.SENDLOOP_ALPHA_PI_ALUMNI.getId()));
		assertEquals(chosenList.getUsers().length, 1);
	}
	
	@Test
	public void testChooseListToImportFromTwoListsOneBigger(){
		HashMap<String,HashSet<User>> userMailingList = new HashMap<String,HashSet<User>>();
		HashSet<User> users = new HashSet<User>();
		HashSet<User> users2 = new HashSet<User>();
		User u1 = new User();
		users.add(u1);
		User u2 = new User();
		users.add(u2);
		User u3 = new User();
		users2.add(u3);
		userMailingList.put(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId(), users);
		userMailingList.put(MailingList.SENDLOOP_ALPHA_PI_ALUMNI.getId(), users2);
		MailingListTuple chosenList = SendloopBatchProcess.chooseListToImport(userMailingList);
		assertNotNull(chosenList);
		assertEquals(chosenList.getMailingListId(),MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertEquals(chosenList.getUsers().length, userMailingList.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId()).size());
	}
}
