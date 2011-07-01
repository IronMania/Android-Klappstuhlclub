package sem.android;

import java.util.*;

public class MessageMeeting {

	private String meetingText;
	private double longitude;
	private double latitude;
	private Calendar date;
	public String getMeetingText() {
		return meetingText;
	}
	public void setMeetingText(String meetingText) {
		this.meetingText = meetingText;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
