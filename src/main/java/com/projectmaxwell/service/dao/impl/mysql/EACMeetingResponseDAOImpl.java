package com.projectmaxwell.service.dao.impl.mysql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.exception.InvalidParameterException;
import com.projectmaxwell.exception.NoEACMeetingDefinedException;
import com.projectmaxwell.model.EACMeeting;
import com.projectmaxwell.service.dao.EACMeetingResponseDAO;

public class EACMeetingResponseDAOImpl extends AbstractMysqlDAOImpl implements
		EACMeetingResponseDAO {
	
	@Override
	public EACMeeting getNextEACMeeting() {
		try{
			EACMeeting response = new EACMeeting();
			CallableStatement call = con.prepareCall("CALL get_next_EAC_meeting(?)");
			int currentTime = (int)(System.currentTimeMillis()/1000);
			call.setInt(1, currentTime);
			ResultSet result = call.executeQuery();
			if(result.next()){
				Date d = new Date(result.getLong("date"));
				Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
				c.setTime(new Date(d.getTime() * 1000L));
				response.setDate(d.getTime());
				response.setCalendar(c);
				response.setGoogleMaps(result.getString("google_maps"));
				response.setName(result.getString("name"));
				response.setWebsite(result.getString("website"));
				response.setId(result.getInt("id"));
				return response;
			}else{
				throw new NoEACMeetingDefinedException("" + System.currentTimeMillis(), "No future EAC meetings defined at present.");
			}			
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();			
		}
	}

	@Override
	public EACMeeting createEACMeeting(EACMeeting meeting) {
		
		try{
			CallableStatement call = con.prepareCall("CALL create_EAC_meeting(?,?,?,?)");
			call.setString(1, meeting.getName());
			call.setString(2, meeting.getWebsite());
			call.setString(3, meeting.getGoogleMaps());
			call.setLong(4, meeting.getDate());
			
			ResultSet result = call.executeQuery();
			if(result.next()){
				Date d = new Date(meeting.getDate());
				Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
				c.setTime(new Date(d.getTime() * 1000L));
				meeting.setCalendar(c);
				meeting.setId(result.getInt("id"));
				
				return meeting;
			}else{
				throw new WebApplicationException();
			}	
		}catch(SQLException sqle){
			throw new WebApplicationException(sqle);
		}finally{
			releaseConnection();
		}
	}

	@Override
	public EACMeeting[] getEACMeetings(int numRows) {
		
		if(numRows < 0){
			throw new InvalidParameterException(String.valueOf(Math.random()),"Invalid value for query parameter 'numRows'.");
		}
		
		ArrayList<EACMeeting> meetings = new ArrayList<EACMeeting>();
		try {
			CallableStatement call = con.prepareCall("CALL get_eac_meetings(?)");
			call.setInt(1,numRows);			
			ResultSet result = call.executeQuery();
			
			
			while(result.next()){
				EACMeeting meeting = new EACMeeting();
				Date d = new Date(result.getLong("date"));
				Calendar c = Calendar.getInstance(TimeZone.getTimeZone("PST"));
				c.setTime(new Date(d.getTime() * 1000L));
				meeting.setCalendar(c);
				meeting.setDate(d.getTime());
				meeting.setGoogleMaps(result.getString("google_maps"));
				meeting.setName(result.getString("name"));
				meeting.setId(result.getInt("id"));
				meeting.setWebsite(result.getString("website"));
				meetings.add(0, meeting);
			}
		} catch (SQLException e) {
			new NoEACMeetingDefinedException(String.valueOf(Math.random()),"Could not retrieve EAC meetings due to exception.");
		}finally{
			releaseConnection();
		}
		
		return meetings.toArray(new EACMeeting[meetings.size()]);
	}

	
}
