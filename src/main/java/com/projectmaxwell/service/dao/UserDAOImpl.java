package com.projectmaxwell.service.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.exception.MissingUserFieldException;
import com.projectmaxwell.model.User;

public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {

	public UserDAOImpl(){
		super();
	}
	
	@Override
	public User getUserById(String userId) throws WebApplicationException{
		User user = new User();
		user.setUserId(userId);
		
		try{
			CallableStatement call = con.prepareCall("CALL get_user_by_id(?)");
			call.setString(1, userId);
			ResultSet result = call.executeQuery();
			result.next();
			user.setFirstName(result.getString("first_name"));
			user.setLastName(result.getString("last_name"));
			user.setEmail(result.getString("email"));
			user.setAssociateClassId(result.getInt("associate_class_id"));
			user.setChapter(result.getString("chapter"));
			user.setDateOfBirth(result.getDate("date_of_birth"));
			user.setPin(result.getInt("pin"));
			user.setUserStatus(result.getInt("user_status"));
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
	public User[] getUsers() throws WebApplicationException{
		HashSet<User> userSet = new HashSet<User>();
		try{
			CallableStatement call = con.prepareCall("CALL get_users");
			ResultSet result = call.executeQuery();
			while(result.next()){
				User user = new User();
				user.setUserId(result.getString("id"));
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
		validator.validateUserCreation(user);
		try{
			CallableStatement call = con.prepareCall("CALL create_user(?,?,?,?,?,?,?,?,?,?)");
			call.setString(1, user.getFirstName());
			call.setString(2, user.getLastName());
			call.setString(3, user.getEmail());
			call.setObject(4, user.getDateOfBirth());
			call.setInt(5, user.getYearInitiated());
			call.setInt(6, user.getYearGraduated());
			call.setInt(7, user.getPin());
			call.setInt(8, user.getAssociateClassId());
			call.setString(9, user.getChapter());
			call.setInt(10, user.getUserStatus());
			ResultSet result = call.executeQuery();
			result.next();
			user.setUserId(result.getString("id"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return user;
	}
	
	@Override
	public User updateUser(User newUser) throws WebApplicationException {
		if(newUser == null || newUser.getUserId() == null || newUser.getUserId().length() < 1){
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
			call.setString(9, newUser.getChapter());
			call.setInt(10, newUser.getUserStatus());
			call.setString(11, newUser.getUserId());
			ResultSet result = call.executeQuery();
			result.next();
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return newUser;
	}

}
