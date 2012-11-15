package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.AssociateClass;

public interface AssociateClassDAO {
	
	public AssociateClass[] getAssociateClasses();
	
	public AssociateClass getAssociateClassById(int associateClassId);
	
	public AssociateClass createAssociateClass(AssociateClass associateClass);
	
	public AssociateClass updateAssociateClass(AssociateClass associateClass);
	
	public void deleteAssociateClassById(int associateClassId);
}
