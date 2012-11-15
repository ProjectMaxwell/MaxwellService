package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.AcademicTerm;

public interface AcademicTermDAO {
	
	public AcademicTerm[] getAcademicTerms();
	
	public AcademicTerm getAcademicTermById(int academicTermId);
	
	public AcademicTerm createAcademicTerm(AcademicTerm academicTerm);
	
	public AcademicTerm updateAcademicTerm(AcademicTerm academicTerm);
	
	public void deleteAcademicTermById(int academicTermId);
}
