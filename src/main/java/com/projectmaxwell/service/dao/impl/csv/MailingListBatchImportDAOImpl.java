package com.projectmaxwell.service.dao.impl.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import org.apache.http.message.BasicNameValuePair;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.exception.InvalidFileException;
import com.projectmaxwell.exception.PropertiesException;
import com.projectmaxwell.model.MailingList;
import com.projectmaxwell.model.User;
import com.projectmaxwell.service.dao.MailingListBatchImportDAO;

public class MailingListBatchImportDAOImpl extends AbstractCSVDAOImpl implements
		MailingListBatchImportDAO {
	
	private static final MailingListBatchImportDAOImpl instance = new MailingListBatchImportDAOImpl();
	
	private Properties prop;
	private String batchImportFileLocation;
	private String batchImportFailuresFileLocation;
	private File batchImportFile;
	private File batchImportFailuresFile;
	
	private MailingListBatchImportDAOImpl(){
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
				batchImportFile = new File(batchImportFileLocation);
				if(!batchImportFile.exists()){
					batchImportFile.createNewFile();
				}
			} catch (IOException e) {
				System.out.println("Couldn't find csv import file defined in properties due to exception.  " + e.getMessage());
				throw new PropertiesException(String.valueOf(Math.random()),"Couldn't find csv import file defined in properties due to exception.  " + e.getMessage());
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
			throw e;
		}
	}
	
	public static MailingListBatchImportDAOImpl getInstance(){
		return instance;
	}
	
	@Override
	public HashMap<String,HashSet<User>> readQueue() {
		HashMap<String, HashSet<User>> listUserMappings = new HashMap<String,HashSet<User>>();
		HashSet<User> users = new HashSet<User>();
		FileReader batchImportFileReader;
		CSVReader reader = null;
		try{
			batchImportFileReader = new FileReader(batchImportFile);
		}catch(IOException e){
			System.out.println("Couldn't get file reader for file.");
			throw new PropertiesException(String.valueOf(Math.random()),"Couldn't get file reader from file due to exception.  " + e.getMessage());
		}
		
		try{
			reader = new CSVReader(batchImportFileReader,',');	
			
			String [] nextLine;
	    	try {
				while ((nextLine = reader.readNext()) != null) {
					if(nextLine[0] == null || nextLine[0].equals("") || nextLine[1] == null || nextLine[1].equals("")){
						System.out.println("Invalid or empty line in batch import queue, skipping.");
						continue;
					}
					
					try{
						User u = new User();
						u.setEmail(nextLine[1]);
						if(nextLine.length > 1 && nextLine[2] != null && !nextLine[2].equals("")){
							u.setFirstName(nextLine[2]);
						}
						if(nextLine.length > 2 && nextLine[3] != null && !nextLine[3].equals("")){
							u.setLastName(nextLine[3]);
						}
						users.add(u);
						String mailingListId = nextLine[0];
						System.out.println(mailingListId);
						if(listUserMappings.containsKey(mailingListId)){
							listUserMappings.get(mailingListId).add(u);
						}else{
							HashSet<User> userSet = new HashSet<User>();
							userSet.add(u);
							listUserMappings.put(mailingListId, userSet);
						}
					}catch (Exception e) {
						System.out.println("Found other error." + e.getClass() + e.getMessage());
					}
				}
			} catch (IOException e) {
				System.out.println("Could not get new line from reader." + e.getMessage());
			}
		}finally{
			try {
				reader.close();
				batchImportFileReader.close();
			} catch (IOException e) {
				System.out.println("Couldn't close file reader.");
			}
		}
		return listUserMappings;
	}

	@Override
	public void writeToQueue(User user, String list) {
		if(list == null || list.equals("")){
			System.out.println("No mailing list id defined.");
		}
		FileWriter batchImportFileWriter = null;
		try {
			batchImportFileWriter = new FileWriter(batchImportFile,true);
		} catch (IOException e) {
			System.out.println("Could not open up file writer to write to batch import csv due to exception." + e.getMessage());
			return;
		}

    	CSVWriter writer = null;
    	try {
			writer = new CSVWriter(batchImportFileWriter,',',CSVWriter.NO_QUOTE_CHARACTER);
			String[] rowToWrite = {list,user.getEmail(),user.getFirstName(),user.getLastName()};
			writer.writeNext(rowToWrite);
		} catch (Exception e) {
			System.out.println("Could not get CSV writer from file." + e.getMessage());
			throw new InvalidFileException(String.valueOf(Math.random()),"Could not open batch import csv file.  " + e.getMessage()); 
		} finally{
			try {
				writer.close();
				batchImportFileWriter.close();
			} catch (IOException e) {
				System.out.println("Could not close file writers.");
			}
		}
	}

	@Override
	public void writeFailures(User[] users) {	
		FileWriter batchImportFailuresFileWriter = null;
		try {
			batchImportFailuresFileWriter = new FileWriter(batchImportFailuresFile,true);
		} catch (IOException e) {
			System.out.println("Could not open up file writer to write to failures csv due to exception." + e.getMessage());
			return;
		}

    	CSVWriter writer = null;
    	try {
			writer = new CSVWriter(batchImportFailuresFileWriter,',',CSVWriter.NO_QUOTE_CHARACTER);
			for(User u : users){
				System.out.println(u.getEmail() + " was not added to mailing list.");
				String[] rowToWrite = {u.getEmail(),u.getFirstName(),u.getLastName()};
				writer.writeNext(rowToWrite);
			}
			
		} catch (Exception e) {
			System.out.println("Could not get CSV writer from file." + e.getMessage());
			throw new InvalidFileException(String.valueOf(Math.random()),"Could not open sendloop batch import csv file.  " + e.getMessage()); 
		} finally{
			try {
				writer.close();
				batchImportFailuresFileWriter.close();
			} catch (IOException e) {
				System.out.println("Could not close file writers.");
			}
		}
	}

	@Override
	public void clearQueue() {
		FileWriter batchImportFileWriter;
		try {
			batchImportFileWriter = new FileWriter(batchImportFile);
		} catch (IOException e) {
			System.out.println("Couldn't get writer to clear out imports file." + e.getMessage());
			return;
		}	
		
		try {
			batchImportFileWriter.write("");
		} catch (IOException e) {
			System.out.println("Couldn't write empty string to import file." + e.getMessage());
		}finally{
			try {
				batchImportFileWriter.close();
			} catch (IOException e) {
				System.out.println("Could not close file writer.");
			}
		}
		
	}

}
