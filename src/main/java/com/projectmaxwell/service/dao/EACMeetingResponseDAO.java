package com.projectmaxwell.service.dao;

import com.projectmaxwell.model.EACMeeting;

public interface EACMeetingResponseDAO {

	EACMeeting getNextEACMeeting();

	EACMeeting createEACMeeting(EACMeeting meeting);

	EACMeeting[] getEACMeetings(int numRows);

}
