package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.projectmaxwell.exception.MySQLException;
import com.projectmaxwell.model.RecruitEngagementLevel;
import com.projectmaxwell.service.dao.RecruitDAO;

public class RecruitDAOImpl extends AbstractMysqlDAOImpl implements RecruitDAO {

	public RecruitEngagementLevel[] getRecruitEngagementLevels(){
		ArrayList<RecruitEngagementLevel> levelsList = new ArrayList<RecruitEngagementLevel>();
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_engagement_levels()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				RecruitEngagementLevel level = new RecruitEngagementLevel();
				level.setId(result.getInt("id"));
				level.setEngagementLevel(result.getString("engagement_level"));
				level.setDescription(result.getString("description"));
				levelsList.add(level);
			}
		}catch(SQLException sqle){
			throw new MySQLException(String.valueOf(Math.random()),
					"Could not retrieve list of recruit engagement levels due to mysql exception: " + sqle.getMessage());
		}finally{
			releaseConnection();
		}
		return levelsList.toArray(new RecruitEngagementLevel[levelsList.size()]);
	}
}
