package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.service.dao.impl.AbstractDAOImpl;

public abstract class AbstractMysqlDAOImpl extends AbstractDAOImpl {

	private static final Logger LOGGER = Logger.getLogger(AbstractMysqlDAOImpl.class.getName());
	
	protected Connection con;
	
	public AbstractMysqlDAOImpl() throws WebApplicationException{
		super();
		try{
			con = DatasourceConnection.getConnection();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Could not get new connection - operation will fail.");
			throw new WebApplicationException(e);
		}
	}
	
	public void releaseConnection() throws WebApplicationException{
		try{
			if(!con.isClosed()){
				con.close();
			}
		}catch(Exception e){
			LOGGER.log(Level.WARNING,"Could not release connection - this may lead to a memory leak.");
			throw new WebApplicationException(e);
		}
	}
}
