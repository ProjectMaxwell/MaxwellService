package com.projectmaxwell.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

public class PhiAuthClient {

	private static final String VALIDATE_ENDPOINT = "/token/%s";
	
	private static String hostname;

	public static String getHostname() {
		return hostname;
	}

	public static void setHostname(String hostname) {
		PhiAuthClient.hostname = hostname;
	}
	
	public static PhiAuthValidationResponse validateToken(String token) throws URISyntaxException, HttpException, IOException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String uri = hostname + String.format(VALIDATE_ENDPOINT, token);
		System.out.println(uri);
		HttpGet request = new HttpGet(uri);
		HttpResponse response = httpClient.execute(request);
		String responseString = IOUtils.toString(response.getEntity().getContent());
		System.out.println(responseString);
		ObjectMapper mapper = new ObjectMapper();
		PhiAuthValidationResponse validationResponse = mapper.readValue(responseString, PhiAuthValidationResponse.class);
		return validationResponse;
	}
}
