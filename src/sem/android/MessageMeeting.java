package sem.android;

import java.util.*;

public class MessageMeeting {

	private String meetingText;
	private float longitude;
	private float latitude;
	private Calendar date;
	public String getMeetingText() {
		return meetingText;
	}
	public void setMeetingText(String meetingText) {
		this.meetingText = meetingText;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
