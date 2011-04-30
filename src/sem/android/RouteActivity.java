package sem.android;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//first Tab. for navigation to next meeting place
public class RouteActivity extends Map {
	private MapView myMap;
	private LocationManager locManager;
	private LocationListener locListener;
	private GeoPoint nextMeeting= null;


	@Override
	public void startup() {
		myMap = getMapView();
		
		initLocationManager();
		
	}

	
	/**
	 * Initialise the location manager.
	 */
	private void initLocationManager() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 
		locListener = new LocationListener() { 
			public void onLocationChanged(Location newLocation) {
				createAndShowMyItemizedOverlay(newLocation);
			}
 			public void onProviderDisabled(String arg0) {} 
			public void onProviderEnabled(String arg0) {} 
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);
 
	}
	
	/**
	 * This method will bea called whenever a cahnge of the current position
	 * is submitted via the GPS.
	 * @param newLocation
	 */
	protected void createAndShowMyItemizedOverlay(Location newLocation) {
		List<Overlay> overlays = myMap.getOverlays();
 
		// first remove old overlay
		if (overlays.size() > 0) {
			for (Iterator<Overlay> iterator = overlays.iterator(); iterator.hasNext();) {
				iterator.next();
				iterator.remove();
			}
		}
 
		// transform the location to a geopoint
		GeoPoint geopoint = new GeoPoint(
				(int) (newLocation.getLatitude() * 1E6), (int) (newLocation
						.getLongitude() * 1E6));
 
		// initialize icon
		Drawable icon = getResources().getDrawable(R.drawable.person);
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon
				.getIntrinsicHeight());
 
		// create my overlay and show it
		MapItemOverlay overlay = new MapItemOverlay(icon);
		OverlayItem item = new OverlayItem(geopoint, "My Location", null);
		overlay.addItem(item);
		myMap.getOverlays().add(overlay);
		
		// move to location
		myMap.getController().animateTo(geopoint);
		
		drawNextMeeting();
		// redraw map
		myMap.postInvalidate();
	}
	
	protected void drawNextMeeting(){
		if (nextMeeting==null) nextMeeting = findNextMeeting();
		
		// create my overlay and show it
		Drawable icon = getResources().getDrawable(R.drawable.klapp);
		MapItemOverlay overlay = new MapItemOverlay(icon);
		OverlayItem item = new OverlayItem(nextMeeting, "My Location", null);
		overlay.addItem(item);
		myMap.getOverlays().add(overlay);
	}
	
	protected GeoPoint findNextMeeting(){
		GeoPoint geopoint = new GeoPoint(38422006, -122084095);
		return geopoint;
	}
}
