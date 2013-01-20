package com.projectmaxwell.model.validation;

import com.projectmaxwell.exception.InvalidParameterException;
import com.projectmaxwell.exception.MissingUserFieldException;
import com.projectmaxwell.model.User;

public class ModelValidator {

	//TODO: Replace Math.random with actual errorIds that are logged.
	public void validateUserCreation(User user) throws InvalidParameterException {
		if(user.getUserId() != null){
			throw new InvalidParameterException(String.valueOf(Math.random()),
					"userId '" + user.getUserId() + "' is invalid.  " +
					"userId must be null during user creation.");
		}
		if(user.getFirstName() == null || user.getFirstName().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'firstName' is null or empty");
		}
		if(user.getLastName() == null || user.getLastName().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'lastName' is null or empty");
		}
		switch(user.getUserType()){
		//For 
		case 1:
			validateAssociateUserCreation(user);
		case 2:
			validateInitiateUserCreation(user);
			break;
		case 3:
			validateAlumniUserCreation(user);
			break;
		case 5:
			validateRecruitUserCreation(user);
			break;
		default:
			throw new InvalidParameterException(String.valueOf(Math.random()), "User Type must be valid value.");
		}
	}
	
	private void validateInitiateUserCreation(User user) {
		//DO initiate specific stuff here
		validateInitiateUserCreation(user);
		
	}

	private void validateAssociateUserCreation(User user) {
		//DO associate specific stuff here
		validateUndergradUserCreation(user);		
	}

	private void validateRecruitUserCreation(User user) {
		if(user.getAssociateClassId() > 0){
			throw new InvalidParameterException(String.valueOf(Math.random()),
					"Recruits may not be assigned to associate classes.  " +
					"If this is an associate account, please use the appropriate user type.");
		}
		if(user.getPin() > 0){
			throw new InvalidParameterException(String.valueOf(Math.random()),
					"Recruits may not have pin numbers.  " +
					"If this is an initiate account, please use the appropriate user type.");
		}
	}

	private void validateAlumniUserCreation(User user) {
		throw new InvalidParameterException(String.valueOf(Math.random()), "Creation of alumni is not yet supported.");
	}

	public void validateUndergradUserCreation(User user){
		if(user.getAssociateClassId() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'associateClassId' did not map to a valid associate class");	
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
			throw new InvalidParameterException(String.valueOf(Math.random()),
					"userId '" + newUserData.getUserId() + "' is invalid.  " +
					"userId in request body must match userId in URI.");
		}
		if(newUserData.getFirstName() == null || newUserData.getFirstName().length() < 1){
			throw new MissingUserFieldException(String.valueOf(Math.random()),
			"Required field 'firstName' is null or empty");
		}
	}

}
