package com.projectmaxwell.service.dao;

import java.sql.Connection;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.model.validation.ModelValidator;

public abstract class AbstractDAOImpl {

	protected ModelValidator validator;
	protected Connection con;
	
	public AbstractDAOImpl() throws WebApplicationException{
		validator = new ModelValidator();
		try{
			con = DatasourceConnection.getConnection();
		}catch(Exception e){
			throw new WebApplicationException(e);
		}
	}
	
	public void releaseConnection() throws WebApplicationException{
		try{
			con.close();
			con = null;
		}catch(Exception e){
			throw new WebApplicationException(e);
		}
	}
}
