package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.projectmaxwell.model.AssociateClass;
import com.projectmaxwell.service.dao.AssociateClassDAO;
import com.projectmaxwell.service.dao.AssociateClassDAOImpl;

@Path("/associateClasses")
@Produces(MediaType.APPLICATION_JSON)
public class AssociateClassResource {

	private AssociateClassDAO associateClassDAO;

	public AssociateClassResource(){
		associateClassDAO = new AssociateClassDAOImpl();
	}
	
	@GET
	public AssociateClass[] getAssociateClasses(){
		return associateClassDAO.getAssociateClasses();
	}
	
	@GET
	@Path("/{id}")
	public AssociateClass getAssociateClassById(@PathParam("id") int associateClassId){
		return associateClassDAO.getAssociateClassById(associateClassId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public AssociateClass createAssociateClass(AssociateClass associateClass){
		return associateClassDAO.createAssociateClass(associateClass);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public AssociateClass updateAssociateClass(AssociateClass associateClass, @PathParam("id") int associateClassId){
		return associateClassDAO.updateAssociateClass(associateClass);
	}
	
	@DELETE
	@Path("/{id}")
	public void updateAssociateClass(@PathParam("id") int associateClassId){
		associateClassDAO.deleteAssociateClassById(associateClassId);
	}
}
