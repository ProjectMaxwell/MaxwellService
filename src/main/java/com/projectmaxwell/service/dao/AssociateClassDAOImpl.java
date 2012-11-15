package com.projectmaxwell.service.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.ws.rs.WebApplicationException;
import com.projectmaxwell.model.AssociateClass;

public class AssociateClassDAOImpl extends AbstractDAOImpl implements
		AssociateClassDAO {

	@Override
	public AssociateClass[] getAssociateClasses() {
		
		ArrayList<AssociateClass> associateClassSet = new ArrayList<AssociateClass>();
		try{
			CallableStatement call = con.prepareCall("CALL get_associate_classes()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				AssociateClass associateClass = new AssociateClass();
				associateClass.setAssociateClassId(result.getInt("id"));
				associateClass.setName(result.getString("name"));
				associateClassSet.add(associateClass);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return associateClassSet.toArray(new AssociateClass[associateClassSet.size()]);
	}

	@Override
	public AssociateClass getAssociateClassById(int associateClassId) {
		AssociateClass associateClass = new AssociateClass();
		
		try{
			CallableStatement call = con.prepareCall("CALL get_associate_class_by_id(?)");
			call.setInt(1, associateClassId);
			ResultSet result = call.executeQuery();
			result.next();
			associateClass.setAssociateClassId(associateClassId);
			associateClass.setName(result.getString("name"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return associateClass;
	}

	@Override
	public AssociateClass createAssociateClass(AssociateClass associateClass) {
		try{
			CallableStatement call = con.prepareCall("CALL create_associate_class(?)");
			call.setString(1, associateClass.getName());
			ResultSet result = call.executeQuery();
			result.next();
			associateClass.setAssociateClassId(result.getInt("id"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return associateClass;
	}

	@Override
	public AssociateClass updateAssociateClass(AssociateClass associateClass) {
		
		try{
			CallableStatement call = con.prepareCall("CALL update_associate_class_by_id(?,?)");
			call.setInt(1, associateClass.getAssociateClassId());
			call.setString(2, associateClass.getName());
			ResultSet result = call.executeQuery();
			result.next();
			associateClass.setName(result.getString("name"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}

		return associateClass;
		
	}
	
	@Override
	public void deleteAssociateClassById(int associateClassId){
		
		try{
			PreparedStatement stmt = con.prepareStatement("CALL delete_associate_class_by_id(?)");
			stmt.setInt(1, associateClassId);
			stmt.execute();
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
	}

}
