package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;

import org.objectweb.asm.Type;

import com.projectmaxwell.exception.MissingUserFieldException;
import com.projectmaxwell.exception.MySQLException;
import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.model.User;
import com.projectmaxwell.model.UserType;
import com.projectmaxwell.service.SupportedHttpMethods;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.impl.mysql.AbstractMysqlDAOImpl;

public class UserDAOImpl extends AbstractMysqlDAOImpl implements UserDAO {

	Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());
	
	public UserDAOImpl(){
		super();
	}
	
	@Override
	public User getUserById(int userId) throws WebApplicationException{
		User user = new User();
		user.setUserId(userId);
		
		try{
			CallableStatement call = con.prepareCall("CALL get_user_by_id(?)");
			call.setInt(1, userId);
			ResultSet result = call.executeQuery();
			result.next();
			user.setFirstName(result.getString("first_name"));
			user.setLastName(result.getString("last_name"));
			user.setEmail(result.getString("email"));
			user.setAssociateClassId(result.getInt("associate_class_id"));
			user.setChapterId(result.getInt("chapter_id"));
			user.setDateOfBirth(result.getDate("date_of_birth"));
			user.setPin(result.getInt("pin"));
			user.setUserTypeId(result.getInt("user_type_id"));
			user.setYearGraduated(result.getInt("year_graduated"));
			user.setYearInitiated(result.getInt("year_initiated"));
			user.setPhoneNumber(result.getString("phone_number"));
			user.setHighschool(result.getString("highschool"));
			user.setReferredById(result.getInt("referred_by_id"));
			user.setFacebookId(result.getString("facebook_id"));
			user.setLinkedInId(result.getString("linkedIn_id"));
			user.setTwitterId(result.getString("twitter_id"));
			user.setGoogleAccountId(result.getString("google_account_id"));
			user.setDateCreated(result.getInt("date_created"));
			user.setDateModified(result.getInt("date_modified"));
			//user.setPhoneNumber(result.getString("phone_number"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return user;
	}

	@Override
	public User[] getUsers(int userType) throws WebApplicationException{
		int type = 1; //Default to active users, user userType if it's set
		if(userType > 1){
			type = userType;
		}
		HashSet<User> userSet = new HashSet<User>();
		try{
			CallableStatement call = con.prepareCall("CALL get_users(?)");
			call.setInt(1, type);
			ResultSet result = call.executeQuery();
			while(result.next()){
				User user = new User();
				user.setUserId(result.getInt("id"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				Integer associateClassId = result.getInt("associate_class_id");
				if(!result.wasNull()){
					user.setAssociateClassId(associateClassId);
				}
				
				userSet.add(user);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return userSet.toArray(new User[userSet.size()]);
	}

	@Override
	public User createUser(User user) throws WebApplicationException {
		validator.validateUser(user);
		try{
			CallableStatement call = con.prepareCall("CALL create_user(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			call.setString(1, user.getFirstName());
			call.setString(2, user.getLastName());
			call.setString(3, user.getEmail());
			call.setObject(4, user.getDateOfBirth());
			if(user.getYearInitiated() != null){
				call.setInt(5, user.getYearInitiated());
			}else{
				call.setNull(5, Type.INT);
			}
			if(user.getYearGraduated() != null){
				call.setInt(6, user.getYearGraduated());
			}else{
				call.setNull(6, Type.INT);
			}
			if(user.getPin() != null){
				call.setInt(7, user.getPin());
			}else{
				call.setNull(7, Type.INT);
			}
			if(user.getAssociateClassId() != null){
				call.setInt(8, user.getAssociateClassId());
			}else{
				call.setNull(8, Type.INT);
			}
			if(user.getChapterId() > 0){
				call.setInt(9, user.getChapterId());
			}else{
				call.setNull(9, Type.INT);
			}
			if(user.getUserTypeId() != null){
				call.setInt(10, user.getUserTypeId());
			}else{
				call.setNull(10, Type.INT);
			}
			call.setString(11, user.getPhoneNumber());
			call.setString(12, user.getHighschool());
			if(user.getReferredById() != null){
				call.setInt(13, user.getReferredById());
			}else{
				call.setNull(13, Type.INT);
			}
			call.setString(14, user.getFacebookId());
			call.setString(15, user.getLinkedInId());
			call.setString(16, user.getTwitterId());
			call.setString(17, user.getGoogleAccountId());
			ResultSet result = call.executeQuery();
			result.next();
			user.setUserId(result.getInt("id"));
			user.setDateCreated(result.getInt("date_created"));
			
			if(user.getUserTypeId() == 5){
				RecruitInfo recruitInfo = user.getRecruitInfo();
				if(recruitInfo == null){
					recruitInfo = new RecruitInfo();
					recruitInfo.setRecruitEngagementLevelId(1);
					recruitInfo.setRecruitSourceId(6);
				}
				createRecruitInfo(recruitInfo, Integer.valueOf(user.getUserId()));
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return user;
	}
	
	@Override
	public User updateUser(User user, int userId) throws WebApplicationException {
		if(user == null || user.getUserId() == null || user.getUserId() < 1 || userId < 1 || user.getUserId() != userId){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
					"Required field 'userId' is null or empty");
		}
		//User oldUser = getUserById(user.getUserId());
		validator.validateUserObjectByMethod(user, null, SupportedHttpMethods.PUT);
		
		try{
			CallableStatement call = con.prepareCall("CALL `update_user_by_id`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			call.setInt(1, user.getUserId());
			call.setString(2, user.getFirstName());
			call.setString(3, user.getLastName());
			call.setString(4, user.getEmail());
			call.setObject(5, user.getDateOfBirth());
			if(user.getYearInitiated() != null){
				call.setInt(6, user.getYearInitiated());
			}else{
				call.setNull(6, Type.INT);
			}
			if(user.getYearGraduated() != null){
				call.setInt(7, user.getYearGraduated());
			}else{
				call.setNull(7, Type.INT);
			}
			if(user.getPin() != null){
				call.setInt(8, user.getPin());
			}else{
				call.setNull(8, Type.INT);
			}
			if(user.getAssociateClassId() != null){
				call.setInt(9, user.getAssociateClassId());
			}else{
				call.setNull(9, Type.INT);
			}
			if(user.getChapterId() > 0){
				call.setInt(10, user.getChapterId());
			}else{
				call.setNull(10, Type.INT);
			}
			if(user.getUserTypeId() != null){
				call.setInt(11, user.getUserTypeId());
			}else{
				call.setNull(11, Type.INT);
			}
			call.setString(12, user.getPhoneNumber());
			call.setString(13, user.getHighschool());
			if(user.getReferredById() != null){
				call.setInt(14, user.getReferredById());
			}else{
				call.setNull(14, Type.INT);
			}
			call.setString(15, user.getFacebookId());
			call.setString(16, user.getLinkedInId());
			call.setString(17, user.getTwitterId());
			call.setString(18, user.getGoogleAccountId());
			ResultSet result = call.executeQuery();
			if(result.next()){
				user.setDateModified(result.getInt("date_modified"));
			}
		}catch(SQLException sqle){
			throw new MySQLException(String.valueOf(Math.random()),"Could not update user due to exception. " + sqle.getMessage());
		}finally{
			releaseConnection();			
		}
		return user;
	}
	
	@Override
	public RecruitInfo createRecruitInfo(RecruitInfo recruitInfo, int userId){
		validator.validateRecruitInfo(recruitInfo);
		
		try {
			CallableStatement call = con.prepareCall("CALL create_recruit_info(?,?,?,?,?,?,?,?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitInfo.getRecruitSourceId());
			call.setInt(3, recruitInfo.getRecruitEngagementLevelId());
			if(recruitInfo.getRushListUserId() != null){
				call.setInt(4, recruitInfo.getRushListUserId());
			}else{
				call.setNull(4, Type.INT);
			}
			if(recruitInfo.getGpa() != null){
				call.setDouble(5, recruitInfo.getGpa());
			}else{
				call.setNull(5, Type.DOUBLE);
			}
			call.setString(6, recruitInfo.getClassStanding());
			call.setString(7, recruitInfo.getLifeExperiences());
			call.setString(8, recruitInfo.getLookingFor());
			call.setString(9, recruitInfo.getExpectations());
			call.setString(10, recruitInfo.getExtracurriculars());
			ResultSet result = call.executeQuery();
			if(result.next()){
				recruitInfo.setDateCreated(result.getLong("date_created"));
			}
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"Could not create recruit info due to exception. " + e.getMessage());
		}finally{
			releaseConnection();
		}
		return recruitInfo;
	}

	@Override
	public RecruitInfo updateRecruitInfo(RecruitInfo recruitInfo, int userId) {
		validator.validateRecruitInfo(recruitInfo);
		try {
			CallableStatement call = con.prepareCall("CALL update_recruit_info_by_user_id(?,?,?,?,?,?,?,?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitInfo.getRecruitSourceId());
			call.setInt(3, recruitInfo.getRecruitEngagementLevelId());
			if(recruitInfo.getRushListUserId() != null){
				call.setInt(4, recruitInfo.getRushListUserId());
			}else{
				call.setNull(4, Type.INT);
			}
			if(recruitInfo.getGpa() != null){
				call.setDouble(5, recruitInfo.getGpa());
			}else{
				call.setNull(5, Type.DOUBLE);
			}
			call.setString(6, recruitInfo.getClassStanding());
			call.setString(7, recruitInfo.getLifeExperiences());
			call.setString(8, recruitInfo.getLookingFor());
			call.setString(9, recruitInfo.getExpectations());
			call.setString(10, recruitInfo.getExtracurriculars());
			ResultSet result = call.executeQuery();
			if(result.next()){
				recruitInfo.setDateModified(result.getLong("date_modified"));
			}
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"Could not update recruit info due to exception. " + e.getMessage());
		}finally{
			releaseConnection();
		}
		
		return recruitInfo;
	}
	
	@Override
	public RecruitInfo getRecruitInfoByUserId(int userId) throws WebApplicationException{
		RecruitInfo recruitInfo = new RecruitInfo();
		
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_info_by_user_id(?)");
			call.setInt(1, userId);
			ResultSet result = call.executeQuery();
			result.next();
			recruitInfo.setRecruitEngagementLevelId(result.getInt("recruit_engagement_level_id"));
			recruitInfo.setRecruitSourceId(result.getInt("recruit_source_id"));
			recruitInfo.setRushListUserId(result.getInt("rush_list_user_id"));
			recruitInfo.setGpa(result.getDouble("gpa"));
			recruitInfo.setClassStanding(result.getString("class_standing"));
			recruitInfo.setLifeExperiences(result.getString("life_experiences"));
			recruitInfo.setLookingFor(result.getString("looking_for"));
			recruitInfo.setExpectations(result.getString("expectations"));
			recruitInfo.setExtracurriculars(result.getString("extracurriculars"));
			recruitInfo.setDateCreated(result.getLong("date_created"));
			recruitInfo.setDateModified(result.getLong("date_modified"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return recruitInfo;
	}

	@Override
	public UserType[] getUserTypes() {
		ArrayList<UserType> userTypesList = new ArrayList<UserType>();
		try{
			CallableStatement call = con.prepareCall("CALL get_user_types()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				UserType userType = new UserType();
				userType.setUserTypeId(result.getInt("id"));
				userType.setName(result.getString("name"));
				userType.setDescription(result.getString("description"));
				userTypesList.add(userType);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return userTypesList.toArray(new UserType[userTypesList.size()]);
	}

}
