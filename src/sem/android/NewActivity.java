package sem.android;

import java.util.GregorianCalendar;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Toast;

public class NewActivity extends MapActivity {
	// for geopoints
	private MapView mapView;
	@SuppressWarnings("unused")
	private MapController mc;
	private GeoPoint newMeetingPoint;

	// for my Overlay Icon
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MapOverlay itemizedOverlay;

	//for dialog box the text
	private String txtNextMeeting;

	// for showing dialogs
	static final int DATE_DIALOG_ID = 0;
	static final int ADD_MEETING_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;

	// help for DialogTime/DatePicker
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map); // Set the layout to show google Maps
		mapView = (MapView) findViewById(R.id.mapview); // get Mapview from
														// current window
		mapView.setBuiltInZoomControls(true); // adding Zoom to google maps
		mc = mapView.getController(); // creating MapController

		// creating an geopoint for testing
		// TODO comment out
		String coordinates[] = { "1.352566007", "103.78921587" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);
		newMeetingPoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		drawSelectedGeopoint();

	}

	// for displaying the time
//	private static String pad(int c) {
//		if (c >= 10)
//			return String.valueOf(c);
//		else
//			return "0" + String.valueOf(c);
//	}

	// drawing the icon to select the next
	private void drawSelectedGeopoint() {
		// create my overlay item (icon)
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.pushpin1); // get the pushpin icon as drawable

		itemizedOverlay = new MapOverlay(drawable); // create overlayitem with
													// icon

		// add Overlay Item to a Geopoint and show it
		mapOverlays.clear(); // clear the last Overlay
		OverlayItem overlayitem = new OverlayItem(newMeetingPoint, "ha", "haha"); // setting Overlay Icon to new MeetingPoint
 
		itemizedOverlay.addItem(overlayitem);
		mapOverlays.add(itemizedOverlay); // add Overlayitem to Overlay array
		mapView.invalidate(); // draw the overlays again
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// extende class for Overlays. Can trigger events and save GeoPoints
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
				newMeetingPoint = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());// saves																					// Geopoint

				Toast.makeText(
						// shows Latitude and Longitude on map
						getBaseContext(),
						newMeetingPoint.getLatitudeE6() / 1E6 + "," + newMeetingPoint.getLongitudeE6() / 1E6,
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

	// add Meeting is called by Option Menu
	private void addMeeting() {
		showDialog(ADD_MEETING_DIALOG_ID); // enables dialog to input new
											// Meeting
		// showDialog(ADD_MEETING_DIALOG_ID);
	}

	private void showHelp() {
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		
		switch (id) {
		// dialog for adding Meeting
		case ADD_MEETING_DIALOG_ID:
			
			// creating the MeetingDialog
			final AddMeetingDialog dialog = new AddMeetingDialog(this);
			dialog.setContentView(R.layout.addmeetingdialog); // set Layout
			
			dialog.show();

			// listener for all Buttons
			dialog.getButtonOk().setOnClickListener(new OnClickListener() {
				
				//@Override
				public void onClick(View v) {
					
					System.out.println("--onclick--");
					mYear = dialog.getmYear();
					mMonth = dialog.getmMonth();
					mDay = dialog.getmDay();
					
					mHour = dialog.getmHour();
					mMinute = dialog.getmMinute();
					
					txtNextMeeting = dialog.getEditText().toString();
					
					sendmeeting();
					dialog.dismiss();
				}
			});
			
			return null;
		}
		return null;
	}

	// just to sum everything up for sending it
	@SuppressWarnings("unused")
	protected void sendmeeting() {
		// preparing everything to send
		GregorianCalendar meetingTime = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute, 0);

		// TODO these 2 Strings require Sending
		String sendString = txtNextMeeting;
		sparqlApi test = new sparqlApi();
		// newMeetingPoint;

	}
}
