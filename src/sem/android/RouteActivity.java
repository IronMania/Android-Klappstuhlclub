package sem.android;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//first Tab. for navigation to next meeting place
public class RouteActivity extends Map {
	private MapView mapView;
	private MapController mc;
	private MapItemOverlay mio;
	private Drawable ownDrawable;
	private Drawable klappDrawable;
	List<Overlay> mapOverlays;

	private void makeUseOfNewLocation(Location location) {
		// TODO Auto-generated method stub
//		GeoPoint point = this.calculateGeoPoint(location.getLatitude(), location.getLongitude());
//		GeoPoint point = new GeoPoint(19240000, -99120000);
		GeoPoint point = new GeoPoint((int)(location.getLatitude() * 1e6), (int)(location.getLongitude() * 1e6));

		OverlayItem item = new OverlayItem(point, "you", "yes,you!!");
		mio.clear();
		mio.addOverlay(item);
		// mapOverlays.add(mio);
		mapView.invalidate();
		mc.animateTo(point);
	}

	@Override
	public void startup() {
		mapView = getMapView();
		mc = mapView.getController();
		
		// creating the person icon
		ownDrawable = this.getResources().getDrawable(R.drawable.person);
		mapOverlays = mapView.getOverlays();
		mio = new MapItemOverlay(ownDrawable, mapView.getContext());
		mapOverlays.add(mio);
		
		//creation Klappstuhl Icon
		klappDrawable = this.getResources().getDrawable(R.drawable.klappstuhl);
		mio = new MapItemOverlay(klappDrawable);
		mapOverlays.add(mio);
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		makeUseOfNewLocation(lastKnownLocation);
	}

}
