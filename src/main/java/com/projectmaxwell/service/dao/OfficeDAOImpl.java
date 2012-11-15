package com.projectmaxwell.service.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.model.Office;

public class OfficeDAOImpl extends AbstractDAOImpl implements OfficeDAO {

	@Override
	public Office[] getOffices(boolean active) {
		
		HashSet<Office> officeSet = new HashSet<Office>();
		try{
			CallableStatement call = con.prepareCall("CALL get_offices(?)");
			call.setBoolean(1, active);
			ResultSet result = call.executeQuery();
			while(result.next()){
				Office office = new Office();
				office.setActive(active);
				office.setHousepoints(result.getInt("housepoints"));
				office.setName(result.getString("name"));
				office.setOfficeId(result.getInt("id"));
				officeSet.add(office);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return officeSet.toArray(new Office[officeSet.size()]);
	}

}
