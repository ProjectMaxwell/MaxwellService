package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.WebApplicationException;

import org.objectweb.asm.Type;

import com.projectmaxwell.exception.InvalidParameterException;
import com.projectmaxwell.exception.MySQLException;
import com.projectmaxwell.model.RecruitComment;
import com.projectmaxwell.model.RecruitEngagementLevel;
import com.projectmaxwell.model.RecruitInfo;
import com.projectmaxwell.service.dao.RecruitDAO;

public class RecruitDAOImpl extends AbstractMysqlDAOImpl implements RecruitDAO {

	public RecruitEngagementLevel[] getRecruitEngagementLevels(){
		ArrayList<RecruitEngagementLevel> levelsList = new ArrayList<RecruitEngagementLevel>();
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_engagement_levels()");
			ResultSet result = call.executeQuery();
			while(result.next()){
				RecruitEngagementLevel level = new RecruitEngagementLevel();
				level.setRecruitEngagementLevelId(result.getInt("id"));
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
	
	@Override
	public RecruitInfo createRecruitInfo(RecruitInfo recruitInfo, int userId){
		validator.validateRecruitInfo(recruitInfo);
		
		try {
			CallableStatement call = con.prepareCall("CALL create_recruit_info(?,?,?,?,?,?,?,?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitInfo.getRecruitSourceId());
			call.setInt(3, recruitInfo.getRecruitEngagementLevelId());
			if(recruitInfo.getRushListUserId() != null){
				call.setInt(4, recruitInfo.getRushListUserId());
			}else{
				call.setNull(4, Type.INT);
			}
			if(recruitInfo.getGpa() != null){
				call.setDouble(5, recruitInfo.getGpa());
			}else{
				call.setNull(5, Type.DOUBLE);
			}
			call.setString(6, recruitInfo.getClassStanding());
			call.setString(7, recruitInfo.getLifeExperiences());
			call.setString(8, recruitInfo.getLookingFor());
			call.setString(9, recruitInfo.getExpectations());
			call.setString(10, recruitInfo.getExtracurriculars());
			ResultSet result = call.executeQuery();
			if(result.next()){
				recruitInfo.setDateCreated(result.getLong("date_created"));
			}
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"Could not create recruit info due to exception. " + e.getMessage());
		}finally{
			releaseConnection();
		}
		return recruitInfo;
	}

	@Override
	public RecruitInfo updateRecruitInfo(RecruitInfo recruitInfo, int userId) {
		validator.validateRecruitInfo(recruitInfo);
		try {
			CallableStatement call = con.prepareCall("CALL update_recruit_info_by_user_id(?,?,?,?,?,?,?,?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitInfo.getRecruitSourceId());
			call.setInt(3, recruitInfo.getRecruitEngagementLevelId());
			if(recruitInfo.getRushListUserId() != null){
				call.setInt(4, recruitInfo.getRushListUserId());
			}else{
				call.setNull(4, Type.INT);
			}
			if(recruitInfo.getGpa() != null){
				call.setDouble(5, recruitInfo.getGpa());
			}else{
				call.setNull(5, Type.DOUBLE);
			}
			call.setString(6, recruitInfo.getClassStanding());
			call.setString(7, recruitInfo.getLifeExperiences());
			call.setString(8, recruitInfo.getLookingFor());
			call.setString(9, recruitInfo.getExpectations());
			call.setString(10, recruitInfo.getExtracurriculars());
			ResultSet result = call.executeQuery();
			if(result.next()){
				recruitInfo.setDateModified(result.getLong("date_modified"));
			}
		} catch (SQLException e) {
			throw new MySQLException(String.valueOf(Math.random()),"Could not update recruit info due to exception. " + e.getMessage());
		}finally{
			releaseConnection();
		}
		
		return recruitInfo;
	}
	
	@Override
	public RecruitInfo getRecruitInfoByUserId(int userId) throws WebApplicationException{
		RecruitInfo recruitInfo = new RecruitInfo();
		
		try{
			CallableStatement call = con.prepareCall("CALL get_recruit_info_by_user_id(?)");
			call.setInt(1, userId);
			ResultSet result = call.executeQuery();
			result.next();
			recruitInfo.setRecruitEngagementLevelId(result.getInt("recruit_engagement_level_id"));
			recruitInfo.setRecruitSourceId(result.getInt("recruit_source_id"));
			recruitInfo.setRushListUserId(result.getInt("rush_list_user_id"));
			recruitInfo.setGpa(result.getDouble("gpa"));
			recruitInfo.setClassStanding(result.getString("class_standing"));
			recruitInfo.setLifeExperiences(result.getString("life_experiences"));
			recruitInfo.setLookingFor(result.getString("looking_for"));
			recruitInfo.setExpectations(result.getString("expectations"));
			recruitInfo.setExtracurriculars(result.getString("extracurriculars"));
			recruitInfo.setDateCreated(result.getLong("date_created"));
			recruitInfo.setDateModified(result.getLong("date_modified"));
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return recruitInfo;
	}

	@Override
	public RecruitComment addRecruitComment(RecruitComment recruitComment,
			int userId) {
		validator.validateRecruitComment(recruitComment);
		if(recruitComment.getRecruitUserId() != userId){
			throw new InvalidParameterException(String.valueOf(Math.random()),
					"userId in URI did not match recruitUserId field on object.");
		}
		recruitComment.setRecruitCommentId(69);
		recruitComment.setDateCreated((int)(System.currentTimeMillis() / 1000));
		try{
			CallableStatement call = con.prepareCall("CALL add_recruit_comment(?,?,?)");
			call.setInt(1, userId);
			call.setInt(2, recruitComment.getCommenterUserId());
			call.setString(3, recruitComment.getComment());
			ResultSet result = call.executeQuery();
			if(result.next()){
				recruitComment.setRecruitCommentId(result.getInt("id"));
				recruitComment.setDateCreated(result.getInt("date_created"));
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return recruitComment;
	}

	@Override
	public RecruitComment[] getRecruitCommentsByRecruitUserId(int userId) {
		ArrayList<RecruitComment> recruitCommentsList = new ArrayList<RecruitComment>();
		try{
			CallableStatement call = con.prepareCall("CALL get_comments_by_recruit_user_id(?)");
			call.setInt(1, userId);
			ResultSet result = call.executeQuery();
			while(result.next()){
				RecruitComment comment = new RecruitComment();
				comment.setRecruitCommentId(result.getInt("id"));
				comment.setRecruitUserId(userId);
				comment.setCommenterUserId(result.getInt("commenter_user_id"));
				comment.setDateCreated(result.getInt("date_created"));
				comment.setComment(result.getString("comment"));
				recruitCommentsList.add(comment);
			}
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
		return recruitCommentsList.toArray(new RecruitComment[recruitCommentsList.size()]);
	}
}
