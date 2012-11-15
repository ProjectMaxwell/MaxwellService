package com.projectmaxwell.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.junit.Test;

public class PhiAuthValidationResponseTest {

	@Test
	public void testValidate() throws URISyntaxException, HttpException, IOException{
		PhiAuthClient.setHostname("http://evergreenalumniclub.com:7080/PhiAuth/rest");
		PhiAuthValidationResponse validationResponse = PhiAuthClient.validateToken("f03da1d9-d3ac-4b7b-b9aa-f461cc8fc0eb");
		assertNotNull(validationResponse);
		assertNotNull(validationResponse.getAccessToken());
		assertEquals(validationResponse.getAccessToken(),"f03da1d9-d3ac-4b7b-b9aa-f461cc8fc0eb");
	}
	
}
