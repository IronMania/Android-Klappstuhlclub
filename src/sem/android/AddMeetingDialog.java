package sem.android;

import java.sql.Date;
import java.util.Calendar;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddMeetingDialog extends Dialog implements OnClickListener {

	// for dialog box, which pops up when adding KlappstuhlclubMeetings
	private Button buttonOk;
	private Button buttonCancel;
	private EditText editText;
	private String txtNextMeeting;

	// for showing dialogs
	static final int DATE_DIALOG_ID = 0;
	static final int ADD_MEETING_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;
	Context mContext;

	// help for DialogTime/DatePicker
	private TextView mDateDisplay;
	private TextView mTimeDisplay;
	private Button buttonPickDate;
	private Button buttonPickTime;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.addmeetingdialog);
		   
		// getting Buttons and Boxes as Objects (from the inputForm)
			mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
			mTimeDisplay = (TextView) findViewById(R.id.TimeDisplay);
			buttonOk = (Button) findViewById(R.id.okButton);
			buttonCancel = (Button) findViewById(R.id.cancelButton);
			buttonPickDate = (Button) findViewById(R.id.pickDate);
			buttonPickTime = (Button) findViewById(R.id.pickTime);
			editText = (EditText) findViewById(R.id.edittext);

			// listener for all Buttons
			buttonOk.setOnClickListener(this);
			buttonCancel.setOnClickListener(this);
			buttonPickDate.setOnClickListener(this);
			buttonPickTime.setOnClickListener(this);

			// get the current date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// get the current time
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// // display the current date and Time to the corresponding fields
			updateDateDisplay();
			updateTimeDisplay();
		   
	   }
	
	public AddMeetingDialog(Context context) {
		super(context);
		mContext = context;
//		setContentView(R.layout.addmeetingdialog); // set Layout
		// TODO Auto-generated constructor stub
		

	}

	// updates the date in the TextView
	private void updateDateDisplay() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	// Form for changing the Date (not Time)
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	// updates the time we display in the TextView
	private void updateTimeDisplay() {
		mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));
	}

	// for displaying the time
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// the callback received when the user "sets" the time in the dialog
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();
		}
	};

	// listener for clicking. Mostly on Buttons
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == buttonPickDate)
			getOwnerActivity().showDialog(DATE_DIALOG_ID);
		if (v == buttonCancel)
			dismiss();
		if (v == buttonOk) {
			txtNextMeeting = editText.getText().toString();// TODO convert
															// editText to
															// String
			dismiss();
			// sendmeeting();
		}
		if (v == buttonPickTime)
			getOwnerActivity().showDialog(TIME_DIALOG_ID);

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {

		// dialog for adding Meeting
		case ADD_MEETING_DIALOG_ID:
			// d = new AddMeetingDialog(mContext);
//			d.setContentView(R.layout.addmeetingdialog);
			
			show();
			return this;

			// dialog for changing Time of new Meeting
		case TIME_DIALOG_ID:
			this.dismiss();
			return new TimePickerDialog(mContext, mTimeSetListener, mHour,
					mMinute, false);

			// dialog for changing Date of new Meeting
		case DATE_DIALOG_ID:
			this.dismiss();
			return new DatePickerDialog(mContext, mDateSetListener, mYear,
					mMonth, mDay);
		}
		return null;
	}

	// just to sum everything up for sending it
	protected void sendmeeting() {
		// preparing everything to send
		Calendar meetingTime = Calendar.getInstance();
		Date sendDate = new Date(mYear, mMonth, mDay);
		sendDate.setHours(mHour);
		sendDate.setMinutes(mMinute);

		// TODO these 2 Strings require Sending
		@SuppressWarnings("unused")
		String sendString = txtNextMeeting;
		meetingTime.setTime(sendDate);
		
		
		
	}
}