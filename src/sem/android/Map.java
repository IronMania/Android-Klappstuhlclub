package sem.android;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class Map extends MapActivity  {
	private MapView mapView;
	private MapController mc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		

		
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mc = mapView.getController();
		
		startup();
    }
	
	public void startup(){
		
	}
	
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	public MapView getMapView() {
		return mapView;
	}

	public MapController getMc() {
		return mc;
	}
	public GeoPoint calculateGeoPoint(double lat ,double lng ){
		GeoPoint gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
		return gp;
		
	}
    
}
