package com.projectmaxwell.model.validation;

import com.projectmaxwell.exception.InvalidUserIdException;
import com.projectmaxwell.exception.MissingUserFieldException;
import com.projectmaxwell.model.User;

public class ModelValidator {

	//TODO: Replace Math.random with actual errorIds that are logged.
	public void validateUserCreation(User user) throws InvalidUserIdException {
		if(user.getUserId() != null){
			throw new InvalidUserIdException(String.valueOf(Math.random()),
					"userId '" + user.getUserId() + "' is invalid.  " +
					"userId must be null during user creation.");
		}
		if(user.getFirstName() == null || user.getFirstName().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'firstName' is null or empty");
		}
	}
	
	/**
	 * This method actually allows for comparison of old and new fields
	 * However this isn't being used presently, any field is modifiable
	 * @param newUserData
	 * @param oldUserData
	 */
	public void validateUserUpdate(User newUserData, User oldUserData){
		if(newUserData.getUserId() == null || newUserData.getUserId().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
					"Required field 'userId' is null or empty");
		}
		if(newUserData.getUserId() != oldUserData.getUserId()){
			throw new InvalidUserIdException(String.valueOf(Math.random()),
					"userId '" + newUserData.getUserId() + "' is invalid.  " +
					"userId in request body must match userId in URI.");
		}
		if(newUserData.getFirstName() == null || newUserData.getFirstName().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'firstName' is null or empty");
		}
	}

}
