package sem.android;

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
import android.widget.Toast;

public class NewActivity extends MapActivity {
	// for geopoints
	MapView mapView;
	MapController mc;
	GeoPoint newMeetingPoint;

	// for my Overlay Icon
	List<Overlay> mapOverlays;
	Drawable drawable;
	MapOverlay itemizedOverlay;
	
	//for dialog
	Dialog dialog;
	AddMeetingDialog customizeDialog;
	
	//for switching
	static final int DATE_DIALOG_ID = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mc = mapView.getController();

		// creating an geopoint for testing
		String coordinates[] = { "1.352566007", "103.78921587" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);
		//
		newMeetingPoint = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		drawSelectedGeopoint();
		
		customizeDialog = new AddMeetingDialog(this);
		
		
	}
	private void drawSelectedGeopoint() {
		// create my overlay item (icon)
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.pushpin1);
		itemizedOverlay = new MapOverlay(drawable);

		// add Overlay Item to a Geopoint and show it
		mapOverlays.clear();
		OverlayItem overlayitem = new OverlayItem(newMeetingPoint, "ha", "haha");
		itemizedOverlay.addItem(overlayitem);
		mapOverlays.add(itemizedOverlay);
		mapView.invalidate();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

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
						(int) event.getX(), (int) event.getY());

				Toast.makeText(
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

	private void addMeeting() {
		
		customizeDialog.show();
//		 showDialog(ADD_MEETING_DIALOG_ID);
	}

	private void showHelp() {
	}


	
}
