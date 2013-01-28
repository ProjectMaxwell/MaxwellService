package com.projectmaxwell.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.projectmaxwell.model.Chapter;
import com.projectmaxwell.service.dao.ChapterDAO;
import com.projectmaxwell.service.dao.impl.mysql.ChapterDAOImpl;

@Consumes("application/json")
@Produces("application/json")
@Path("/chapters")
public class ChapterResource extends AbstractResource {
	
	ChapterDAO chapterDAO;
	
	public ChapterResource(){
		super();
		chapterDAO = new ChapterDAOImpl();
	}
	
	@GET
	public Chapter[] getChapters(){
		return chapterDAO.getChapters();
	}
}
