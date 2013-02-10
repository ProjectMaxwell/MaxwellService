package com.projectmaxwell.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.impl.csv.MailingListBatchImportDAOImpl;

public class MailingListCSVDAOImplTest {
	MailingListBatchImportDAOImpl impl;
	
	@Before
	public void setup(){
		impl = MailingListBatchImportDAOImpl.getInstance();
	}
	
	@Test
	@Ignore
	public void testReadQueue() {
		
		HashMap<String, HashSet<User>> response = impl.readQueue();
		HashSet<User> usersSet = response.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertNotNull(usersSet);
		User[] users = usersSet.toArray(new User[1]);
		assertNotNull(users);
		assertTrue(users.length > 0);
		assertEquals(users[0].getEmail(),"test@test.com");
		assertEquals(users[0].getFirstName(),"test");
		assertEquals(users[0].getLastName(),"user");
	}
	
	@Test
	@Ignore
	public void testClearQueue(){
		impl.clearQueue();
		HashMap<String, HashSet<User>> response = impl.readQueue();
		assertNull(response);
	}
	
	@Test
	@Ignore
	public void testWriteFailures(){
		User[] users = new User[3];
		User u = new User();
		u.setEmail("testEmail1@test.test");
		u.setFirstName("test");
		u.setLastName("email");
		User u2 = new User();
		u2.setEmail("testEmail2@test.test");
		u2.setFirstName("test2");
		User u3 = new User();
		u3.setEmail("testEmail3@test.test");
		u3.setLastName("email3");
		users[0] = u;
		users[1] = u2;
		users[2] = u3;
		impl.writeFailures(users);
	}
	
	@Test
	@Ignore
	public void testWriteToQueue(){
		User u = new User();
		u.setEmail("galactoise+fake13@gmail.com");
		u.setFirstName("fake13");
		u.setLastName("user13");
		
		impl.clearQueue();
		impl.writeToQueue(u, MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		HashMap<String, HashSet<User>> response = impl.readQueue();
		HashSet<User> usersSet = response.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertNotNull(usersSet);
		User[] users = usersSet.toArray(new User[1]);
		assertNotNull(users);
		assertEquals(users.length, 1);
		assertNotNull(users[0]);
		assertEquals(users[0].getEmail(),u.getEmail());
		assertEquals(users[0].getFirstName(),u.getFirstName());
		assertEquals(users[0].getLastName(),u.getLastName());
	}
	
	@Test
	@Ignore
	public void testWriteToQueueAppend(){
		User u = new User();
		u.setEmail("galactoise+fake14@gmail.com");
		u.setFirstName("fake14");
		u.setLastName("user14");
		
		User u2 = new User();
		u2.setEmail("galactoise+fake15@gmail.com");
		u2.setFirstName("fake15");
		u2.setLastName("user15");
		
		impl.clearQueue();
		impl.writeToQueue(u, MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		impl.writeToQueue(u2,MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		HashMap<String, HashSet<User>> response = impl.readQueue();
		HashSet<User> usersSet = response.get(MailingList.SENDLOOP_EVERGREEN_ALUMNI_CLUB.getId());
		assertNotNull(usersSet);
		User[] users = usersSet.toArray(new User[1]);
		assertNotNull(users);
		assertEquals(users.length, 2);
		assertNotNull(users[0]);
		assertNotNull(users[1]);
		assertNotNull(users[0].getEmail());
		User user1 = null;
		User user2 = null;
		if(users[0].getEmail().equals(u.getEmail())){
			user1 = users[0];
			user2 = users[1];
		}else if(users[0].getEmail().equals(u2.getEmail())){
			user1 = users[1];
			user2 = users[2];
		}else{
			fail();
		}
		assertEquals(user1.getEmail(),u.getEmail());
		assertEquals(user1.getFirstName(),u.getFirstName());
		assertEquals(user1.getLastName(),u.getLastName());
		assertEquals(user2.getEmail(),u2.getEmail());
		assertEquals(user2.getFirstName(),u2.getFirstName());
		assertEquals(user2.getLastName(),u2.getLastName());
	}

}
