package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;

import org.objectweb.asm.Type;

import com.projectmaxwell.model.RecruitContact;
import com.projectmaxwell.model.RecruitContactType;
import com.projectmaxwell.service.dao.RecruitContactDAO;

public class RecruitContactDAOImpl extends AbstractMysqlDAOImpl implements RecruitContactDAO {

	@Override
	public RecruitContactType[] getRecruitContactTypes(){
		
		ArrayList<RecruitContactType> recruitContactTypeSet = new ArrayList<RecruitContactType>();
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_contact_types()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				RecruitContactType type = new RecruitContactType();
				type.setRecruitContactTypeId(result.getInt("id"));
				type.setName(result.getString("name"));
				type.setDescription(result.getString("description"));
				recruitContactTypeSet.add(type);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return recruitContactTypeSet.toArray(new RecruitContactType[recruitContactTypeSet.size()]);
	}

	@Override
	public RecruitContact[] getRecruitContactHistoryByParameters(Integer recruitUserId, Integer recruitContactorUserId,	Integer maxResults) {
		ArrayList<RecruitContact> recruitContactList = new ArrayList<RecruitContact>();
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_contact_history(?,?,?)");
			
			if(recruitUserId == null){
				call.setNull(1, Type.INT);
			}else{
				call.setInt(1, recruitUserId);
			}
			
			if(recruitContactorUserId == null){
				call.setNull(2, Type.INT);
			}else{
				call.setInt(2, recruitContactorUserId);
			}
			
			if(maxResults == null){
				call.setNull(3, Type.INT);
			}else{
				call.setInt(3, maxResults);
			}
			
			ResultSet result = call.executeQuery();
			while(result.next()){
				RecruitContact contact = new RecruitContact();
				contact.setRecruitContactId(result.getInt("id"));
				contact.setRecruitContactTypeId(result.getInt("recruit_contact_type_id"));
				contact.setRecruitUserId(result.getInt("recruit_user_id"));
				contact.setRecruitContactorUserId(result.getInt("recruit_contactor_user_id"));
				contact.setContactTimestamp(result.getInt("contact_timestamp"));
				contact.setNotes(result.getString("notes"));
				recruitContactList.add(contact);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();
		}
		return recruitContactList.toArray(new RecruitContact[recruitContactList.size()]);
	}
}
