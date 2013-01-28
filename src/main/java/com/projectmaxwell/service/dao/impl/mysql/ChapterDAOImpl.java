package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.projectmaxwell.exception.MySQLException;
import com.projectmaxwell.model.Chapter;
import com.projectmaxwell.service.dao.ChapterDAO;

public class ChapterDAOImpl extends AbstractMysqlDAOImpl implements ChapterDAO {

	@Override
	public Chapter[] getChapters() {
		try {
			CallableStatement call = con.prepareCall("CALL get_chapters()");
			ResultSet result = call.executeQuery();
			ArrayList<Chapter> chapterList = new ArrayList<Chapter>();
			while(result.next()){
				Chapter chapter = new Chapter();
				chapter.setChapterId(result.getInt("id"));
				chapter.setName(result.getString("name"));
				chapter.setSchool(result.getString("school"));
				boolean isActive = result.getBoolean("active");
				if(!result.wasNull()){
					chapter.setActive(isActive);
				}
				chapterList.add(chapter);
			}			
			return chapterList.toArray(new Chapter[chapterList.size()]);
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"Could not retrieve list of chapters due to mysql exception: " + e.getMessage());
		}
	}

}
