package com.projectmaxwell.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class AssociateClass {

	private int associateClassId;
	private String name;
	
	public int getAssociateClassId() {
		return associateClassId;
	}
	
	public void setAssociateClassId(int associateClassId) {
		this.associateClassId = associateClassId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
