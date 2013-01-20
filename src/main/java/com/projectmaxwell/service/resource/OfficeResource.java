package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.Office;
import com.projectmaxwell.service.dao.OfficeDAO;
import com.projectmaxwell.service.dao.impl.mysql.OfficeDAOImpl;

@Path("/offices")
@Produces("application/json")
@Consumes("application/json")
public class OfficeResource extends AbstractResource {

	OfficeDAO officeDao;
	
	public OfficeResource(){
		officeDao = new OfficeDAOImpl();
	}
	
	@GET
	public Office[] getOffices(){
		return officeDao.getOffices(true);
	}
}
