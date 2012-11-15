package com.projectmaxwell.util;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

public class PasswordSaltTest {

	
	@Test
	public void testPasswordSalt(){
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		assertTrue(true);
	}	
	
}
