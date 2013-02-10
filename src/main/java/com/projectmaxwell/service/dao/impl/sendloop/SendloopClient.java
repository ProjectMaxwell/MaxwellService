package com.projectmaxwell.service.dao.impl.sendloop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.exception.DependentServiceException;
import com.projectmaxwell.exception.PropertiesException;
import com.projectmaxwell.exception.SendloopDAOException;
import com.projectmaxwell.model.User;

public class SendloopClient extends DefaultHttpClient{

	private String hostname;
	private List<NameValuePair> bodyParams;
	protected Properties prop;
	private List<Header> headers;
	
	public static final String IMPORT_ENDPOINT = "Subscriber.Import/json";
	private static final String BROWSE_ENDPOINT = "Subscriber.Browse/json";
	
	public SendloopClient(){
		super(); 
		prop = new Properties();
		try {
			prop.load(DatasourceConnection.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			throw new PropertiesException(String.valueOf(Math.random()),"Could not load properties for service.");
		}
		
		hostname = prop.getProperty("sendloop.hostname");

		bodyParams = new ArrayList<NameValuePair>();
		addBodyParam("APIKey",prop.getProperty("sendloop.apikey"));
		
		headers = new ArrayList<Header>();
		addHeader("Accept","application/json");
	}
	
	public <T> T postRequest(String path, TypeReference<T> type){
		HttpPost post;
		try {
			post = new HttpPost(hostname + path);
			post.setEntity(new UrlEncodedFormEntity(bodyParams));
		} catch (URISyntaxException e) {
			throw new DependentServiceException(String.valueOf(Math.random()),"Failed to connect to the sendloop server.  " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new SendloopDAOException(String.valueOf(Math.random()),"Could not encode sendloop properties.");
		}
		
		post.setHeaders(headers.toArray(new Header[headers.size()]));
		
		try {
			HttpResponse response = this.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			String jsonBody = "";
			while ((line = rd.readLine()) != null) {
				jsonBody += line;
			}
			
			System.out.println(jsonBody);
			
			if(type != null){
				T responseObject = deserialize(jsonBody, type);
				return (T) responseObject;
			}
		} catch (HttpException e) {
			throw new SendloopDAOException(String.valueOf(Math.random()),"HTTP Request to Sendloop failed.  " + e.getMessage());
		} catch (IOException e) {
			throw new SendloopDAOException(String.valueOf(Math.random()),"IOException while contacting sendloop.");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T deserialize(String jsonBody, TypeReference<T> type){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T) mapper.readValue(jsonBody, type);
		} catch (Exception e) {
			throw new SendloopDAOException(String.valueOf(Math.random()),"Could not deserialize Sendloop's JSON response due to exception.  " + e.getMessage());
		}
		//return null;
	}
	
	public SendloopImportResponse importUserToList(User user, String listId){
		System.out.println("ListID: " + listId);
		System.out.println("Subscribers[0][EmailAddress]: " + user.getEmail());
		System.out.println("Subscribers[0][CustomField2]: " + user.getFirstName() + " " + user.getLastName());
//		NameValuePair listPair = addBodyParam("ListID", listId);
//		NameValuePair emailAddressPair = addBodyParam("Subscribers[0][EmailAddress]",user.getEmail());
//		NameValuePair namePair = addBodyParam("Subscribers[0][CustomField2]",user.getFirstName() + " " + user.getLastName());		
		NameValuePair listPair = new BasicNameValuePair("ListID",listId);
		NameValuePair emailAddressPair = new BasicNameValuePair("Subscribers[0][EmailAddress]",user.getEmail());
		NameValuePair namePair = new BasicNameValuePair("Subscribers[0][CustomField2]",user.getFirstName() + " " + user.getLastName());
		
		//SendloopImportResponse response = postRequest(IMPORT_ENDPOINT, new TypeReference<SendloopImportResponse>(){});
		
/*		removeBodyParam(namePair);
		removeBodyParam(emailAddressPair);
		removeBodyParam(listPair);*/
		
		NameValuePair[] requestParams = new NameValuePair[3];
		requestParams[0] = listPair;
		requestParams[1] = emailAddressPair;
		requestParams[2] = namePair;
		return importUsersToList(requestParams);
	}
	
	public SendloopImportResponse importUsersToList(NameValuePair[] requestParams){
		
		for(NameValuePair pair : requestParams){
			addBodyParam(pair);
		}
		
		SendloopImportResponse response = postRequest(IMPORT_ENDPOINT, new TypeReference<SendloopImportResponse>(){});
		
		for(NameValuePair pair : requestParams){
			removeBodyParam(pair);
		}
		
		return response;
	}
	
	public SendloopBrowseListResponse browseList(String listId){
		System.out.println("ListID: " + listId);
		NameValuePair listPair = addBodyParam("ListID",listId);
		SendloopBrowseListResponse response = postRequest(BROWSE_ENDPOINT, new TypeReference<SendloopBrowseListResponse>(){});
		
		removeBodyParam(listPair);
		return response;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String uri) {
		this.hostname = uri;
	}

	public List<NameValuePair> getBodyParams() {
		return bodyParams;
	}

	public void setBodyParams(List<NameValuePair> bodyParams) {
		this.bodyParams = bodyParams;
	}
	
	public void addBodyParam(NameValuePair bodyParam){
		bodyParams.add(bodyParam);
	}
	
	public NameValuePair addBodyParam(String key, String value){
		NameValuePair paramToAdd = new BasicNameValuePair(key,value);
		bodyParams.add(paramToAdd);
		return paramToAdd;
	}
	
	public void removeBodyParam(NameValuePair bodyParam){
		bodyParams.remove(bodyParam);
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}
	
	public void addHeader(Header header){
		headers.add(header);
	}
	
	public Header addHeader(String key, String value){
		Header header = new BasicHeader(key, value);
		headers.add(header);
		return header;
	}
	
	public void removeHeader(Header header){
		headers.remove(header);
	}
	
	
}
