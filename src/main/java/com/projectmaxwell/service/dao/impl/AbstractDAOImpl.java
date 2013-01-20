package com.projectmaxwell.service.dao.impl;

import com.projectmaxwell.model.validation.ModelValidator;

public abstract class AbstractDAOImpl {

	protected ModelValidator validator;

	public AbstractDAOImpl() {
		super();
		validator = new ModelValidator();
	}
	
	
}
