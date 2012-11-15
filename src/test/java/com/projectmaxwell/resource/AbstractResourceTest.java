package com.projectmaxwell.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import com.projectmaxwell.service.resource.AbstractResource;
import com.projectmaxwell.util.PhiAuthValidationResponse;
import com.projectmaxwell.exception.InsufficientScopesException;

public class AbstractResourceTest {

	PhiAuthValidationResponse response;
	AbstractResource resource;
	
	@Before
	public void beforeTest(){
		response = new PhiAuthValidationResponse();
		resource = new AbstractResource(){};
	}
	
	@Test
	public void testHasScope(){
		String[] grantedScopes = {"read_information_others","read_user_list","CUD_system_metadata","update_user_self","create_user","read_information_self","view_system_metadata","update_user_others"};
		String[] allowedScopes = {"CUD_system_metadata"};
		response.setScopes(grantedScopes);
		boolean pass = resource.hasScope(response,allowedScopes);
		assertTrue(pass);		
	}
	
	@Test
	public void testEmptyAllowedScopes(){
		String[] grantedScopes = {"read_information_others","read_user_list","CUD_system_metadata","update_user_self","create_user","read_information_self","view_system_metadata","update_user_others"};
		String[] allowedScopes = {};
		response.setScopes(grantedScopes);
		boolean pass = resource.hasScope(response,allowedScopes);
		assertTrue(pass);		
	}
	
	@Test
	public void testNullAllowedScopes(){
		String[] grantedScopes = {"read_information_others","read_user_list","CUD_system_metadata","update_user_self","create_user","read_information_self","view_system_metadata","update_user_others"};
		response.setScopes(grantedScopes);
		boolean pass = resource.hasScope(response,null);
		assertTrue(pass);		
	}
	
	@Test(expected = InsufficientScopesException.class) 
	public void testWrongScopes(){
		String[] grantedScopes = {"read_information_others","read_user_list","CUD_system_metadata","update_user_self","create_user","read_information_self","view_system_metadata","update_user_others"};
		String[] allowedScopes = {"unknown_scope"};
		response.setScopes(grantedScopes);
		boolean pass = resource.hasScope(response,allowedScopes);
	}
	
	@Test(expected = InsufficientScopesException.class)
	public void testEmptyScopes(){
		String[] grantedScopes = {};
		String[] allowedScopes = {"CUD_system_metadata"};
		response.setScopes(grantedScopes);
		boolean pass = resource.hasScope(response,allowedScopes);
	}
	
	@Test(expected = InsufficientScopesException.class)
	public void testNullScopes(){
		String[] allowedScopes = {"CUD_system_metadata"};
		boolean pass = resource.hasScope(response,allowedScopes);
	}
}
