package sem.android;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewActivity extends MapActivity implements OnClickListener {
	// for geopoints
	private MapView mapView;
	@SuppressWarnings("unused")
	private MapController mc;
	private GeoPoint newMeetingPoint;

	// for my Overlay Icon
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MapOverlay itemizedOverlay;

	// for dialog box, which pops up when adding KlappstuhlclubMeetings
//	private Dialog dialog;
	private Button buttonOk;
	private Button buttonCancel;
	private EditText editText;
	private String txtNextMeeting;

	// for showing dialogs
	static final int DATE_DIALOG_ID = 0;
	static final int ADD_MEETING_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;

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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map); 		//Set the layout to show google Maps
		mapView = (MapView) findViewById(R.id.mapview); //get Mapview from current window
		mapView.setBuiltInZoomControls(true);  //adding Zoom to google maps
		mc = mapView.getController(); // creating MapController

		// creating an geopoint for testing
		//TODO comment out
		String coordinates[] = { "1.352566007", "103.78921587" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);
		newMeetingPoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		drawSelectedGeopoint();

			}

	// updates the date in the TextView
	private void updateDateDisplay() {
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	//Form for changing the Date (not Time)
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
	//for displaying the time
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

	//drawing the icon to select the next 
	private void drawSelectedGeopoint() {
		// create my overlay item (icon)
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.pushpin1); //get the puspin icon as drawable
		itemizedOverlay = new MapOverlay(drawable); //create overlayitem with icon

		// add Overlay Item to a Geopoint and show it
		mapOverlays.clear(); //clear the last Overlay
		OverlayItem overlayitem = new OverlayItem(newMeetingPoint, "ha", "haha"); //setting Overlay Icon to new Meetingpoint 
		itemizedOverlay.addItem(overlayitem);
		mapOverlays.add(itemizedOverlay); //add Overlayitem to Overlay array
		mapView.invalidate(); //draw the overlays again
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	//extende class for Overlays. Can trigger events and save GeoPoints
	// own class. requires other touch event
	class MapOverlay extends MapItemOverlay {
		public MapOverlay(Drawable defaultMarker) {
			super(defaultMarker);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {
			// ---when user lifts his finger---
			if (event.getAction() == 1) {
				newMeetingPoint = mapView.getProjection().fromPixels(
						(int) event.getX(), (int) event.getY());//saves point as Geopoint

				Toast.makeText(//shows Latitude and Longitude on map
						getBaseContext(),
						newMeetingPoint.getLatitudeE6() / 1E6 + ","
								+ newMeetingPoint.getLongitudeE6() / 1E6,
						Toast.LENGTH_SHORT).show();
				drawSelectedGeopoint();
			}
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.newmenubar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.add_meeting:
			addMeeting();
			return true;
		case R.id.help:
			showHelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	//add Meeting is called by Option Menu
	private void addMeeting() {
		showDialog(ADD_MEETING_DIALOG_ID); //enables dialog to input new Meeting
		// showDialog(ADD_MEETING_DIALOG_ID);
	}

	private void showHelp() {
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		//dialog for adding Meeting
		case ADD_MEETING_DIALOG_ID:
			// creating the MeetingDialog
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.addmeetingdialog); //set Layout

			// getting Buttons and Boxes as Objects (from the inputForm)
			mDateDisplay = (TextView) dialog.findViewById(R.id.dateDisplay);
			mTimeDisplay = (TextView) dialog.findViewById(R.id.TimeDisplay);
			buttonOk = (Button) dialog.findViewById(R.id.okButton);
			buttonCancel = (Button) dialog.findViewById(R.id.cancelButton);
			buttonPickDate = (Button) dialog.findViewById(R.id.pickDate);
			buttonPickTime = (Button) dialog.findViewById(R.id.pickTime);
			editText = (EditText) dialog.findViewById(R.id.edittext);

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

			dialog.show();
			return null;
			
			//dialog for changing Time of new Meeting
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
			
			//dialog for changing Date of new Meeting
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	//listener for clicking. Mostly on Buttons
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == buttonPickDate)
			showDialog(DATE_DIALOG_ID); 
		if (v == buttonCancel) showDialog(4);
		if (v == buttonOk) {
			
			txtNextMeeting = editText.getText().toString();// TODO convert
															// editText to
															// String
			showDialog(4);
			sendmeeting();
		}
		if (v == buttonPickTime)
			showDialog(TIME_DIALOG_ID);

	}

	//just to sum everything up for sending it
	@SuppressWarnings("unused")
	protected void sendmeeting() {
		// preparing everything to send
		GregorianCalendar meetingTime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute, 0);
		
		// TODO these 2 Strings require Sending
		String sendString = txtNextMeeting;
		sparqlApi test = new sparqlApi(this);

	}
}
