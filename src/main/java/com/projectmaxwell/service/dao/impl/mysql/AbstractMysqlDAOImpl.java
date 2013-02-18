package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.datasource.DatasourceConnection;
import com.projectmaxwell.service.dao.impl.AbstractDAOImpl;
import com.projectmaxwell.service.resource.filter.PhiAuthSecurityContext;

public abstract class AbstractMysqlDAOImpl extends AbstractDAOImpl {

	private static final Logger LOGGER = Logger.getLogger(AbstractMysqlDAOImpl.class.getName());
	
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
			LOGGER.log(Level.SEVERE,"Releasing connection.");
		}catch(Exception e){
			throw new WebApplicationException(e);
		}
	}
}
