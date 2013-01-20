package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.model.AcademicTerm;
import com.projectmaxwell.service.dao.AcademicTermDAO;

public class AcademicTermDAOImpl extends AbstractMysqlDAOImpl implements
		AcademicTermDAO {

	@Override
	public AcademicTerm[] getAcademicTerms() {
		HashSet<AcademicTerm> academicTermSet = new HashSet<AcademicTerm>();
		try{
			CallableStatement call = con.prepareCall("CALL get_academic_terms()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				AcademicTerm academicTerm = new AcademicTerm();
				academicTerm.setAcademicTermId(result.getInt("id"));
				academicTerm.setName(result.getString("name"));
				academicTerm.setStartDate(result.getDate("start_date"));
				academicTerm.setEndDate(result.getDate("end_date"));
				academicTermSet.add(academicTerm);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return academicTermSet.toArray(new AcademicTerm[academicTermSet.size()]);
	}

	@Override
	public AcademicTerm getAcademicTermById(int academicTermId) {
		AcademicTerm academicTerm = new AcademicTerm();
		
		try{
			CallableStatement call = con.prepareCall("CALL get_academic_term_by_id(?)");
			call.setInt(1, academicTermId);
			ResultSet result = call.executeQuery();
			result.next();
			academicTerm.setAcademicTermId(academicTermId);
			academicTerm.setName(result.getString("name"));
			academicTerm.setStartDate(result.getDate("start_date"));
			academicTerm.setEndDate(result.getDate("end_date"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return academicTerm;
	}

	@Override
	public AcademicTerm createAcademicTerm(AcademicTerm academicTerm) {
		try{
			CallableStatement call = con.prepareCall("CALL create_academic_term(?,?,?)");
			call.setString(1, academicTerm.getName());
			call.setObject(2, academicTerm.getStartDate());
			call.setObject(3, academicTerm.getEndDate());
			ResultSet result = call.executeQuery();
			result.next();
			academicTerm.setAcademicTermId(result.getInt("id"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return academicTerm;
	}

	@Override
	public AcademicTerm updateAcademicTerm(AcademicTerm academicTerm) {
		try{
			CallableStatement call = con.prepareCall("CALL update_academic_term_by_id(?,?,?,?)");
			call.setInt(1, academicTerm.getAcademicTermId());
			call.setString(2, academicTerm.getName());
			call.setObject(3, academicTerm.getStartDate());
			call.setObject(4, academicTerm.getEndDate());
			ResultSet result = call.executeQuery();
			result.next();
			academicTerm.setName(result.getString("name"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}

		return academicTerm;
	}

	@Override
	public void deleteAcademicTermById(int academicTermId) {
		try{
			PreparedStatement stmt = con.prepareStatement("CALL delete_academic_term_by_id(?)");
			stmt.setInt(1, academicTermId);
			stmt.execute();
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
	}

}
