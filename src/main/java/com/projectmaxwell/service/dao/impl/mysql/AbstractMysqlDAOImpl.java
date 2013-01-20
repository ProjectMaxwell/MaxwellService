package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.Connection;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.model.validation.ModelValidator;
import com.projectmaxwell.service.dao.impl.AbstractDAOImpl;

public abstract class AbstractMysqlDAOImpl extends AbstractDAOImpl {

	protected Connection con;
	
	public AbstractMysqlDAOImpl() throws WebApplicationException{
		super();
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
