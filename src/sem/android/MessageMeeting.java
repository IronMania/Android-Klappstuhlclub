package sem.android;

import java.util.Calendar;

public class MessageMeeting {

	private String meetingLabel;
	private String meetingComment;
	private double longitude;
	private double latitude;
	private Calendar date;
	
	
	public String getMeetingComment() {
		return meetingComment;
	}
	public void setMeetingComment(String meetingText) {
		this.meetingComment = meetingText;
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
	public String getMeetingLabel() {
		return meetingLabel;
	}
	public void setMeetingLabel(String meetingNumber) {
		this.meetingLabel = meetingNumber;
	}
}
