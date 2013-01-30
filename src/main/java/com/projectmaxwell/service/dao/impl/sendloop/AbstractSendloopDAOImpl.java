package com.projectmaxwell.service.dao.impl.sendloop;

import com.projectmaxwell.service.dao.impl.AbstractDAOImpl;

public abstract class AbstractSendloopDAOImpl extends AbstractDAOImpl {

	protected SendloopClient client;
	
	public AbstractSendloopDAOImpl(){
		client = new SendloopClient();
	}
}
