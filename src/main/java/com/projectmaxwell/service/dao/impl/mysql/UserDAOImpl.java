package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.ws.rs.WebApplicationException;

import org.objectweb.asm.Type;

import com.projectmaxwell.exception.MissingUserFieldException;
import com.projectmaxwell.exception.MySQLException;
import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.model.User;
import com.projectmaxwell.model.UserType;
import com.projectmaxwell.service.dao.UserDAO;
import com.projectmaxwell.service.dao.impl.mysql.AbstractMysqlDAOImpl;

public class UserDAOImpl extends AbstractMysqlDAOImpl implements UserDAO {

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
			user.setUserId(userId);
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
//			releaseConnection();			
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
				user.setAssociateClassId(result.getInt("associate_class_id"));
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
	public User updateUser(User newUser) throws WebApplicationException {
		if(newUser == null || newUser.getUserId() == null || newUser.getUserId() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
					"Required field 'userId' is null or empty");
		}
		User oldUser = getUserById(newUser.getUserId());
		
		validator.validateUserUpdate(newUser, oldUser);
		
		try{
			CallableStatement call = con.prepareCall("CALL `update_user_by_id`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			call.setString(1, newUser.getFirstName());
			call.setString(2, newUser.getLastName());
			call.setString(3, newUser.getEmail());
			call.setObject(4, newUser.getDateOfBirth());
			call.setInt(5, newUser.getYearInitiated());
			call.setInt(6, newUser.getYearGraduated());
			call.setInt(7, newUser.getPin());
			call.setInt(8, newUser.getAssociateClassId());
			if(newUser.getChapterId() > 0){
				call.setInt(9, newUser.getChapterId());
			}else{
				call.setNull(9, Type.INT);
			}
			call.setInt(10, newUser.getUserTypeId());
			call.setInt(11, newUser.getUserId());
			ResultSet result = call.executeQuery();
			result.next();
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return newUser;
	}
	
	@Override
	public RecruitInfo createRecruitInfo(RecruitInfo recruitInfo, int userId){
		validator.validateRecruitInfo(recruitInfo);
		
		try {
			CallableStatement call = con.prepareCall("CALL create_recruit_info(?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitInfo.getRecruitSourceId());
			call.setInt(3, recruitInfo.getRecruitEngagementLevelId());
			
			call.execute();
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"MySQL threw exception: " + e.getMessage());
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
