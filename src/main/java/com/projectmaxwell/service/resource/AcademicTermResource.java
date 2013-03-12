package com.projectmaxwell.service.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.projectmaxwell.model.AcademicTerm;
import com.projectmaxwell.service.dao.AcademicTermDAO;
import com.projectmaxwell.service.dao.impl.mysql.AcademicTermDAOImpl;
import com.projectmaxwell.util.PhiAuthClient;

@Path("/academicTerms")
@Produces(MediaType.APPLICATION_JSON)
public class AcademicTermResource extends AbstractResource{

	private AcademicTermDAO academicTermDAO;

	public AcademicTermResource(){
		academicTermDAO = new AcademicTermDAOImpl();
		PhiAuthClient.setHostname("http://evergreenalumniclub.com:7080/PhiAuth/rest");
	}
	
	@GET
	@RolesAllowed({"view_system_metadata"})
	public AcademicTerm[] getAcademicTerms(@HeaderParam("Authorization") String token){
		return academicTermDAO.getAcademicTerms();
	}
	
	@GET
	@Path("/{id}")
	@RolesAllowed({"view_system_metadata"})
	public AcademicTerm getAcademicTermById(@PathParam("id") int academicTermId){
		return academicTermDAO.getAcademicTermById(academicTermId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"CUD_system_metadata"})
	public AcademicTerm createAcademicTerm(AcademicTerm academicTerm){
		return academicTermDAO.createAcademicTerm(academicTerm);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@RolesAllowed({"CUD_system_metadata"})
	public AcademicTerm updateAcademicTerm(AcademicTerm academicTerm, @PathParam("id") int academicTermId){
		return academicTermDAO.updateAcademicTerm(academicTerm);
	}
	
	@DELETE
	@Path("/{id}")
	@RolesAllowed({"CUD_system_metadata"})
	public void deleteAcademicTerm(@PathParam("id") int academicTermId){
		academicTermDAO.deleteAcademicTermById(academicTermId);
	}
}
