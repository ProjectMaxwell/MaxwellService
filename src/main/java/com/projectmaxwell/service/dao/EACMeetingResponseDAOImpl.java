package com.projectmaxwell.service.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.WebApplicationException;

import com.projectmaxwell.exception.NoEACMeetingDefinedException;
import com.projectmaxwell.model.EACMeeting;

public class EACMeetingResponseDAOImpl extends AbstractDAOImpl implements
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
			call.setInt(4, meeting.getDate());
			
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

	
}
